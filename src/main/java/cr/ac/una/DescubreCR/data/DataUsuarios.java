/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;

import static cr.ac.una.DescubreCR.data.ConectarDB.conectar;
import cr.ac.una.DescubreCR.domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author JEYCOB
 */
public class DataUsuarios {
    
    private static final String TBUSUARIOS = "tb_usuarios";
    private static final String CEDULA = "cedula";
    private static final String NOMBRE = "nombre";
    private static final String APELLIDO = "apellido";
    private static final String IDIOMA = "idioma";
    private static final String NOMBREUSUARIO = "nombreUsuario";
    private static final String CONTRASEÑA = "contraseña";

    public boolean insertar(Usuario user) throws SQLException {
        String sql = "INSERT INTO " + TBUSUARIOS + " ("+NOMBRE+", "+APELLIDO+", "+CEDULA+", "+IDIOMA+", nacionalidad, fechaNacimiento, telefono, nombreUsuario, contraseña, tipoUsuario, correo, canton, provincia, fechaRegistro) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection conexion = null;
        PreparedStatement statement = null;
        int resultado = 2;
        
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);

            statement.setString(1, user.getNombre());
            statement.setString(2, user.getApellido());
            statement.setString(3, user.getCedula());
            statement.setString(4, user.getIdioma());
            statement.setString(5, user.getNacionalidad());
            statement.setDate(6, new java.sql.Date(user.getFechaNacimiento().getTime()));
            statement.setString(7, user.getTelefono());
            statement.setString(8, user.getNombreUsuario());
            statement.setString(9, user.getContraseña());
            statement.setString(10, user.getTipoUsuario());
            statement.setString(11, user.getCorreo());
            statement.setString(12, user.getCanton());
            statement.setString(13, user.getProvincia());
            statement.setDate(14, new java.sql.Date(user.getFechaRegistro().getTime()));

            resultado = statement.executeUpdate();

        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
        return resultado == 1;
    }

    
    public LinkedList<Usuario> getUsuarios() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        String sql = "SELECT * FROM " + TBUSUARIOS + ";";
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                Usuario user = new Usuario(
                    result.getString("nombre"),
                    result.getString("apellido"),
                    result.getString("cedula"),
                    result.getString("idioma"),
                    result.getString("nacionalidad"),
                    result.getDate("fechaNacimiento"),
                    result.getString("telefono"),
                    result.getString("nombreUsuario"),
                    result.getString("contraseña"),
                    result.getString("tipoUsuario"),
                    result.getString("correo"),
                    result.getString("canton"),
                    result.getString("provincia"),
                    result.getDate("fechaRegistro")
                );
                usuarios.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        return usuarios;
    }


    public boolean eliminar(String cedula) {
        String sql = "DELETE FROM "+TBUSUARIOS+" WHERE "+CEDULA+" = ?;";

        try (
            Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(sql);
        ) {
            statement.setString(1, cedula);

            int resultado = statement.executeUpdate();

            return resultado == 1;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    
    public boolean modificar(Usuario user) throws SQLException {
        Connection conexion = null;
        PreparedStatement statement = null;
        String sql = "UPDATE " + TBUSUARIOS + " SET nombre=?, apellido=?, idioma=?, nacionalidad=?, fechaNacimiento=?, telefono=?, nombreUsuario=?, contraseña=?,tipoUsuario=?, correo=?,canton=?,provincia=?,fechaRegistro=? WHERE cedula=?";
        int resultado;

        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);

            statement.setString(1, user.getNombre());
            statement.setString(2, user.getApellido());
            statement.setString(3, user.getIdioma());
            statement.setString(4, user.getNacionalidad());
            statement.setDate(5, new java.sql.Date(user.getFechaNacimiento().getTime()));
            statement.setString(6, user.getTelefono());
            statement.setString(7, user.getNombreUsuario());
            statement.setString(8, user.getContraseña());
            statement.setString(9, user.getTipoUsuario());
            statement.setString(10, user.getCorreo());
            statement.setString(11, user.getCanton());
            statement.setString(12, user.getProvincia());
            statement.setDate(13, new java.sql.Date(user.getFechaRegistro().getTime()));

            statement.setString(14, user.getCedula());

            resultado = statement.executeUpdate();

        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
            return (resultado == 1);
    }

    public Usuario buscarPorCedula(String cedula) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM " + TBUSUARIOS + " WHERE " + CEDULA + " = ?";

        try (
            Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(sql);
        ) {
            statement.setString(1, cedula);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    usuario = construirUsuarioDesdeResultSet(result);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw ex;
        }

        return usuario;
    }
    
    
    public boolean login(String nameUser, String contraseña) throws SQLException {
        String sql = "SELECT * FROM " + TBUSUARIOS + " WHERE " + NOMBREUSUARIO + " = ? AND " + CONTRASEÑA + " = ?";

        try (
            Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(sql);
        ) {
            statement.setString(1, nameUser);
            statement.setString(2, contraseña);
            
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw ex;
        }

        return false;
    }


    private Usuario construirUsuarioDesdeResultSet(ResultSet result) throws SQLException {
        return new Usuario(
                result.getString("nombre"),
                result.getString("apellido"),
                result.getString("cedula"),
                result.getString("idioma"),
                result.getString("nacionalidad"),
                result.getDate("fechaNacimiento"),
                result.getString("telefono"),
                result.getString("nombreUsuario"),
                result.getString("contraseña"),
                result.getString("tipoUsuario"),
                result.getString("correo"),
                result.getString("canton"),
                result.getString("provincia"),
                result.getDate("fechaRegistro")
        );
    }
}
