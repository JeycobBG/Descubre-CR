package cr.ac.una.DescubreCR.data;

import cr.ac.una.DescubreCR.domain.ColaboradorEmpresarial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Jordi
 */
public class DataColaboradores extends ConectarDB {
     private static final String TBCOLABORADOR = "tb_colaborador_empresarial";
    
    private static final String IDENTIFICADOR = "ide";
    private static final String NOMBRE = "nombreEmpresa";
    private static final String DESCRIPCION = "descripcionEmpresa";
    private static final String DIRECCION = "direccionEmpresa";
    private static final String TELEFONO = "telefonoEmpresa";
    private static final String SITIOWEB = "sitioWeb";
    private static final String ESTADO = "estadoAprobacion";
    private static final String TIPO = "tipoColaborador";
    private static final String REDES = "redesSociales";

    public ColaboradorEmpresarial insertar(ColaboradorEmpresarial colab) throws SQLException {
    
    String sql = "INSERT INTO " + TBCOLABORADOR + " (ide,nombreEmpresa, descripcionEmpresa, direccionEmpresa, telefonoEmpresa, sitioWeb, estadoAprobacion, tipoColaborador, redesSociales) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    Connection conexion = null;
    PreparedStatement statement = null;
    try {
        conexion = conectar();
        statement = conexion.prepareStatement(sql);
        
        statement.setString(1, colab.getIde());
        statement.setString(2, colab.getNombreEmpresa());
        statement.setString(3, colab.getDescripcionEmpresa());
        statement.setString(4, colab.getDireccionEmpresa());
        statement.setString(5, colab.getTelefonoEmpresa());
        statement.setString(6, colab.getSitioWeb());
        statement.setString(7, colab.getEstadoAprobacion());
        statement.setString(8, colab.getTipoColaborador());
        statement.setString(9, colab.getRedesSociales());

        int resultado = statement.executeUpdate();
        System.out.println("result= " + resultado);
    } finally {
        if (statement != null) {
            statement.close();
        }
        if (conexion != null) {
            conexion.close();
        }
    }
    return colab;
}
    public boolean eliminar(String ide) throws SQLException {
         String sql = "DELETE FROM "+TBCOLABORADOR+" WHERE "+IDENTIFICADOR+" =?;";
         System.out.println("Data colaborador");
        Connection conexion = conectar();
        PreparedStatement statement = conexion.prepareStatement(sql);
        statement.setString(1, ide);
        int resultado =statement.executeUpdate();
        System.out.println("result= "+resultado);
        statement.close();
        conexion.close();
        
        return (resultado==1);
    }
    
    public LinkedList<ColaboradorEmpresarial> getColaboradores() throws SQLException {
    LinkedList<ColaboradorEmpresarial> colaboradores = new LinkedList<>();
    String sql = "SELECT * FROM " + TBCOLABORADOR + ";";
    Connection conexion = null;
    PreparedStatement statement = null;
    ResultSet result = null;
    
    try {
        conexion = conectar();
        statement = conexion.prepareStatement(sql);
        result = statement.executeQuery();
        
        while (result.next()) {
            
            ColaboradorEmpresarial colab = new ColaboradorEmpresarial(
                result.getString("ide"),
                result.getString("nombreEmpresa"),
                result.getString("descripcionEmpresa"),
                result.getString("direccionEmpresa"),
                result.getString("telefonoEmpresa"),
                result.getString("sitioWeb"),
                result.getString("estadoAprobacion"),
                result.getString("tipoColaborador"),
                result.getString("redesSociales")
            );
            
            colaboradores.add(colab);
        }
    } finally {
        if (result != null) {
            result.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (conexion != null) {
            conexion.close();
        }
    }
    return colaboradores;
}
    public boolean modificar(ColaboradorEmpresarial colab) throws SQLException {
        Connection conexion = null;
        PreparedStatement statement = null;

        String sql = "UPDATE " + TBCOLABORADOR + " SET nombreEmpresa=?, descripcionEmpresa=?, direccionEmpresa=?, telefonoEmpresa=?, sitioWeb=?, "
                + "estadoAprobacion=?, tipoColaborador=?, redesSociales=? WHERE ide=? ";    
        int resultado;

        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            
            
            statement.setString(1, colab.getNombreEmpresa());
            statement.setString(2, colab.getDescripcionEmpresa());
            statement.setString(3, colab.getDireccionEmpresa());
            statement.setString(4, colab.getTelefonoEmpresa());
            statement.setString(5, colab.getSitioWeb());
            statement.setString(6, colab.getEstadoAprobacion());
            statement.setString(7, colab.getTipoColaborador());
            statement.setString(8, colab.getRedesSociales());
            statement.setString(9, colab.getIde());

            resultado = statement.executeUpdate();
            System.out.println("Resultado = " + resultado);

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
    
    public ColaboradorEmpresarial buscar(String ide) throws SQLException {
        ColaboradorEmpresarial colab = null;
        String sql = "SELECT * FROM " + TBCOLABORADOR + " WHERE " + IDENTIFICADOR + " = ?";
        try (Connection conexion = conectar();
            PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, ide);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    colab = construirNuevoColaborador(result);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw ex;
        }
        System.out.println("Identificador en buscarData = "+ide);
        return colab;
    }

    private ColaboradorEmpresarial construirNuevoColaborador(ResultSet result) throws SQLException {
        return new ColaboradorEmpresarial(
                result.getString("ide"),
                result.getString("nombreEmpresa"),
                result.getString("descripcionEmpresa"),
                result.getString("direccionEmpresa"),
                result.getString("telefonoEmpresa"),
                result.getString("sitioWeb"),
                result.getString("estadoAprobacion"),
                result.getString("tipoColaborador"),
                result.getString("redesSociales"));
                
        
    }
}
