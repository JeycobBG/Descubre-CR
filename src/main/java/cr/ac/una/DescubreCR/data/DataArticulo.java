/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;

import cr.ac.una.DescubreCR.domain.Articulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author JEYCOB
 */
public class DataArticulo extends ConectarDB{
    private static final String TB_ARTICULOS = "tb_articulo";
    private static final String IDARTICULO = "id";
    private static final String IDENTIFICADOR = "identificador";
    private static final String TITULO = "titulo";
    private static final String TEMA = "tema";
    private static final String NOMBRE_AUTOR = "nombreAutor";
    private static final String FECHA = "fecha";
    private static final String ACERCA_DEL_AUTOR = "acercaDelAutor";
    private static final String TEXTO_ARTICULO = "textoArticulo";
    
    public static void actualizar(Articulo articulo) throws SQLException {
    String sql = "UPDATE " + TB_ARTICULOS + " SET " +IDENTIFICADOR+" = ?,"+ TITULO + " = ?, " + TEMA + " = ?, " +
            NOMBRE_AUTOR + " = ?, " + FECHA + " = ?, " + ACERCA_DEL_AUTOR + " = ?, " + 
            TEXTO_ARTICULO + " = ? WHERE " + IDARTICULO + " = ?";
    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {
        statement.setString(1, articulo.getIdentificador());
        statement.setString(2, articulo.getTitulo());
        statement.setString(3, articulo.getTema());
        statement.setString(4, articulo.getNombreAutor());
        Date fecha =articulo.getFecha();     
        Timestamp timestamp = new Timestamp(fecha.getTime());
        statement.setTimestamp(5, timestamp); 
        statement.setString(6, articulo.getAcercaDelAutor());
        statement.setString(7, articulo.getTextoArticulo());
        statement.setInt(8, articulo.getIdArticulo());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("No se pudo actualizar el artículo con ID: " + articulo.getIdArticulo());
        }

        statement.executeUpdate();
    }
}

    public static Articulo obtenerPorID(int idArticulo) throws SQLException {
        String sql = "SELECT * FROM " + TB_ARTICULOS + " WHERE id = ?";
        try (Connection conexion = conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idArticulo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setIdArticulo(resultSet.getInt(IDARTICULO));
                    articulo.setIdentificador(resultSet.getString(IDENTIFICADOR));
                    articulo.setTitulo(resultSet.getString(TITULO));
                    articulo.setTema(resultSet.getString(TEMA));
                    articulo.setNombreAutor(resultSet.getString(NOMBRE_AUTOR));
                    articulo.setFecha(resultSet.getDate(FECHA));
                    articulo.setAcercaDelAutor(resultSet.getString(ACERCA_DEL_AUTOR));
                    articulo.setTextoArticulo(resultSet.getString(TEXTO_ARTICULO));
                    return articulo;
                } else {
                    return null;
                }
            }
        }
    }
      public static Articulo obtenerPorIdentificador(String identificador) throws SQLException {
        String sql = "SELECT * FROM " + TB_ARTICULOS + " WHERE identificador = ?";
        try (Connection conexion = conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, identificador);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setIdentificador(resultSet.getString(IDENTIFICADOR));
                    articulo.setTitulo(resultSet.getString(TITULO));
                    articulo.setTema(resultSet.getString(TEMA));
                    articulo.setNombreAutor(resultSet.getString(NOMBRE_AUTOR));
                    articulo.setFecha(resultSet.getDate(FECHA));
                    articulo.setAcercaDelAutor(resultSet.getString(ACERCA_DEL_AUTOR));
                    articulo.setTextoArticulo(resultSet.getString(TEXTO_ARTICULO));
                    return articulo;
                } else {
                    return null;
                }
            }
        }
    }

    public boolean eliminar(int idArticulo) throws SQLException {
    String sql = "DELETE FROM " + TB_ARTICULOS + " WHERE id = ?";
    Connection conexion = conectar();
    PreparedStatement statement = conexion.prepareStatement(sql);
    statement.setInt(1, idArticulo);
    int resultado = statement.executeUpdate();
    statement.close();
    conexion.close();
    return (resultado == 1);
    }

    public Articulo insertar(Articulo articulo) throws SQLException {
    String sql = "INSERT INTO " + TB_ARTICULOS + " (" + IDENTIFICADOR + ", " + TITULO + ", " + TEMA + ", " +
            NOMBRE_AUTOR + ", " + FECHA + ", " + ACERCA_DEL_AUTOR + ", " + TEXTO_ARTICULO +
            ") VALUES (?, ?, ?, ?, ?, ?, ?)";
    Connection conexion = conectar();
    PreparedStatement statement = conexion.prepareStatement(sql);
    statement.setString(1, articulo.getIdentificador());
    statement.setString(2, articulo.getTitulo());
    statement.setString(3, articulo.getTema());
    statement.setString(4, articulo.getNombreAutor());
    
    Date fecha =articulo.getFecha();     
    Timestamp timestamp = new Timestamp(fecha.getTime());
    statement.setTimestamp(5, timestamp); 
    
    statement.setString(6, articulo.getAcercaDelAutor());
    statement.setString(7, articulo.getTextoArticulo());
    statement.executeUpdate();
    statement.close();
    conexion.close();
    return articulo;
    }


    public LinkedList<Articulo> obtenerArticulos() throws SQLException {
        LinkedList<Articulo> articulos = new LinkedList<>();
        String sql = "SELECT * FROM " + TB_ARTICULOS;
        Connection conexion = conectar();
        PreparedStatement statement = conexion.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Articulo articulo = new Articulo();
            articulo.setIdArticulo(result.getInt(IDARTICULO));
            articulo.setIdentificador(result.getString(IDENTIFICADOR));
            articulo.setTitulo(result.getString(TITULO));
            articulo.setTema(result.getString(TEMA));
            articulo.setNombreAutor(result.getString(NOMBRE_AUTOR));
            articulo.setFecha(result.getDate(FECHA));
            articulo.setAcercaDelAutor(result.getString(ACERCA_DEL_AUTOR));
            articulo.setTextoArticulo(result.getString(TEXTO_ARTICULO));
            articulos.add(articulo);
        }
        statement.close();
        conexion.close();
        return articulos;
    }
}
