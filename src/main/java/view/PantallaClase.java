

package view;
import modelo.academico.Clase;
import modelo.academico.Curso;
import modelo.control.RegistroLlegada;
import modelo.control.ReportePdf;
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
    private JLabel totalValor;
    private JLabel puntualesValor;
    private JLabel tardanzasValor;
    private JLabel externosValor;

    public PantallaClase(Curso curso, Clase clase, Docente docente, List<RegistroLlegada> registros) {
        this.curso = curso;
        this.clase = clase;
        this.docente = docente;
        this.registros = registros;
        this.visibles = new ArrayList<>();
        configurarVentana();
        construirContenido();
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            simularLlegadas();
        });
    }

    private void configurarVentana() {
        setTitle("Attend Your Classes");
        setSize(1150, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        JPanel panel = new JPanel(new GridLayout(3, 1, 4, 4));
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

        estadoClase = new JLabel("Clase iniciada. Esperando ingreso de estudiantes...", SwingConstants.CENTER);
        estadoClase.setFont(new Font("Segoe UI", Font.BOLD, 15));
        estadoClase.setForeground(Color.CYAN);

        panel.add(titulo);
        panel.add(detalle);
        panel.add(estadoClase);
        return panel;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"Código", "Estudiante", "Llegada", "Estado", "Retraso", "Excusa", "¿Pertenece?", "Autorización", "Criterio docente"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        tabla.getColumnModel().getColumn(1).setPreferredWidth(145);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(245);
        tabla.getColumnModel().getColumn(8).setPreferredWidth(340);
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = table.getModel().getValueAt(table.convertRowIndexToModel(row), 3).toString();
                if (!isSelected) {
                    if ("Tardanza".equalsIgnoreCase(estado)) {
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

    private JPanel crearZonaInferior() {
        JPanel zona = new JPanel(new BorderLayout(12, 12));
        zona.setBackground(new Color(240, 240, 240));
        zona.add(crearResumen(), BorderLayout.CENTER);
        zona.add(crearBotonPdf(), BorderLayout.EAST);
        return zona;
    }

    private JPanel crearResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 12, 12));
        panel.setBackground(new Color(240, 240, 240));
        totalValor = crearValor();
        puntualesValor = crearValor();
        tardanzasValor = crearValor();
        externosValor = crearValor();
        panel.add(crearTarjeta("Ingresos mostrados", totalValor));
        panel.add(crearTarjeta("A tiempo", puntualesValor));
        panel.add(crearTarjeta("Tardanzas", tardanzasValor));
        panel.add(crearTarjeta("Externos", externosValor));
        actualizarResumen();
        return panel;
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

    private void simularLlegadas() {
        Thread hilo = new Thread(() -> {
            pausar(900);
            for (RegistroLlegada registro : registros) {
                SwingUtilities.invokeLater(() -> agregarRegistro(registro));
                pausar(900);
            }
            SwingUtilities.invokeLater(() -> estadoClase.setText("Registro finalizado. Ya puede extraer el PDF de asistencia."));
        });
        hilo.start();
    }

    private void agregarRegistro(RegistroLlegada registro) {
        visibles.add(registro);
        modelo.addRow(new Object[]{
                registro.getEstudiante().getCodigoEstudiante(),
                registro.getEstudiante().getNombre() + " " + registro.getEstudiante().getApellido(),
                registro.getHoraIngreso(),
                registro.getEstado(),
                registro.getMinutosRetraso() > 0 ? registro.getMinutosRetraso() + " min" : "A tiempo",
                registro.obtenerExcusaVisible(),
                registro.obtenerPertenenciaVisible(),
                registro.obtenerAutorizacionVisible(),
                registro.getDecisionDocente()
        });
        estadoClase.setText("Último ingreso: " + registro.getEstudiante().getNombre() + " " + registro.getEstudiante().getApellido()
                + " · " + registro.getHoraIngreso() + " · " + registro.getEstado());
        actualizarResumen();
    }

    private void actualizarResumen() {
        int puntuales = 0;
        int tardanzas = 0;
        int externos = 0;

        for (RegistroLlegada r : visibles) {
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

        totalValor.setText(String.valueOf(visibles.size()));
        puntualesValor.setText(String.valueOf(puntuales));
        tardanzasValor.setText(String.valueOf(tardanzas));
        externosValor.setText(String.valueOf(externos));
    }

    private void pausar(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}