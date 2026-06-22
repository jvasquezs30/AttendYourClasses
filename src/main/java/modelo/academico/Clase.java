package modelo.academico;
import modelo.control.Asistencia;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Clase {
    private int idClase;
    private String tema;
    private Date fecha;
    private String horaInicio;
    private int duracion;
    private int tolerancia;
    private String aula;
    private boolean activa;
    private List<Asistencia> asistencias;
    public Clase() {
        this.asistencias = new ArrayList<>();
        this.activa = false;
        this.tolerancia = 10;
    }
    public Clase(int idClase, String tema, Date fecha, String horaInicio, int duracion, int tolerancia, String aula) {
        this.idClase = idClase;
        this.tema = tema;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracion = duracion;
        this.tolerancia = tolerancia;
        this.aula = aula;
        this.activa     = false;
        this.asistencias = new ArrayList<>();
    }
    public void iniciarClase() {
        this.activa = true;
    }

    public void finalizarClase() {
        this.activa = false;
    }

    public void configurarHorario(String horaInicio, int duracion) {
        this.horaInicio = horaInicio;
        this.duracion = duracion;
    }
    public void agregarAsistencia(Asistencia a) {
        asistencias.add(a);
    }

    public boolean esTardanza(String horaIngreso) {
        return minutosDespuesInicio(horaIngreso) > tolerancia;
    }

    public int minutosDespuesInicio(String horaIngreso) {
        try {
            LocalTime inicio = LocalTime.parse(horaInicio);
            LocalTime llegada = LocalTime.parse(horaIngreso);
            int minutos = (llegada.getHour() * 60 + llegada.getMinute()) - (inicio.getHour() * 60 + inicio.getMinute());
            return Math.max(minutos, 0);
        } catch (DateTimeParseException ex) {
            return 0;
        }
    }
    public int getIdClase()     
    { 
        return idClase; 
    }
    public String  getTema()        
    { 
        return tema; 
    }
    public Date  getFecha()       
    {
        return fecha; 
    }
    public String  getHoraInicio()  
    { 
        return horaInicio;
    }
    public int getDuracion()    
    {
        return duracion; 
    }
    public int  getTolerancia()  
    { 
        return tolerancia;
    }
    public String getAula()        
    { 
        return aula; 
    }
    public boolean isActiva()       
    { 
        return activa; 
    }
    public List<Asistencia> getAsistencias()
    {
        return asistencias; 
    }

    public void setIdClase(int i)       
    { 
        this.idClase = i; 
    }
    public void setTema(String t)       
    { 
        this.tema = t; 
    }
    public void setFecha(Date f)        
    { 
        this.fecha = f; 
    }
    public void setHoraInicio(String h) 
    { 
        this.horaInicio = h; 
    }
    public void setDuracion(int d)      
    { 
        this.duracion = d; 
    }
    
    public void setTolerancia(int t)   
    { 
        this.tolerancia = t; 
    }
    
    public void setAula(String a)      
    { 
        this.aula = a; 
    }

    @Override
    public String toString() {
        return "Clase[" + idClase + "] " + tema + " | " + fecha + " | Aula: " + aula;
    }
}
