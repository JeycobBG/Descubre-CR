/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;

import cr.ac.una.DescubreCR.domain.Persona;
import cr.ac.una.DescubreCR.domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
/**
 *
 * @author kvene
 */
public class DataUsuarios extends ConectarDB{
    private static final String TBPERSONAS = "tb_persona";
    private static final String ID = "id";
    private static final String CEDULA = "cedula";
    private static final String NOMBRE = "nombre";
    private static final String APELLIDO = "apellido";
    private static final String IDIOMA = "idioma";
    private static final String NACIONALIDAD = "nacionalidad";
    private static final String FECHANACIMIENTO = "fechaNacimiento";
    private static final String TELEFONO = "telefono";
    
    private static final String TBUSUARIOS = "tb_usuario";
    private static final String NOMBREUSUARIO = "nombreUsuario";
    private static final String CONTRASENA = "contraseña";
    private static final String TIPOUSUARIO = "tipoUsuario";
    private static final String CORREO = "correo";
    private static final String FECHAREGISTRO = "fechaRegistro";
    private static final String IDPERSONA = "idPersona";
    
    public boolean insertarPersona(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO " + TBPERSONAS + " ("+NOMBRE+", "+APELLIDO+", "
                + ""+CEDULA+", "+IDIOMA+", "+NACIONALIDAD+", "+FECHANACIMIENTO+", "
                + ""+TELEFONO+") " +
                
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        Connection conexion = null;
        PreparedStatement statement = null;
        int resultado = 2;
        
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getCedula());
            statement.setString(4, usuario.getIdioma());
            statement.setString(5, usuario.getNacionalidad());
            statement.setDate(6, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
            statement.setString(7, usuario.getTelefono());
            
            resultado = statement.executeUpdate();
            insertarUsuario(usuario,conexion);
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

    public boolean insertarUsuario(Usuario user, Connection conexion) throws SQLException {
        String sql = "INSERT INTO " + TBUSUARIOS + " ("+NOMBREUSUARIO+", "+CONTRASENA+", "+TIPOUSUARIO+", "
                +CORREO+", "+FECHAREGISTRO+", " + IDPERSONA + ") " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement statement = null;
        int resultado = 0;

        try {
            statement = conexion.prepareStatement(sql);

            statement.setString(1, user.getNombreUsuario());
            statement.setString(2, user.getContraseña());
            statement.setString(3, user.getTipoUsuario());
            statement.setString(4, user.getCorreo());
            statement.setDate(5, new java.sql.Date(user.getFechaRegistro().getTime()));
            statement.setInt(6, buscarID(user.getCedula(), conexion));
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
                Usuario user = construirUsuarioDesdeResultSet(result, conexion);
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


    public boolean eliminarPersona(String cedula) {
        String sql = "DELETE FROM " + TBPERSONAS + " WHERE "+CEDULA+" = ?;";

        try (
            Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(sql);
        ) {
            statement.setString(1, cedula);
            
            this.eliminarUsuario(this.buscarID(cedula, conexion), conexion);
            
            int resultado = statement.executeUpdate();

            return resultado == 1;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    private boolean eliminarUsuario(int idPersona, Connection conexion) {
        String sql = "DELETE FROM "+TBUSUARIOS+" WHERE "+IDPERSONA+" = ?;";

        try (
            PreparedStatement statement = conexion.prepareStatement(sql);
        ) {
            statement.setInt(1, idPersona);

            int resultado = statement.executeUpdate();

            return resultado == 1;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean modificarUsuario(Usuario user, Connection conexion) throws SQLException {
        
        String sql = "UPDATE " + TBUSUARIOS + " SET nombreUsuario=?, contraseña=?, tipoUsuario=?, "
                    + "correo=?, fechaRegistro=? WHERE idPersona=?";

        
        int resultado;
        
        PreparedStatement statement = conexion.prepareStatement(sql);

        statement.setString(1, user.getNombreUsuario());
        statement.setString(2, user.getContraseña());
        statement.setString(3, user.getTipoUsuario());
        statement.setString(4, user.getCorreo());
        statement.setDate(5, new java.sql.Date(user.getFechaRegistro().getTime()));

        statement.setInt(6, buscarID(user.getCedula(), conexion));

        resultado = statement.executeUpdate();
        
        statement.close();
        
        System.out.println(resultado + " Resultado 1");
        return (resultado == 1);
    }
    
    
    
    public boolean modificarPersona(Usuario user) throws SQLException {
        
        String sql = "UPDATE " + TBPERSONAS + " SET nombre=?, "
                + "apellido=?, idioma=?, nacionalidad=?, fechaNacimiento=?, "
                + "telefono=? WHERE cedula=?";
        
        System.out.println("Modify persona");
        int resultado;
        Connection conexion = conectar();
        PreparedStatement statement = conexion.prepareStatement(sql);
        System.out.println("Data Modificar " + user.toString());
        System.out.println("Modificar persona");
        
        if(!this.modificarUsuario(user, conexion)){
            System.out.println("Error modificar user");
            return false; 
        }
            
        statement.setString(1, user.getNombre());
        statement.setString(2, user.getApellido());
        statement.setString(3, user.getIdioma());
        statement.setString(4, user.getNacionalidad());
        statement.setDate(5, new java.sql.Date(user.getFechaNacimiento().getTime()));
        statement.setString(6, user.getTelefono());

        statement.setString(7, user.getCedula());

        resultado = statement.executeUpdate();
        
        System.out.println("Modificada");
        
        statement.close();
        conexion.close();
        
        return (resultado == 1);
    }
    
    
    public Usuario buscarPorCedula(String cedula) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM " + TBPERSONAS + " WHERE " + CEDULA + " = ?";

        try (
            Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(sql);
        ) {
            statement.setString(1, cedula);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    usuario = construirUsuarioDesdeResultSet(result, conexion);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw ex;
        }

        return usuario;
    }
    
    public Usuario buscarPersonaCedula(String cedula) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM " + TBPERSONAS + " WHERE " + CEDULA + " = ?";

        try (
            Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(sql);
        ) {
            statement.setString(1, cedula);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    usuario = buscarUsuario(result.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw ex;
        }
        return usuario;
    }
    
    private int buscarID(String cedula, Connection conexion) throws SQLException {
        String sql = "SELECT * FROM " + TBPERSONAS + " WHERE " + CEDULA + " = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql);) {
            statement.setString(1, cedula);
            
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("id");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw ex;
        }
        return 0;
    }
    
    public Persona buscarPersona(int id, Connection conexion) throws SQLException {
        Persona persona = null;
        String sql = "SELECT * FROM " + TBPERSONAS + " WHERE " + ID + " = ?";
        System.out.println("Aqui 2 id " + id);

        PreparedStatement statement = conexion.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            persona = construirPersonaDesdeResultSet(result);
        }

        return persona;
    }
    

    public Usuario buscarUsuario(int id) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM " + TBUSUARIOS + " WHERE " + IDPERSONA + " = ?";
        
        try (
            Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(sql);
        ) {
            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    usuario = construirUsuarioDesdeResultSet(result, conexion);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw ex;
        }
        return usuario;
    }
    
    
    public boolean login(String nameUser, String contraseña) throws SQLException {
        String sql = "SELECT * FROM " + TBUSUARIOS + " WHERE " + NOMBREUSUARIO + " = ? AND " + CONTRASENA + " = ?";

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
    
    public Page<Usuario> listar(Pageable pageable) throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();
        String countSql = "SELECT COUNT(*) FROM " + TBUSUARIOS;
        String selectSql = "SELECT * FROM " + TBUSUARIOS + " LIMIT ? OFFSET ?";
        Connection conexion = conectar();
        PreparedStatement countStatement = conexion.prepareStatement(countSql);
        ResultSet countRs = countStatement.executeQuery();
        countRs.next();
        int total = countRs.getInt(1);

        PreparedStatement selectStatement = conexion.prepareStatement(selectSql);
        selectStatement.setInt(1, pageable.getPageSize());
        selectStatement.setInt(2, (int) pageable.getOffset());

        ResultSet rs = selectStatement.executeQuery();
        
        while (rs.next()) {
            Usuario usuario = construirUsuarioDesdeResultSet(rs, conexion);
            usuarios.add(usuario);
        }

    return new PageImpl<>(usuarios, pageable, total);
}



    private Usuario construirUsuarioDesdeResultSet(ResultSet result, Connection conexion) throws SQLException {
        System.out.println("Aqui 1");
        Persona persona = buscarPersona(result.getInt("idPersona"), conexion);
        return new Usuario(
            result.getString("nombreUsuario"),
            result.getString("contraseña"),
            result.getString("tipoUsuario"),
            result.getString("correo"),
            result.getDate("fechaRegistro"),
            result.getInt("idPersona"),
            persona.getNombre(),
            persona.getApellido(),
            persona.getCedula(),
            persona.getIdioma(),
            persona.getNacionalidad(),
            persona.getFechaNacimiento(),
            persona.getTelefono()
        );
    }
    
    private Persona construirPersonaDesdeResultSet(ResultSet result) throws SQLException {
        return new Persona(
                result.getString("nombre"),
                result.getString("apellido"),
                result.getString("cedula"),
                result.getString("idioma"),
                result.getString("nacionalidad"),
                result.getDate("fechaNacimiento"),
                result.getString("telefono"));
    }
    
}
