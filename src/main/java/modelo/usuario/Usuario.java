package modelo.usuario;
public abstract class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String identificacion;
    private String correo;
    private String contrasena;
    protected boolean sesionActiva;
    public Usuario() {
        this.sesionActiva = false;
    }
    public Usuario(int idUsuario, String nombre, String apellido,String identificacion, String correo, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.correo = correo;
        this.contrasena = contrasena;
        this.sesionActiva = false;
    }
    public void iniciarSesion(String correo, String contrasena) {
        if (this.correo.equals(correo) && this.contrasena.equals(contrasena)) {
            this.sesionActiva = true;
        } else {
        }
    }

    public void cerrarSesion() {
        this.sesionActiva = false;
    }

    public void actualizarDatos(String nuevoCorreo, String nuevaContrasena) {
        this.correo     = nuevoCorreo;
        this.contrasena = nuevaContrasena;
    }
    
    public abstract void consultarPerfil();

    
    public abstract String getRol();
    
    public String toString() {
        return "[" + getRol() + "] " + nombre + " " + apellido + " | ID: " + identificacion;
    }

    
    public String toString(boolean detallado) {
        if (detallado) {
            return toString() + " | Correo: " + correo + " | Sesión activa: " + sesionActiva;
        }
        return toString();
    }
    public int getIdUsuario()
    {
        return idUsuario;
    }
    public String getNombre() 
    { 
        return nombre; 
    }
    public String getApellido() 
    { 
        return apellido;
    }
    public String getIdentificacion() 
    { 
        return identificacion;
    }
    public String getCorreo()         
    { 
        return correo;
    }
    public boolean isSesionActiva()   
    { 
        return sesionActiva; 
    }

    public void setIdUsuario(int idUsuario)        
    {
        this.idUsuario = idUsuario;
    }
    public void setNombre(String nombre)             
    {
        this.nombre = nombre; 
    }
    public void setApellido(String apellido)         
    { 
        this.apellido = apellido;
    }
    public void setIdentificacion(String id)          
    { 
        this.identificacion = id; 
    }
    public void setCorreo(String correo)             
    { 
        this.correo = correo; 
    }
    public void setContrasena(String contrasena)     
    { 
        this.contrasena = contrasena; 
    }
}
