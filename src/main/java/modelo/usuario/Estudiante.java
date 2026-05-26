/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.usuario;
import modelo.control.Asistencia;
import modelo.control.Justificacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oscar
 */


public class Estudiante extends Usuario {
    private String codigoEstudiante;
    private String programa;
    private int semestre;
    private String estadoAcademico;
    private List<Asistencia>    asistencias;
    private List<Justificacion> justificaciones;
    public Estudiante() {
        super();
        this.asistencias     = new ArrayList<>();
        this.justificaciones = new ArrayList<>();
        this.estadoAcademico = "Activo";
    }
    public Estudiante(int idUsuario, String nombre, String apellido,String identificacion, String correo, String contrasena,String codigoEstudiante, String programa, int semestre) {
        super(idUsuario, nombre, apellido, identificacion, correo, contrasena);
        this.codigoEstudiante = codigoEstudiante;
        this.programa         = programa;
        this.semestre         = semestre;
        this.estadoAcademico  = "Activo";
        this.asistencias      = new ArrayList<>();
        this.justificaciones  = new ArrayList<>();
    }
    @Override
    public void consultarPerfil() {
    }

    @Override
    public String getRol() {
        return "ESTUDIANTE";
    }
    public void consultarAsistencia() {
        if (asistencias.isEmpty()) {
        } else {
            for (Asistencia a : asistencias) {
            }
        }
    }

    public void enviarJustificacion(Justificacion j) {
        justificaciones.add(j);
    }

    public void consultarJustificaciones() {
        for (Justificacion j : justificaciones) {
        }
    }

    public void verEstadoAsistencia() {
        long presentes = 0, tardanzas = 0, ausentes = 0;
        for (Asistencia a : asistencias) {
            switch (a.getEstado().toLowerCase()) {
                case "presente": presentes++; break;
                case "tardanza": tardanzas++; break;
                case "ausente":  ausentes++;  break;
            }
        }
    }

    public void agregarAsistencia(Asistencia a) { asistencias.add(a); }
    public void enviarJustificacion(String motivo, String archivoSoporte) {
        Justificacion j = new Justificacion(0, motivo,
                          new java.util.Date(), "Pendiente", "", archivoSoporte);
        justificaciones.add(j);
    }
    public String getCodigoEstudiante()          
    {
        return codigoEstudiante; }
    public String getPrograma()                   
    { 
        return programa; }
    public int    getSemestre()                    
    { 
        return semestre; }
    public String getEstadoAcademico()             
    {
        return estadoAcademico; }
    public List<Asistencia>    getAsistencias()    
    { 
        return asistencias; }
    public List<Justificacion> getJustificaciones()
    { 
        return justificaciones; }

    public void setCodigoEstudiante(String c) 
    { 
        this.codigoEstudiante = c; }
    public void setPrograma(String p)          
    { 
        this.programa = p; }
    public void setSemestre(int s)             
    { 
        this.semestre = s; }
    public void setEstadoAcademico(String e)    
    {
        this.estadoAcademico = e; }
}
