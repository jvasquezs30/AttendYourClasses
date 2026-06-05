/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.control;

import modelo.academico.Clase;
import modelo.usuario.Estudiante;

import java.util.Date;

/**
 *
 * @author Oscar
 */
public class RegistroLlegada extends Asistencia {

    private Estudiante estudiante;
    private Justificacion justificacion;
    private int minutosRetraso;
    private boolean perteneceClase;
    private boolean autorizadoPermanecer;
    private String decisionDocente;
    private boolean pasoLista;

    public RegistroLlegada(int idAsistencia, Clase clase, Estudiante estudiante, String horaIngreso) {
        this(idAsistencia, clase, estudiante, horaIngreso, "", true, true, "Estudiante matriculado en el curso");
    }

    public RegistroLlegada(int idAsistencia, Clase clase, Estudiante estudiante, String horaIngreso, String excusa) {
        this(idAsistencia, clase, estudiante, horaIngreso, excusa, true, true, "Estudiante matriculado en el curso");
    }

    public RegistroLlegada(int idAsistencia, Clase clase, Estudiante estudiante, String horaIngreso, String excusa,
                           boolean perteneceClase, boolean autorizadoPermanecer, String decisionDocente) {
        super(idAsistencia, new Date(), horaIngreso, definirEstado(clase, horaIngreso, perteneceClase, autorizadoPermanecer), excusa);
        this.estudiante = estudiante;
        this.minutosRetraso = clase.minutosDespuesInicio(horaIngreso);
        this.perteneceClase = perteneceClase;
        this.autorizadoPermanecer = autorizadoPermanecer;
        this.decisionDocente = decisionDocente;
        this.pasoLista = false;
        registrarExcusaManual(excusa);
    }

    private static String definirEstado(Clase clase, String horaIngreso, boolean perteneceClase, boolean autorizadoPermanecer) {
        if (!perteneceClase && autorizadoPermanecer) {
            return "Invitado autorizado";
        }
        if (!perteneceClase) {
            return "No autorizado";
        }
        return clase.esTardanza(horaIngreso) ? "Tardanza" : "Presente";
    }

    public void marcarPasoLista(Clase clase, String horaManual) {
        this.pasoLista = true;
        setHoraIngreso(horaManual);
        this.minutosRetraso = clase.minutosDespuesInicio(horaManual);
        setEstado(definirEstado(clase, horaManual, perteneceClase, autorizadoPermanecer));
    }

    public void quitarPasoLista() {
        this.pasoLista = false;
    }

    public void registrarExcusaManual(String excusa) {
        String texto = excusa == null ? "" : excusa.trim();
        actualizarObservacion(texto);

        if (texto.isEmpty()) {
            this.justificacion = null;
            return;
        }

        if (this.justificacion == null) {
            this.justificacion = new Justificacion(
                    getIdAsistencia(),
                    texto,
                    new Date(),
                    "Pendiente",
                    "Registrada manualmente por el docente",
                    ""
            );
        } else {
            this.justificacion.setMotivo(texto);
            this.justificacion.setFechaEnvio(new Date());
            this.justificacion.setEstado("Pendiente");
            this.justificacion.setComentario("Actualizada manualmente por el docente");
        }
    }

    public boolean tieneTardanza() {
        return "Tardanza".equalsIgnoreCase(getEstado());
    }

    public String obtenerEstadoManual() {
        if (!pasoLista) {
            return "No registrado";
        }
        return getEstado();
    }

    public String obtenerExcusaVisible() {
        if (justificacion == null) {
            return tieneTardanza() ? "Sin excusa registrada" : "No aplica";
        }
        return justificacion.getMotivo();
    }

    public String obtenerPertenenciaVisible() {
        return perteneceClase ? "Sí" : "No";
    }

    public String obtenerAutorizacionVisible() {
        if (perteneceClase) {
            return "No requiere";
        }
        return autorizadoPermanecer ? "Puede quedarse" : "Debe retirarse";
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Justificacion getJustificacion() {
        return justificacion;
    }

    public int getMinutosRetraso() {
        return minutosRetraso;
    }

    public boolean isPerteneceClase() {
        return perteneceClase;
    }

    public boolean isAutorizadoPermanecer() {
        return autorizadoPermanecer;
    }

    public String getDecisionDocente() {
        return decisionDocente;
    }

    public boolean isPasoLista() {
        return pasoLista;
    }

    public void setPasoLista(boolean pasoLista) {
        this.pasoLista = pasoLista;
    }

    public String generarJson() {
        Estudiante e = getEstudiante();
        return "{\n" +
                "  \"estudiante\": {\n" +
                "    \"idUsuario\": " + e.getIdUsuario() + ",\n" +
                "    \"nombre\": \"" + escaparJson(e.getNombre()) + "\",\n" +
                "    \"apellido\": \"" + escaparJson(e.getApellido()) + "\",\n" +
                "    \"identificacion\": \"" + escaparJson(e.getIdentificacion()) + "\",\n" +
                "    \"correo\": \"" + escaparJson(e.getCorreo()) + "\",\n" +
                "    \"codigoEstudiante\": \"" + escaparJson(e.getCodigoEstudiante()) + "\",\n" +
                "    \"programa\": \"" + escaparJson(e.getPrograma()) + "\",\n" +
                "    \"semestre\": " + e.getSemestre() + ",\n" +
                "    \"estadoAcademico\": \"" + escaparJson(e.getEstadoAcademico()) + "\"\n" +
                "  },\n" +
                "  \"asistencia\": {\n" +
                "    \"pasoLista\": " + pasoLista + ",\n" +
                "    \"estado\": \"" + escaparJson(obtenerEstadoManual()) + "\",\n" +
                "    \"perteneceClase\": " + perteneceClase + ",\n" +
                "    \"autorizado\": " + autorizadoPermanecer + ",\n" +
                "    \"excusa\": \"" + escaparJson(obtenerExcusaVisible()) + "\",\n" +
                "    \"decisionDocente\": \"" + escaparJson(decisionDocente) + "\"\n" +
                "  }\n" +
                "}";
    }

    private String escaparJson(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}
