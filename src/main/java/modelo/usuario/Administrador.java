/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.usuario;
import model.academico.Institucion;
import java.util.List;

/**
 *
 * @author Oscar
 */

public class Administrador extends Usuario {
    private String cargo;
    private String nivelAcceso;
    public Administrador() {
        super();
        this.nivelAcceso = "TOTAL";
    }
    public Administrador(int idUsuario, String nombre, String apellido,String identificacion, String correo, String contrasena,String cargo) {
        super(idUsuario, nombre, apellido, identificacion, correo, contrasena);
        this.cargo = cargo;
        this.nivelAcceso = "TOTAL";
    }
    @Override
    public void consultarPerfil() {
    }

    @Override
    public String getRol() {
        return "ADMINISTRADOR";
    }
    public void gestionarUsuarios(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
        }
    }

    public void registrarUsuario(List<Usuario> usuarios, Usuario nuevo) {
        usuarios.add(nuevo);
    }

    public void eliminarUsuario(List<Usuario> usuarios, int idUsuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario() == idUsuario) {
                usuarios.remove(i);
                break;
            }
        }
    }

    public void configurarSistema(Institucion inst, String nuevaDireccion) {
        inst.setDireccion(nuevaDireccion);
    }
    public void consultarEstadisticas() {
    }

    public void consultarEstadisticas(Institucion inst) {
        inst.listarCursos();
    }
    public String getCargo()       
    { 
        return cargo; }
    public String getNivelAcceso() 
    { 
        return nivelAcceso; }

    public void setCargo(String cargo)             
    {
        this.cargo = cargo; }
    public void setNivelAcceso(String nivelAcceso)
    { 
        this.nivelAcceso = nivelAcceso; }
}
