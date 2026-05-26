package modelo.usuario;
import modelo.academico.Curso;
import java.util.List;

public class DocenteCoordinador extends Docente {
    private String areaCoordenada;
    private int    aniosExperiencia;
    public DocenteCoordinador() {
        super();
        this.areaCoordenada  = "Sin asignar";
        this.aniosExperiencia = 0;
    }
    public DocenteCoordinador(int idUsuario, String nombre, String apellido, String identificacion, String correo, String contrasena,String especialidad, String departamento, String tipoContrato, String areaCoordenada, int aniosExperiencia) {
        super(idUsuario, nombre, apellido, identificacion, correo, contrasena,
              especialidad, departamento, tipoContrato);
        this.areaCoordenada   = areaCoordenada;
        this.aniosExperiencia = aniosExperiencia;
    }
    @Override
    public void consultarPerfil() {
        super.consultarPerfil();
    }

    @Override
    public String getRol() {
        return "DOCENTE-COORDINADOR";
    }
    public void supervisarCurso(Curso curso) {
        curso.listarEstudiantes();
    }

    public void asignarDocenteACurso(Docente docente, Curso curso) {
        curso.asignarDocente(docente);
    }
    public void supervisarCurso(Curso curso, boolean generarReporte) {
        supervisarCurso(curso);
        if (generarReporte) {
        }
    }
    public String getAreaCoordenada()    
    {
        return areaCoordenada; }
   
    public int    getAniosExperiencia()  
    { 
        return aniosExperiencia; }

    public void setAreaCoordenada(String a)  
    { 
        this.areaCoordenada = a; }
    public void setAniosExperiencia(int a)   
    { 
        this.aniosExperiencia = a; }
}
