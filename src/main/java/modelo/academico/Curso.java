/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.academico;//

import modelo.usuario.Docente;
import modelo.usuario.Estudiante;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oscar
 */


public class Curso {
    private int    idCurso;
    private String nombreCurso;
    private String nivel;
    private String jornada;
    private int    cantidadMaxEstudiantes;
    private List<Estudiante> estudiantes;
    private Docente docente;
    private List<Clase> clases;
    public Curso() {
        
        this.estudiantes = new ArrayList<>();
        this.clases      = new ArrayList<>();
    }
    public Curso(int idCurso, String nombreCurso, String nivel,String jornada, int cantidadMaxEstudiantes) {
        this.idCurso = idCurso;
        this.nombreCurso= nombreCurso;
        this.nivel = nivel;
        this.jornada = jornada;
        this.cantidadMaxEstudiantes = cantidadMaxEstudiantes;
        this.estudiantes = new ArrayList<>();
        this.clases  = new ArrayList<>();
    }
    public void agregarEstudiante(Estudiante e) {
        if (estudiantes.size() < cantidadMaxEstudiantes) {
            estudiantes.add(e);
        } else {
        }
    }

    public void eliminarEstudiante(String codigo) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getCodigoEstudiante().equals(codigo)) {
                estudiantes.remove(i);
                break;
            }
        }
    }

    public void listarEstudiantes() {
        for (Estudiante e : estudiantes) {
        }
    }

    public void asignarDocente(Docente d) {
        this.docente = d;
    }
    public Clase crearClase(String tema, java.util.Date fecha, String horaInicio, int duracion, String aula) {
       
        Clase c = new Clase((int)(Math.random()*1000), tema, fecha, horaInicio, duracion, 10, aula);
        clases.add(c);
        return c;
    }
    public int getIdCurso()             
    {
        return idCurso;
    }
    public String  getNombreCurso()            
    {
        return nombreCurso;
    }
    public String getNivel()                 
    {
        return nivel; 
    }
    public String getJornada()               
    { 
        return jornada; 
    }
    public int getCantidadMaxEstudiantes() 
    { 
        return cantidadMaxEstudiantes;
    }
    public List<Estudiante> getEstudiantes()         
    { 
        return estudiantes; 
    }
    public Docente       getDocente()                
    { 
        return docente;
    }
    public List<Clase>   getClases()                 
    {
        return clases; 
    }

    public void setIdCurso(int i)                   
    { 
        this.idCurso = i; 
    }
    public void setNombreCurso(String n)           
    {
        this.nombreCurso = n; 
    }
    public void setNivel(String n)                  
    { 
        this.nivel = n; 
    }
    public void setJornada(String j)                
    {
        this.jornada = j; 
    }
    public void setCantidadMaxEstudiantes(int c)    
    { 
        this.cantidadMaxEstudiantes = c; 
    }
    public void setDocente(Docente d)              
    { 
        this.docente = d; 
    }

    @Override
    public String toString() {
        return "Curso[" + idCurso + "] " + nombreCurso + " | " + nivel + " | " + jornada;
    }
}