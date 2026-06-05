package view;

import modelo.academico.Clase;
import modelo.academico.Curso;
import modelo.control.GeneradorJsonRunnable;
import modelo.control.RegistroLlegada;
import modelo.control.ReporteJson;
import modelo.control.ReportePdf;
import modelo.control.RelojClaseThread;
import modelo.usuario.Docente;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PantallaClase extends JFrame {

    private Curso curso;
    private Clase clase;
    private Docente docente;
    private List<RegistroLlegada> registros;
    private List<RegistroLlegada> visibles;
    private DefaultTableModel modelo;
    private JLabel estadoClase;
    private JLabel relojValor;
    private JLabel totalValor;
    private JLabel puntualesValor;
    private JLabel tardanzasValor;
    private JLabel externosValor;
    private LocalTime horaPrograma;
    private RelojClaseThread relojThread;
    private boolean actualizandoTabla;
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    public PantallaClase(Curso curso, Clase clase, Docente docente, List<RegistroLlegada> registros) {
        this.curso = curso;
        this.clase = clase;
        this.docente = docente;
        this.registros = registros;
        this.visibles = new ArrayList<>();
        this.horaPrograma = obtenerHoraInicial();
        configurarVentana();
        construirContenido();
        cargarRegistrosPendientes();
        iniciarRelojDinamico();
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    private void configurarVentana() {
        setTitle("Attend Your Classes");
        setSize(1300, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                detenerRelojDinamico();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                detenerRelojDinamico();
            }
        });
    }

    private void construirContenido() {
        JPanel contenedor = new JPanel(new BorderLayout(15, 15));
        contenedor.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        contenedor.setBackground(new Color(240, 240, 240));
        contenedor.add(crearEncabezado(), BorderLayout.NORTH);
        contenedor.add(crearTabla(), BorderLayout.CENTER);
        contenedor.add(crearZonaInferior(), BorderLayout.SOUTH);
        setContentPane(contenedor);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 4, 4));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel titulo = new JLabel(curso.getNombreCurso(), SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        JLabel detalle = new JLabel("Docente: " + docente.getNombre() + " " + docente.getApellido()
                + "   ·   Tema: " + clase.getTema()
                + "   ·   Inicio: " + clase.getHoraInicio()
                + "   ·   Aula: " + clase.getAula(), SwingConstants.CENTER);
        detalle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detalle.setForeground(Color.LIGHT_GRAY);

        relojValor = new JLabel(textoReloj(), SwingConstants.CENTER);
        relojValor.setFont(new Font("Segoe UI", Font.BOLD, 18));
        relojValor.setForeground(new Color(180, 240, 255));

        estadoClase = new JLabel(" Marque en el recuadro de pasar lista y escriba la excusa (Si aplica).", SwingConstants.CENTER);
        estadoClase.setFont(new Font("Segoe UI", Font.BOLD, 15));
        estadoClase.setForeground(Color.CYAN);

        panel.add(titulo);
        panel.add(detalle);
        panel.add(relojValor);
        panel.add(estadoClase);
        return panel;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {
                "Código",
                "Estudiante",
                "Pasar lista",
                "Estado",
                "Excusa",
                "¿Pertenece?",
                "Autorización",
                "Criterio docente"
        };

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(34);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(40, 40, 40));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setAutoCreateRowSorter(true);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(110);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(260);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(7).setPreferredWidth(360);
        tabla.setToolTipText("Doble clic en Excusa para escribir lo que dijo el estudiante.");

        modelo.addTableModelListener(e -> {
            if (actualizandoTabla || e.getFirstRow() < 0) {
                return;
            }

            int fila = e.getFirstRow();
            int columna = e.getColumn();
            if (fila >= registros.size()) {
                return;
            }

            actualizandoTabla = true;
            try {
                synchronized (registros) {
                    RegistroLlegada registro = registros.get(fila);
                    if (columna == 2) {
                        boolean pasoLista = Boolean.TRUE.equals(modelo.getValueAt(fila, 2));
                        if (pasoLista) {
                            registro.marcarPasoLista(clase, horaPrograma.format(FORMATO_HORA));
                        } else {
                            registro.quitarPasoLista();
                        }
                        refrescarFila(fila, registro);
                        estadoClase.setText("Registro manual actualizado: "
                                + registro.getEstudiante().getNombre() + " "
                                + registro.getEstudiante().getApellido() + " · "
                                + registro.obtenerEstadoManual());
                    } else if (columna == 4) {
                        String excusa = String.valueOf(modelo.getValueAt(fila, 4));
                        registro.registrarExcusaManual(excusa);
                        refrescarFila(fila, registro);
                        estadoClase.setText("Excusa registrada para: "
                                + registro.getEstudiante().getNombre() + " "
                                + registro.getEstudiante().getApellido());
                    }
                }
                actualizarResumen();
            } finally {
                actualizandoTabla = false;
            }
        });

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = table.getModel().getValueAt(table.convertRowIndexToModel(row), 3).toString();
                if (!isSelected) {
                    if ("No registrado".equalsIgnoreCase(estado)) {
                        componente.setBackground(new Color(245, 245, 245));
                    } else if ("Tardanza".equalsIgnoreCase(estado)) {
                        componente.setBackground(new Color(255, 247, 237));
                    } else if ("No autorizado".equalsIgnoreCase(estado)) {
                        componente.setBackground(new Color(254, 226, 226));
                    } else if ("Invitado autorizado".equalsIgnoreCase(estado)) {
                        componente.setBackground(new Color(239, 246, 255));
                    } else {
                        componente.setBackground(Color.WHITE);
                    }
                }
                return componente;
            }
        });
        return new JScrollPane(tabla);
    }

    private void cargarRegistrosPendientes() {
        synchronized (registros) {
            for (RegistroLlegada registro : registros) {
                registro.quitarPasoLista();
                modelo.addRow(new Object[]{
                        registro.getEstudiante().getCodigoEstudiante(),
                        registro.getEstudiante().getNombre() + " " + registro.getEstudiante().getApellido(),
                        Boolean.FALSE,
                        registro.obtenerEstadoManual(),
                        registro.obtenerExcusaVisible(),
                        registro.obtenerPertenenciaVisible(),
                        registro.obtenerAutorizacionVisible(),
                        registro.getDecisionDocente()
                });
            }
        }
        actualizarResumen();
    }

    private JPanel crearZonaInferior() {
        JPanel zona = new JPanel(new BorderLayout(12, 12));
        zona.setBackground(new Color(240, 240, 240));
        zona.add(crearResumen(), BorderLayout.CENTER);
        zona.add(crearBotones(), BorderLayout.EAST);
        return zona;
    }

    private JPanel crearResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 12, 12));
        panel.setBackground(new Color(240, 240, 240));
        totalValor = crearValor();
        puntualesValor = crearValor();
        tardanzasValor = crearValor();
        externosValor = crearValor();
        panel.add(crearTarjeta("Marcados en lista", totalValor));
        panel.add(crearTarjeta("A tiempo", puntualesValor));
        panel.add(crearTarjeta("Tardanzas", tardanzasValor));
        panel.add(crearTarjeta("Externos", externosValor));
        actualizarResumen();
        return panel;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 8, 8));
        panel.setBackground(new Color(240, 240, 240));
        panel.add(crearBotonJson());
        panel.add(crearBotonPdf());
        return panel;
    }

    private JButton crearBotonJson() {
        JButton boton = new JButton("Generar JSON");
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(new Color(20, 120, 80));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        boton.addActionListener(e -> generarJsonEnHilo(boton));
        return boton;
    }

    private JButton crearBotonPdf() {
        JButton boton = new JButton("Extraer PDF");
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(new Color(30, 90, 180));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        boton.addActionListener(e -> {
            String resultado = ReportePdf.generar(curso, clase, docente, registros);
            JOptionPane.showMessageDialog(this, "Reporte generado: " + resultado);
        });
        return boton;
    }

    private JLabel crearValor() {
        JLabel valor = new JLabel("0", SwingConstants.CENTER);
        valor.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valor.setForeground(new Color(40, 40, 40));
        return valor;
    }

    private JPanel crearTarjeta(String titulo, JLabel valor) {
        JPanel tarjeta = new JPanel(new GridLayout(2, 1));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        JLabel etiqueta = new JLabel(titulo, SwingConstants.CENTER);
        etiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        etiqueta.setForeground(Color.DARK_GRAY);

        tarjeta.add(etiqueta);
        tarjeta.add(valor);
        return tarjeta;
    }

    private void refrescarFila(int fila, RegistroLlegada registro) {
        modelo.setValueAt(registro.obtenerEstadoManual(), fila, 3);
        modelo.setValueAt(registro.obtenerExcusaVisible(), fila, 4);
        modelo.setValueAt(registro.obtenerPertenenciaVisible(), fila, 5);
        modelo.setValueAt(registro.obtenerAutorizacionVisible(), fila, 6);
        modelo.setValueAt(registro.getDecisionDocente(), fila, 7);
    }

    private void iniciarRelojDinamico() {
        relojThread = new RelojClaseThread(() -> {
            horaPrograma = horaPrograma.plusMinutes(1);
            relojValor.setText(textoReloj());
        });
        relojThread.start();
    }

    private void detenerRelojDinamico() {
        if (relojThread != null) {
            relojThread.detenerReloj();
        }
    }

    private void generarJsonEnHilo(JButton boton) {
        boton.setEnabled(false);
        estadoClase.setText("Generando JSON en un hilo independiente...");

        GeneradorJsonRunnable tarea = new GeneradorJsonRunnable(curso, clase, docente, registros, resultado ->
                SwingUtilities.invokeLater(() -> {
                    boton.setEnabled(true);
                    estadoClase.setText("JSON generado con Runnable y Thread. La interfaz siguió disponible.");
                    JOptionPane.showMessageDialog(this, resultado);
                })
        );

        Thread hiloJson = new Thread(tarea, "Hilo-Generador-JSON");
        hiloJson.start();
    }

    private LocalTime obtenerHoraInicial() {
        try {
            return LocalTime.parse(clase.getHoraInicio());
        } catch (DateTimeParseException ex) {
            return LocalTime.now().withSecond(0).withNano(0);
        }
    }

    private String textoReloj() {
        return "Reloj de clase: " + horaPrograma.format(FORMATO_HORA)
                ;
    }

    private void actualizarResumen() {
        int marcados = 0;
        int puntuales = 0;
        int tardanzas = 0;
        int externos = 0;

        synchronized (registros) {
            visibles.clear();
            for (RegistroLlegada r : registros) {
                if (!r.isPasoLista()) {
                    continue;
                }
                visibles.add(r);
                marcados++;
                if ("Presente".equalsIgnoreCase(r.getEstado())) {
                    puntuales++;
                }
                if (r.tieneTardanza()) {
                    tardanzas++;
                }
                if (!r.isPerteneceClase()) {
                    externos++;
                }
            }
        }

        totalValor.setText(String.valueOf(marcados));
        puntualesValor.setText(String.valueOf(puntuales));
        tardanzasValor.setText(String.valueOf(tardanzas));
        externosValor.setText(String.valueOf(externos));
    }
}
