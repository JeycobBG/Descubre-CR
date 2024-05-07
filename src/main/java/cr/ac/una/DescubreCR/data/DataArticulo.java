package cr.ac.una.DescubreCR.data;

import cr.ac.una.DescubreCR.domain.Articulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    private static final String DESCRIPCION = "descripcion";
    private static final String NOMBRE_AUTOR = "nombreAutor";
    private static final String FECHA = "fecha";
    private static final String ACERCA_DEL_AUTOR = "acercaDelAutor";
    private static final String TEXTO_ARTICULO = "textoArticulo";
    
    public static void actualizar(Articulo articulo) throws SQLException {
    String sql = "UPDATE " + TB_ARTICULOS + " SET " +IDENTIFICADOR+" = ?,"+ TITULO + " = ?, " + TEMA + " = ?, " +DESCRIPCION+" = ?, "+
            NOMBRE_AUTOR + " = ?, " + FECHA + " = ?, " + ACERCA_DEL_AUTOR + " = ?, " + 
            TEXTO_ARTICULO + " = ? WHERE " + IDARTICULO + " = ?";
    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {
        statement.setString(1, articulo.getIdentificador());
        statement.setString(2, articulo.getTitulo());
        statement.setString(3, articulo.getTema());
        statement.setString(4, articulo.getDescripcion());
        statement.setString(5, articulo.getNombreAutor());
        Date fecha =articulo.getFecha();     
        Timestamp timestamp = new Timestamp(fecha.getTime());
        statement.setTimestamp(6, timestamp); 
        statement.setString(7, articulo.getAcercaDelAutor());
        statement.setString(8, articulo.getTextoArticulo());
        statement.setInt(9, articulo.getIdArticulo());
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
                    articulo.setDescripcion(resultSet.getString(DESCRIPCION));
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
                    articulo.setDescripcion(resultSet.getString(DESCRIPCION));
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
    String sql = "INSERT INTO " + TB_ARTICULOS + " (" + IDENTIFICADOR + ", " + TITULO + ", " + TEMA + ", " +DESCRIPCION+","+
            NOMBRE_AUTOR + ", " + FECHA + ", " + ACERCA_DEL_AUTOR + ", " + TEXTO_ARTICULO +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    Connection conexion = conectar();
    PreparedStatement statement = conexion.prepareStatement(sql);
    statement.setString(1, articulo.getIdentificador());
    statement.setString(2, articulo.getTitulo());
    statement.setString(3, articulo.getTema());
    statement.setString(4, articulo.getDescripcion());
    statement.setString(5, articulo.getNombreAutor());
    
    Date fecha =articulo.getFecha();     
    Timestamp timestamp = new Timestamp(fecha.getTime());
    statement.setTimestamp(6, timestamp); 
    
    statement.setString(7, articulo.getAcercaDelAutor());
    statement.setString(8, articulo.getTextoArticulo());
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
            articulo.setDescripcion(result.getString(DESCRIPCION));
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
    
     public Page<Articulo> obtenerArticulosAdmi(Pageable pageable) throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        String countSql = "SELECT COUNT(*) FROM " + TB_ARTICULOS;
        String selectSql = "SELECT * FROM " + TB_ARTICULOS + " LIMIT ? OFFSET ?";
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
            Articulo articulo = new Articulo();
            articulo.setIdArticulo(rs.getInt(IDARTICULO));
            articulo.setIdentificador(rs.getString(IDENTIFICADOR));
            articulo.setTitulo(rs.getString(TITULO));
            articulo.setTema(rs.getString(TEMA));
            articulo.setDescripcion(rs.getString(DESCRIPCION));
            articulo.setNombreAutor(rs.getString(NOMBRE_AUTOR));
            articulo.setFecha(rs.getDate(FECHA));
            articulo.setAcercaDelAutor(rs.getString(ACERCA_DEL_AUTOR));
            articulo.setTextoArticulo(rs.getString(TEXTO_ARTICULO));
            articulos.add(articulo);
        }
         return new PageImpl<>(articulos, pageable, total);
    }
     
     
    public  Page<Articulo> obtenerPorTitulo(String titulo, Pageable pageable) throws SQLException {
    List<Articulo> articulos = new ArrayList<>();
    String countSql;
    String selectSql;
    if (titulo == null || titulo.trim().isEmpty()) {
        countSql = "SELECT COUNT(*) FROM " + TB_ARTICULOS;
        selectSql = "SELECT * FROM " + TB_ARTICULOS + " LIMIT ? OFFSET ?";
    } else {
        countSql = "SELECT COUNT(*) FROM " + TB_ARTICULOS + " WHERE titulo LIKE ?";
        selectSql = "SELECT * FROM " + TB_ARTICULOS + " WHERE titulo LIKE ? LIMIT ? OFFSET ?";
    }

    try (Connection conexion = conectar();
         PreparedStatement countStatement = conexion.prepareStatement(countSql);
         PreparedStatement selectStatement = conexion.prepareStatement(selectSql)) {

        if (titulo != null && !titulo.trim().isEmpty()) {
            countStatement.setString(1, "%" + titulo + "%");
            selectStatement.setString(1, "%" + titulo + "%");
            selectStatement.setInt(2, pageable.getPageSize());
            selectStatement.setInt(3, (int) pageable.getOffset());
        } else {
            selectStatement.setInt(1, pageable.getPageSize());
            selectStatement.setInt(2, (int) pageable.getOffset());
        }

        ResultSet countRs = countStatement.executeQuery();
        countRs.next();
        int total = countRs.getInt(1);

        ResultSet rs = selectStatement.executeQuery();
        while (rs.next()) {
            Articulo articulo = new Articulo();
            articulo.setIdentificador(rs.getString(IDENTIFICADOR));
            articulo.setTitulo(rs.getString(TITULO));
            articulo.setTema(rs.getString(TEMA));
            articulo.setDescripcion(rs.getString(DESCRIPCION));
            articulo.setNombreAutor(rs.getString(NOMBRE_AUTOR));
            articulo.setFecha(rs.getDate(FECHA));
            articulo.setAcercaDelAutor(rs.getString(ACERCA_DEL_AUTOR));
            articulo.setTextoArticulo(rs.getString(TEXTO_ARTICULO));
            articulos.add(articulo);
        }

        return new PageImpl<>(articulos, pageable, total);
    }
}

     
          
     
}
