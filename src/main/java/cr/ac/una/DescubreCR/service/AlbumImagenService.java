package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.AlbumImagenData;
import cr.ac.una.DescubreCR.domain.Imagen;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

/**
 *
 * @author JEYCOB
 */
public class AlbumImagenService {
    public ArrayList <Imagen> getImagenesOfAlbumById(String id){
        ArrayList<Imagen> imagenes = new AlbumImagenData().getImagenesDeAlbumById(id);
        
        Blob imgBlob;
        String imagen;
        
        
        for(Imagen imgs: imagenes){
            imgBlob = imgs.getSrc();
            byte[] losBytes = {};
            try {
                byte[] bytes = imgBlob.getBytes(1, (int) imgBlob.length());
                losBytes = bytes;
            } catch (SQLException ex) {
                System.out.println("Error parseando la imagen blob");
            }
            imagen = Base64.getEncoder().encodeToString(losBytes);
            imgs.setSrc_String(imagen);
        }
        
        return imagenes;
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
