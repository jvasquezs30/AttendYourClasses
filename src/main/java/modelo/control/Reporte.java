package modelo.control;
import java.util.Date;
import java.util.List;

public class Reporte {
    private int idReporte;
    private Date fechaGeneracion;
    private String tipoReporte;
    private int totalAsistencias;
    private int totalTardanzas;
    private int totalInasistencias;
    public Reporte() {
        this.fechaGeneracion = new Date();
    }
    public Reporte(int idReporte, Date fechaGeneracion, String tipoReporte,int totalAsistencias, int totalTardanzas, int totalInasistencias) {
        this.idReporte = idReporte;
        this.fechaGeneracion = fechaGeneracion;
        this.tipoReporte = tipoReporte;
        this.totalAsistencias  = totalAsistencias;
        this.totalTardanzas = totalTardanzas;
        this.totalInasistencias = totalInasistencias;
    }
    
    public void generarReporte(List<Asistencia> asistencias) {
        this.totalAsistencias = 0;
        this.totalTardanzas = 0;
        this.totalInasistencias = 0;

        for (Asistencia a : asistencias) {
            switch (a.getEstado().toLowerCase()) {
                case "presente":
                    totalAsistencias++;
                    break;
                case "tardanza":
                    totalTardanzas++;
                    break;
                case "ausente":
                    totalInasistencias++;
                    break;
            }
        }
    }

    public void mostrarEstadisticas() {
    }

    public void listarInasistencias(List<Asistencia> asistencias) {
        for (Asistencia a : asistencias) {
            if (a.getEstado().equalsIgnoreCase("Ausente")) {
            }
        }
    }

    public void listarTardanzas(List<Asistencia> asistencias) {
        for (Asistencia a : asistencias) {
            if (a.getEstado().equalsIgnoreCase("Tardanza")) {
            }
        }
    }

    public void exportarReporte() {
    }
   
    public void exportarReporte(String formato) {
    }
    public int    getIdReporte()         
    { 
        return idReporte; }
   
    public Date   getFechaGeneracion()   
    { 
        return fechaGeneracion; }
    
    public String getTipoReporte()        
    { 
        return tipoReporte; }
    
    public int    getTotalAsistencias()   
    { 
        return totalAsistencias; }
   
    public int    getTotalTardanzas()     
    { 
        return totalTardanzas; }
   
   
    public int    getTotalInasistencias() 
    { 
        return totalInasistencias; }

    
    public void setIdReporte(int i)          
    { 
        this.idReporte = i; }
   
    public void setFechaGeneracion(Date f)   
    { 
        this.fechaGeneracion = f; }
   
    
    public void setTipoReporte(String t)      
    { 
        this.tipoReporte = t; }
    
    public void setTotalAsistencias(int t)    
    {
        this.totalAsistencias = t; }
   
    public void setTotalTardanzas(int t)     
    {
        this.totalTardanzas = t; }
    
    public void setTotalInasistencias(int t) 
    { 
        this.totalInasistencias = t; }

    @Override
    public String toString() {
        return "Reporte[" + idReporte + "] " + tipoReporte + " | " + fechaGeneracion;
    }
}
