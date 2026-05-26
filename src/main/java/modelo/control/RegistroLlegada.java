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
        if (clase.esTardanza(horaIngreso) && excusa != null && !excusa.trim().isEmpty()) {
            this.justificacion = new Justificacion(idAsistencia, excusa, new Date(), "Pendiente", "Registrada al momento de llegada", "");
        }
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

    public boolean tieneTardanza() {
        return "Tardanza".equalsIgnoreCase(getEstado());
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
}
