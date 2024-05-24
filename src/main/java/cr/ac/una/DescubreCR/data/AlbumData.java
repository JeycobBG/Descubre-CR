/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;

import cr.ac.una.DescubreCR.domain.Album;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author JEYCOB
 */
public class AlbumData extends ConectarDB{
    private static final String TBALBUM = "tb_album";
    
    public boolean insertar(Album album) throws SQLException{
        String sql = "INSERT INTO "+ TBALBUM + " (nombreAutor,provincia,"
                + "nombreLugar,descripcion,etiquetasAsociadas,fechaCreacion,id_autor) VALUES (?,?,?,?,?,?,?)";
        Connection connection = conectar();
        PreparedStatement sentencia = connection.prepareStatement(sql);
        
        sentencia.setString(1, album.getNombreAutor());
        sentencia.setString(2, String.valueOf(album.getProvincia()));
        sentencia.setString(3, album.getNombreLugar());
        sentencia.setString(4, album.getDescripcion());
        sentencia.setString(5, album.getEtiquetasAsociadas());
        sentencia.setString(6, String.valueOf(album.getFechaCreacion()));
        sentencia.setString(7, String.valueOf(album.getId_autor()));
        
        int resultado = sentencia.executeUpdate();
        System.out.println("resultado Album="+resultado);
        
        ResultSet rs = sentencia.executeQuery("SELECT LAST_INSERT_ID() as id_album");
        if (rs.next()) {
            setId_album(rs.getString("id_album"));
        }
        sentencia.close();
        closeConnection(connection);
        return resultado==1;
    }
    
 //Leer todos los registros -> Listo
     public ArrayList<Album> listarAlbums(){
        ArrayList<Album> albums = new ArrayList<>();
        
        try {
            Connection cn = conectar();
            
            PreparedStatement sentencia = (PreparedStatement) cn.prepareStatement("SELECT * FROM " + TBALBUM);
           
            ResultSet rs = sentencia.executeQuery();
            
            while(rs.next()){
                Album album = new Album();
                album.setId(rs.getInt("id"));
                album.setNombreAutor(rs.getString("nombreAutor"));
                album.setNombreLugar(rs.getString("nombreLugar"));
                albums.add(album);
            }
            sentencia.close();
            closeConnection(cn);
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            albums = null;
        }
        return albums;
     }
     
     //Leer un registro por su identificador (su path del buscador en este caso)-> Listo
     public Album consultar(String id){
        Album album = null;
        
        try {
            Connection cn = conectar();
            
            PreparedStatement sentencia = (PreparedStatement) cn.prepareStatement("SELECT * FROM " + TBALBUM + " WHERE id =?");
            sentencia.setString(1, id);
            ResultSet rs = sentencia.executeQuery();
            
            if(rs.next()){
                album = new Album();
                album.setId(rs.getInt("id"));
                album.setNombreAutor(rs.getString("nombreAutor"));
                album.setProvincia(Integer.parseInt(rs.getString("provincia")));
                album.setNombreLugar(rs.getString("nombreLugar"));
                album.setDescripcion(rs.getString("descripcion"));
                album.setEtiquetasAsociadas(rs.getString("etiquetasAsociadas"));
                album.setFechaCreacion(LocalDate.parse(rs.getString("fechaCreacion")));
            }
            sentencia.close();
            closeConnection(cn);
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            album = null;
        }
        return album;
     }
     
     //Eliminar
     public boolean eliminar(String id){
        int resultado = -1;
        
        try {
            Connection cn = conectar();
            
            PreparedStatement sentencia = (PreparedStatement) cn.prepareStatement("DELETE FROM " + TBALBUM + " WHERE id =?");
           sentencia.setString(1, id);
            
            resultado = sentencia.executeUpdate();
            sentencia.close();
            closeConnection(cn);
            System.out.println("resultado Album eliminar="+resultado);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return resultado==1;
     }
     
    //Actualizar
     public boolean actualizar(Album album) {
        int resultado = -1;
        try {
            Connection cn = conectar();                                                                      // provincia,"+ "nombreLugar,descripcion,etiquetasAsociadas,imagenes,fechaCreacion,identificador

            PreparedStatement sentencia = (PreparedStatement) cn.prepareStatement("UPDATE " + TBALBUM + " SET nombreAutor = ?, provincia = ?, nombreLugar = ?, descripcion = ?, etiquetasAsociadas = ?, fechaCreacion = ?, id_autor = ? WHERE id = ?");
            sentencia.setString(1, album.getNombreAutor());
            sentencia.setString(2, String.valueOf(album.getProvincia()));
            sentencia.setString(3, album.getNombreLugar());
            sentencia.setString(4, album.getDescripcion());
            sentencia.setString(5, album.getEtiquetasAsociadas());
            sentencia.setString(6, String.valueOf(album.getFechaCreacion()));
            sentencia.setString(7, String.valueOf(album.getId_autor()));
            sentencia.setString(8, String.valueOf(album.getId()));

            resultado = sentencia.executeUpdate();
            sentencia.close();
            closeConnection(cn);
            System.out.println("resultado Album Actualizar="+resultado);
        }catch (SQLException e) {
            System.out.println(e.toString());
       }
        return resultado==1;
    }
     
     public Page<Album> listarAlbumsParaPaginacion(Pageable pageable){
         
         ArrayList<Album> albums = new ArrayList<>();
         
         String countSql = "SELECT COUNT(*) FROM " + TBALBUM;
         String selectSql = "SELECT * FROM " + TBALBUM + " LIMIT ? OFFSET ?";
         
         int total = 0;
         
         try {
            Connection conexion = conectar();
            
            PreparedStatement countStatement = conexion.prepareStatement(countSql);
            ResultSet rs = countStatement.executeQuery();
            rs.next();
            total  = rs.getInt(1);
            
            PreparedStatement selectStatement = conexion.prepareStatement(selectSql);
            selectStatement.setInt(1, pageable.getPageSize());
            selectStatement.setLong(2, pageable.getOffset());
            
            rs = selectStatement.executeQuery();
            
            while(rs.next()){
                Album album = new Album();
                album.setId(rs.getInt("id"));
                album.setNombreAutor(rs.getString("nombreAutor"));
                album.setNombreLugar(rs.getString("nombreLugar"));
                albums.add(album);
            }
            closeConnection(conexion);
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return new PageImpl<>(albums, pageable, total);
     }
}