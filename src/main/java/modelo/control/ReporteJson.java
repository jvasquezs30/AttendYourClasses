package modelo.control;

import modelo.academico.Clase;
import modelo.academico.Curso;
import modelo.usuario.Docente;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReporteJson {

    public static String generar(Curso curso, Clase clase, Docente docente, List<RegistroLlegada> registros) {
        File carpetaReportes = new File("reportes");
        if (!carpetaReportes.exists()) {
            carpetaReportes.mkdirs();
        }

        File archivoJson = new File(carpetaReportes, "asistencia_manual_" + System.currentTimeMillis() + ".json");
        String contenido = construirJson(curso, clase, docente, registros);

        try {
            Files.writeString(archivoJson.toPath(), contenido, StandardCharsets.UTF_8);
            return abrirJsonAutomaticamente(archivoJson);
        } catch (IOException ex) {
            return "No se pudo generar el JSON: " + ex.getMessage();
        }
    }

    private static String abrirJsonAutomaticamente(File archivoJson) {
        String ruta = archivoJson.getAbsolutePath();
        if (!Desktop.isDesktopSupported()) {
            return "JSON generado en: " + ruta + "\nNo se pudo abrir automáticamente porque el sistema no soporta Desktop.";
        }
        try {
            Desktop.getDesktop().open(archivoJson);
            return "JSON generado y abierto automáticamente.\nUbicación: " + ruta;
        } catch (IOException ex) {
            return "JSON generado en: " + ruta + "\nNo se pudo abrir automáticamente: " + ex.getMessage();
        }
    }

    private static String construirJson(Curso curso, Clase clase, Docente docente, List<RegistroLlegada> registros) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"curso\": \"").append(escapar(curso.getNombreCurso())).append("\",\n");
        json.append("  \"tema\": \"").append(escapar(clase.getTema())).append("\",\n");
        json.append("  \"docente\": \"").append(escapar(docente.getNombre() + " " + docente.getApellido())).append("\",\n");
        json.append("  \"aula\": \"").append(escapar(clase.getAula())).append("\",\n");
        json.append("  \"fechaGeneracion\": \"").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("\",\n");
        json.append("  \"registros\": [\n");

        for (int i = 0; i < registros.size(); i++) {
            json.append(indent(registros.get(i).generarJson(), 4));
            if (i < registros.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]\n");
        json.append("}");
        return json.toString();
    }

    private static String indent(String texto, int espacios) {
        String prefijo = " ".repeat(espacios);
        return prefijo + texto.replace("\n", "\n" + prefijo);
    }

    private static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}
