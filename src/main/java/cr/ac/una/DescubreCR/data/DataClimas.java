/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;


import cr.ac.una.DescubreCR.domain.Clima;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Jordi
 */
   public class DataClimas extends ConectarDB {
    private static final String TBCLIMA = "tb_clima";
    
    private static final String CODIGO = "codigo";
    private static final String TEMPERATURA = "temperatura";
    private static final String TIPOCLIMA = "tipoClima";
    private static final String FECHA = "fecha";
    private static final String UNIDADC = "unidadC";
    private static final String INDICEUV = "indiceUV";
    private static final String PORCENTAJEHUMEDAD = "porcentajeHumedad";

     public Clima insertar(Clima clima) throws SQLException {
        String sql = "INSERT INTO " + TBCLIMA + " (codigo, temperatura, tipoClima, fecha, unidadC, indiceUV, porcentajeHumedad) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?);";
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1, clima.getCodigo());
            statement.setString(2, clima.getTemperatura());
            statement.setString(3, clima.getTipoClima());
            statement.setDate(4, new java.sql.Date(clima.getFecha().getTime()));  // Convert java.util.Date to java.sql.Date
            statement.setString(5, clima.getUnidadC());
            statement.setInt(6, clima.getIndiceUV());
            statement.setString(7, clima.getPorcentajeHumedad());

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
        return clima;
    }

    public boolean eliminar(String codigo) throws SQLException {
    String sql = "DELETE FROM " + TBCLIMA + " WHERE " + CODIGO + " = ?;";
    System.out.println("Data clima");
    Connection conexion = conectar();
    PreparedStatement statement = conexion.prepareStatement(sql);
    statement.setString(1, codigo);
    int resultado = statement.executeUpdate();
    System.out.println("result= " + resultado);
    statement.close();
    conexion.close();
    
    return (resultado == 1);
}

public LinkedList<Clima> getClimas() throws SQLException {
    LinkedList<Clima> climas = new LinkedList<>();
    String sql = "SELECT * FROM " + TBCLIMA + ";";
    Connection conexion = null;
    PreparedStatement statement = null;
    ResultSet result = null;

    try {
        conexion = conectar();
        statement = conexion.prepareStatement(sql);
        result = statement.executeQuery();

        while (result.next()) {
            Clima clima = new Clima();
            clima.setCodigo(result.getString(CODIGO));
            clima.setTemperatura(result.getString(TEMPERATURA));
            clima.setTipoClima(result.getString(TIPOCLIMA));
            clima.setFecha(result.getDate(FECHA));
            clima.setUnidadC(result.getString(UNIDADC));
            clima.setIndiceUV(result.getInt(INDICEUV));
            clima.setPorcentajeHumedad(result.getString(PORCENTAJEHUMEDAD));

            climas.add(clima);
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
    return climas;
}

    public boolean modificar(Clima clima) throws SQLException {
    Connection conexion = null;
    PreparedStatement statement = null;

    String sql = "UPDATE " + TBCLIMA + " SET " +
                 TEMPERATURA + "=?, " +
                 TIPOCLIMA + "=?, " +
                 FECHA + "=?, " +
                 UNIDADC + "=?, " +
                 INDICEUV + "=?, " +
                 PORCENTAJEHUMEDAD + "=? WHERE " + CODIGO + "=?";
    int resultado;

    try {
        conexion = conectar();
        statement = conexion.prepareStatement(sql);

        statement.setString(1, clima.getTemperatura());
        statement.setString(2, clima.getTipoClima());
        statement.setDate(3, new java.sql.Date(clima.getFecha().getTime()));
        statement.setString(4, clima.getUnidadC());
        statement.setInt(5, clima.getIndiceUV());
        statement.setString(6, clima.getPorcentajeHumedad());
        statement.setString(7, clima.getCodigo());

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

    
    public Clima buscar(String codigo) throws SQLException {
    Clima clima = null;
    String sql = "SELECT * FROM " + TBCLIMA + " WHERE " + CODIGO + " = ?";
    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {
        statement.setString(1, codigo);
        try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                clima = construirNuevoClima(result);
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.toString());
        throw ex;
    }
    System.out.println("Codigo en buscarData = " + codigo);
    return clima;
}

private Clima construirNuevoClima(ResultSet result) throws SQLException {
    Clima clima = new Clima();
    clima.setCodigo(result.getString(CODIGO));
    clima.setTemperatura(result.getString(TEMPERATURA));
    clima.setTipoClima(result.getString(TIPOCLIMA));
    clima.setFecha(result.getDate(FECHA));
    clima.setUnidadC(result.getString(UNIDADC));
    clima.setIndiceUV(result.getInt(INDICEUV));
    clima.setPorcentajeHumedad(result.getString(PORCENTAJEHUMEDAD));
    return clima;
}

}
    
