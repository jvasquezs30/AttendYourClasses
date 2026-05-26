package modelo.usuario;
import modelo.academico.Clase;
import modelo.control.Asistencia;
import modelo.control.Justificacion;
import modelo.control.Reporte;
import java.util.ArrayList;
import java.util.List;

public class Docente extends Usuario {
    private String especialidad;
    private String departamento;
    private String tipoContrato;
    private List<Clase> clasesAsignadas;
    public Docente() {
        super();
        this.clasesAsignadas = new ArrayList<>();
    }
    public Docente(int idUsuario, String nombre, String apellido,String identificacion, String correo, String contrasena,String especialidad, String departamento, String tipoContrato) {
        super(idUsuario, nombre, apellido, identificacion, correo, contrasena);
        this.especialidad    = especialidad;
        this.departamento    = departamento;
        this.tipoContrato    = tipoContrato;
        this.clasesAsignadas = new ArrayList<>();
    }
    @Override
    public void consultarPerfil() {
    }

    @Override
    public String getRol() {
        return "DOCENTE";
    }
    public void iniciarClase(Clase clase) {
        if (clasesAsignadas.contains(clase)) {
            clase.iniciarClase();
        } else {
        }
    }

    public void registrarAsistencia(Clase clase, Estudiante estudiante, String estado, String hora) {
        if (!clasesAsignadas.contains(clase)) {
            return;
        }
        Asistencia a = new Asistencia(
            (int)(Math.random() * 1000),
            new java.util.Date(), hora, estado, ""
        );
        clase.agregarAsistencia(a);
        estudiante.agregarAsistencia(a);
    }
    public void registrarAsistencia(Clase clase, Estudiante estudiante, String estado, String hora, String observacion) {
        if (!clasesAsignadas.contains(clase)) {
            return;
        }
        Asistencia a = new Asistencia(
            (int)(Math.random() * 1000),
            new java.util.Date(), hora, estado, observacion
        );
        clase.agregarAsistencia(a);
        estudiante.agregarAsistencia(a);
    }

    public void configurarTolerancia(Clase clase, int minutos) {
        clase.setTolerancia(minutos);
    }

    public void revisarJustificacion(Justificacion j, boolean aprobada) {
        if (aprobada) {
            j.aprobarJustificacion();
        } else {
            j.rechazarJustificacion("Revisada por docente");
        }
    }
    public Reporte generarReporte(Clase clase, String tipo) {
        Reporte r = new Reporte(
            (int)(Math.random() * 1000),
            new java.util.Date(), tipo,
            clase.getAsistencias().size(), 0, 0
        );
        r.generarReporte(clase.getAsistencias());
        return r;
    }

    public void asignarClase(Clase clase) {
        if (clase == null) return;
        clasesAsignadas.add(clase);
    }
    public String  getEspecialidad()   
    { 
        return especialidad; }
    public String getDepartamento()    
    { 
        return departamento; }
    public String  getTipoContrato()    
    { 
        return tipoContrato; }
    public List<Clase> getClasesAsignadas()
    { 
        return clasesAsignadas; }

    public void setEspecialidad(String e)  
    { 
        this.especialidad = e; }
    public void setDepartamento(String d)  
    { 
        this.departamento = d; }
    public void setTipoContrato(String t)  
    { 
        this.tipoContrato = t; }
}
