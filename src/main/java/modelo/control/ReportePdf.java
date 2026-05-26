package modelo.control;
import modelo.academico.Clase;
import modelo.academico.Curso;
import modelo.usuario.Docente;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportePdf {

    public static String generar(Curso curso, Clase clase, Docente docente, List<RegistroLlegada> registros) {
        File carpetaReportes = new File("reportes");
        if (!carpetaReportes.exists()) {
            carpetaReportes.mkdirs();
        }
        File archivoPdf = new File(carpetaReportes, "reporte_asistencia_" + System.currentTimeMillis() + ".pdf");
        List<String> lineas = new ArrayList<>();
        lineas.add("REPORTE DE ASISTENCIA");
        lineas.add("Materia: " + curso.getNombreCurso());
        lineas.add("Tema: " + clase.getTema());
        lineas.add("Docente: " + docente.getNombre() + " " + docente.getApellido());
        lineas.add("Hora de inicio: " + clase.getHoraInicio());
        lineas.add("Aula: " + clase.getAula());
        lineas.add("Tolerancia: " + clase.getTolerancia() + " minutos");
        lineas.add("Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        lineas.add(" ");
        lineas.add("Código | Estudiante | Llegada | Estado | Retraso | Excusa | Pertenece | Autorización | Criterio docente");
        lineas.add(" ");
        for (RegistroLlegada r : registros) {
            lineas.add(r.getEstudiante().getCodigoEstudiante() + " | "
                    + r.getEstudiante().getNombre() + " " + r.getEstudiante().getApellido() + " | "
                    + r.getHoraIngreso() + " | "
                    + r.getEstado() + " | "
                    + (r.getMinutosRetraso() > 0 ? r.getMinutosRetraso() + " min" : "A tiempo") + " | "
                    + r.obtenerExcusaVisible() + " | "
                    + r.obtenerPertenenciaVisible() + " | "
                    + r.obtenerAutorizacionVisible() + " | "
                    + r.getDecisionDocente());
        }
        try {
            crearPdf(archivoPdf, lineas);
            return abrirPdfAutomaticamente(archivoPdf);
        } catch (IOException ex) {
            return "No se pudo generar el PDF: " + ex.getMessage();
        }
    }

    private static String abrirPdfAutomaticamente(File archivoPdf) {
        String ruta = archivoPdf.getAbsolutePath();
        if (!Desktop.isDesktopSupported()) {
            return "PDF generado en: " + ruta + "\nNo se pudo abrir automáticamente porque el sistema no soporta Desktop.";
        }
        try {
            Desktop.getDesktop().open(archivoPdf);
            return "PDF generado y abierto automáticamente.\nUbicación: " + ruta;
        } catch (IOException ex) {
            return "PDF generado en: " + ruta + "\nNo se pudo abrir automáticamente: " + ex.getMessage();
        }
    }

    private static void crearPdf(File archivo, List<String> lineas) throws IOException {
        StringBuilder contenido = new StringBuilder();
        contenido.append("BT\n/F1 10 Tf\n50 780 Td\n14 TL\n");
        for (String linea : lineas) {
            contenido.append("(").append(escapar(dividir(linea, 118))).append(") Tj\nT*\n");
        }
        contenido.append("ET");

        byte[] stream = contenido.toString().getBytes(StandardCharsets.ISO_8859_1);
        List<Integer> offsets = new ArrayList<>();
        StringBuilder pdf = new StringBuilder();
        pdf.append("%PDF-1.4\n");
        offsets.add(pdf.length());
        pdf.append("1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");
        offsets.add(pdf.length());
        pdf.append("2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");
        offsets.add(pdf.length());
        pdf.append("3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 842 595] /Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >>\nendobj\n");
        offsets.add(pdf.length());
        pdf.append("4 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\nendobj\n");
        offsets.add(pdf.length());
        pdf.append("5 0 obj\n<< /Length ").append(stream.length).append(" >>\nstream\n");
        pdf.append(contenido);
        pdf.append("\nendstream\nendobj\n");
        int xref = pdf.length();
        pdf.append("xref\n0 6\n0000000000 65535 f \n");
        for (Integer offset : offsets) {
            pdf.append(String.format("%010d 00000 n \n", offset));
        }
        pdf.append("trailer\n<< /Root 1 0 R /Size 6 >>\nstartxref\n").append(xref).append("\n%%EOF");
        try (FileOutputStream salida = new FileOutputStream(archivo)) {
            salida.write(pdf.toString().getBytes(StandardCharsets.ISO_8859_1));
        }
    }

    private static String dividir(String texto, int maximo) {
        return texto.length() <= maximo ? texto : texto.substring(0, maximo - 3) + "...";
    }

    private static String escapar(String texto) {
        return texto.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }
}
