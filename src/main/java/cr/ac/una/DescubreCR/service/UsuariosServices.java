/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.DataUsuarios;
import cr.ac.una.DescubreCR.domain.Persona;
import cr.ac.una.DescubreCR.domain.Usuario;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author kvene
 */
public class UsuariosServices {

    public static LinkedList<Usuario> getUsuarios(){
        return new DataUsuarios().getUsuarios();
    }

    public static boolean eliminar(String cedula) {
        return new DataUsuarios().eliminarPersona(cedula);
    }
    
    public static boolean insertar(Usuario usuario) throws SQLException {
        usuario.setContraseña(encriptar(usuario.getContraseña()));
        return new DataUsuarios().insertarUsuario(usuario);
    }
    
    public static boolean insertar(Persona persona) throws SQLException {
        return new DataUsuarios().insertarPersona(persona);
    }
    
    public static String encriptar(String passwordSinEncriptar){
        return DigestUtils.md5Hex(passwordSinEncriptar);
    }
    
    public static boolean modificar(Usuario usuario) {
        try {
            return(new DataUsuarios().modificarPersona(usuario));
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static Usuario buscar(String cedula) {
        Usuario usuario = null;
        try {
            usuario = new DataUsuarios().buscarPersonaCedula(cedula);
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }
    
    public static boolean login(String nameUser, String contraseña) throws SQLException {
        return new DataUsuarios().login(nameUser, contraseña);
    }
    
    public static Page<Usuario> listar(Pageable pageable) throws SQLException{
        return new DataUsuarios().listar(pageable);
    }
    
}
