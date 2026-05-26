package modelo.control;

import java.util.Date;

public class Asistencia {
    private int idAsistencia;
    private Date fecha;
    private String horaIngreso;
    private String estado;
    private String observacion;
    public Asistencia() {
        this.estado      = "Pendiente";
        this.observacion = "";
    }
    public Asistencia(int idAsistencia, Date fecha, String horaIngreso,String estado, String observacion) {
        this.idAsistencia = idAsistencia;
        this.fecha = fecha;
        this.horaIngreso = horaIngreso;
        this.estado = estado;
        this.observacion = observacion;
    }
    public void registrarIngreso(String hora) 
    {
        this.horaIngreso = hora;
        this.estado= "Presente";
    }

    public void marcarTardanza(String hora) {
        this.horaIngreso = hora;
        this.estado = "Tardanza";
    }

    public void marcarInasistencia() {
        this.estado = "Ausente";
    }

    public void consultarEstado() {
    }

    public void actualizarObservacion(String obs) {
        this.observacion = obs;
    }
    public void registrarIngreso(String hora, boolean conTolerancia, int minutosTolerancia) {
        this.horaIngreso = hora;
        this.estado = conTolerancia ? "Tardanza" : "Presente";
    }
    public int    getIdAsistencia()  
    { 
        return idAsistencia; 
    }
    public Date   getFecha()         
    { 
        return fecha; 
    }
    public String getHoraIngreso()   
    { 
        return horaIngreso; }
    public String getEstado()        
    { 
        return estado; }
    public String getObservacion()   
    { 
        return observacion; }

    
    public void setIdAsistencia(int i)  
    { 
        this.idAsistencia = i; 
    }
    public void setFecha(Date f)         
    { 
        this.fecha = f; 
    }
    public void setHoraIngreso(String h)  
    { 
        this.horaIngreso = h; 
    }
    public void setEstado(String e)       
    { 
        this.estado = e; 
    }
    public void setObservacion(String o) 
    { 
        this.observacion = o;
    }

    
    @Override
    public String toString() {
        return "Asistencia[" + idAsistencia + "] " + estado + " | " + fecha + " | " + horaIngreso;
    }
}
