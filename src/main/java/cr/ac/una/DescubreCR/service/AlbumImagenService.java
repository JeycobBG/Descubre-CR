/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.AlbumImagenData;
import cr.ac.una.DescubreCR.domain.Imagen;
import java.util.ArrayList;

/**
 *
 * @author JEYCOB
 */
public class AlbumImagenService {
    public ArrayList <Imagen> getImagenesOfAlbumById(String id){
        return new AlbumImagenData().getImagenesDeAlbumById(id);
    }
    
    public boolean guardar(){
        return new AlbumImagenData().insertar();
    }
    
    public boolean eliminarAsociacionDeAlbumById(String id){
        return new AlbumImagenData().eliminarByAlbumId(id);
    }
    
    public boolean agregarImagenesDeAlbumById(String id_album){
        return new AlbumImagenData().insertarImagenByAlbumId(id_album);
    }   
    
    public boolean eliminarImagenDeAlbum(String id_imagen, String id_album){
        return new AlbumImagenData().eliminarImagenDeAlbum(id_imagen, id_album);
    }
}
