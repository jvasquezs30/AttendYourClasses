package modelo.academico;
import modelo.usuarios.Usuario;
import java.util.ArrayList;
import java.util.List;

public class Institucion {
    private int idInstitucion;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correoInstitucional;
    private List<Curso>cursos;
    private List<Usuario> usuarios;
    public Institucion() {
        this.cursos= new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }
    public Institucion(int idInstitucion, String nombre, String direccion,String telefono, String correoInstitucional) {
        this.idInstitucion  = idInstitucion;
        this.nombre= nombre;
        this.direccion= direccion;
        this.telefono = telefono;
        this.correoInstitucional = correoInstitucional;
        this.cursos= new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }
    public void registrarInstitucion() {
    }

    public void actualizarInformacion(String telefono, String correo) {
        this.telefono  = telefono;
        this.correoInstitucional = correo;
    }

    public void listarCursos() {
        for (Curso c : cursos) {
        }
    }

    public void consultarUsuarios() {
        for (Usuario u : usuarios) {
        }
    }

    public void agregarCurso(Curso c)  
    { 
        cursos.add(c);
    }
    public void agregarUsuario(Usuario u)
    { 
        usuarios.add(u); 
    }
    public int getIdInstitucion()      
    { 
        return idInstitucion; 
    }
    public String getNombre()           
    {
        return nombre;
    }
    public String getDireccion(){
        return direccion; 
    }
    public String getTelefono()          
    { 
        return telefono;
    }
    public String getCorreoInstitucional()
    { 
        return correoInstitucional; 
    }
    public List<Curso>  getCursos()            
    { 
        return cursos; 
    }
    public List<Usuario> getUsuarios()          
    { 
        return usuarios; 
    }

    
    public void setIdInstitucion(int i)        
    { 
        this.idInstitucion = i; 
    }
    public void setNombre(String n)            
    { 
        this.nombre = n; 
    }
    public void setDireccion(String d)         
    { 
        this.direccion = d; 
    }
    public void setTelefono(String t)         
    { 
        this.telefono = t; 
    }
    public void setCorreoInstitucional(String c)
    { this.correoInstitucional = c; }

    @Override
    public String toString() {
        return "Institución: " + nombre + " | " + correoInstitucional;
    }
}
