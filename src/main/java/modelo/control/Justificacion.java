/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.control;
import java.util.Date;

/**
 *
 * @author Oscar
 */



public class Justificacion {
    private int idJustificacion;
    private String motivo;
    private Date fechaEnvio;
    private String estado;
    private String comentario;
    private String archivoSoporte;
    public Justificacion() {
        this.estado    = "Pendiente";
        this.comentario = "";
    }
    public Justificacion(int idJustificacion, String motivo, Date fechaEnvio,String estado, String comentario, String archivoSoporte) {
        this.idJustificacion = idJustificacion;
        this.motivo = motivo;
        this.fechaEnvio  = fechaEnvio;
        this.estado = estado;
        this.comentario  = comentario;
        this.archivoSoporte  = archivoSoporte;
    }
    public void enviarJustificacion() {
        this.estado = "Pendiente";
        this.fechaEnvio = new Date();
    }

    public void aprobarJustificacion() {
        this.estado = "Aprobada";
    }

    public void rechazarJustificacion(String comentario) {
        this.estado = "Rechazada";
        this.comentario= comentario;
    }

    public void consultarEstado() {
    }

    public void adjuntarSoporte(String rutaArchivo) {
        this.archivoSoporte = rutaArchivo;
    }
    public void rechazarJustificacion() {
        rechazarJustificacion("Sin comentario adicional.");
    }
    public int    getIdJustificacion()  
    {
        return idJustificacion; }
    public String getMotivo()           
    { 
        return motivo; }
    public Date   getFechaEnvio()      
    { 
        return fechaEnvio; }
    public String getEstado()          
    { 
        return estado; }
    public String getComentario()       
    { 
        return comentario; }
    public String getArchivoSoporte()  
    { 
        return archivoSoporte; }

    public void setIdJustificacion(int i)    
    { 
        this.idJustificacion = i; }
    
    public void setMotivo(String m)   
            
    { 
        this.motivo = m; }
    public void setFechaEnvio(Date f)        
    { 
        this.fechaEnvio = f; }
    public void setEstado(String e)          
    { 
        this.estado = e; }
    public void setComentario(String c)      
    { 
        this.comentario = c; }
    public void setArchivoSoporte(String a)   
    { 
        this.archivoSoporte = a; }

    @Override
    public String toString() {
        return "Justificacion[" + idJustificacion + "] " + motivo + " | Estado: " + estado;
    }
}
