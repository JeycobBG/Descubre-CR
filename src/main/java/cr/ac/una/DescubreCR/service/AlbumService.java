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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author JEYCOB
 */
public class AlbumService {
    public boolean guardarEnBD(Album album){
        
        try {
            // metodo de accesoDatos para guardar la entidad
            return new AlbumData().insertar(album);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean actualizarEnBD(Album album){
        
        // metodo de accesoDatos para guardar la entidad
        return new AlbumData().actualizar(album);
    }
    
    public ArrayList<Album> listarAlbums(){
        return new AlbumData().listarAlbums();
    }
    
    public Page<Album> listarAlbumsParaPaginacion(Pageable pageable){
        return new AlbumData().listarAlbumsParaPaginacion(pageable);
    }
    
    public Album getAlbumById(String id){
        return new AlbumData().consultar(id);
    }
    
    public boolean eliminarAlbumById(String id){
        return new AlbumData().eliminar(id);
    }
}