/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.AlbumData;
import cr.ac.una.DescubreCR.domain.Album;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author JEYCOB
 */
public class AlbumService {
    public void guardarEnBD(String nombreAutor, int prov, String nombreLugar, String descripcion, String etiquetas, LocalDate fecha){
        Album album = new Album();
        
        album.setNombreAutor(nombreAutor);
        album.setProvincia(prov);
        album.setNombreLugar(nombreLugar);
        album.setDescripcion(descripcion);
        album.setEtiquetasAsociadas(etiquetas);
        album.setFechaCreacion(fecha);
        
        try {
            // metodo de accesoDatos para guardar la entidad
            new AlbumData().insertar(album);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void actualizarEnBD(String id, String nombreAutor, int prov, String nombreLugar, String descripcion, String etiquetas, LocalDate fecha){
        Album album = new Album();
       
        album.setId(Integer.parseInt(id));
        album.setNombreAutor(nombreAutor);
        album.setProvincia(prov);
        album.setNombreLugar(nombreLugar);
        album.setDescripcion(descripcion);
        album.setEtiquetasAsociadas(etiquetas);
        album.setFechaCreacion(fecha);
        
        // metodo de accesoDatos para guardar la entidad
        new AlbumData().actualizar(album);
        
    }
    
    public ArrayList<Album> listarAlbums(){
        return new AlbumData().listarAlbums();
    }
    
    public Album getAlbumById(String id){
        return new AlbumData().consultar(id);
    }
    
    public boolean eliminarAlbumById(String id){
        return new AlbumData().eliminar(id);
    }
}
