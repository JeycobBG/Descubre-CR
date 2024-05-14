/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;

import cr.ac.una.DescubreCR.domain.Imagen;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author JEYCOB
 */
public class ImagenData extends ConectarDB{
    private static final String TBIMAGEN = "tb_imagen";
    
    // guardar imagen
    public boolean insertar(Imagen img){
        String sql = "INSERT INTO " + TBIMAGEN + " (src, fecha_ingreso) VALUES (?,?)";
        int resultado = -1;
        try{
            Connection connection = conectar();
            PreparedStatement sentencia = connection.prepareStatement(sql);
            
            sentencia.setString(1, img.getSrc());
            sentencia.setDate(2, Date.valueOf(img.getFecha()));
            
            resultado = sentencia.executeUpdate();
            System.out.println("resultado Imagen="+resultado);
            
            ResultSet rs = sentencia.executeQuery("SELECT LAST_INSERT_ID() as id_imagen");
            if (rs.next()) {
                setId_imagen(rs.getString("id_imagen"));
            }
            sentencia.close();
        closeConnection(connection);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return resultado==1;
    }
    // eliminar imagen por ID
    
    public boolean eliminarImagenById(String id){
        int resultado = -1;
        String sql = "DELETE FROM " + TBIMAGEN + " WHERE id=?";
        try {
            Connection connection = conectar();
            PreparedStatement sentencia = connection.prepareStatement(sql);
            sentencia.setString(1, id);
            
            resultado = sentencia.executeUpdate();
         
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado==1;
    }
    
    public ArrayList<Imagen> obtenerImagenes(){
        
        ArrayList<Imagen> imagenes = new ArrayList<>();
        try {
            Connection cn = conectar();
            PreparedStatement sentencia = cn.prepareStatement("SELECT * FROM " + TBIMAGEN);
            ResultSet rs = sentencia.executeQuery();
            
            while(rs.next()){
                Imagen img = new Imagen();
                img.setId(rs.getInt("id"));
                img.setSrc(rs.getString("src"));
                img.setFecha(rs.getDate("fecha_ingreso").toLocalDate());
                
                imagenes.add(img);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imagenes;
    }
}