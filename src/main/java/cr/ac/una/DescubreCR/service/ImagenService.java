package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.ImagenData;
import cr.ac.una.DescubreCR.domain.Imagen;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JEYCOB
 */
public class ImagenService {
    public Imagen setImagen(MultipartFile imagenes, LocalDate fecha){
        
        Imagen img = new Imagen();
        String fileName = StringUtils.cleanPath(imagenes.getOriginalFilename());
        
        if(fileName.contains("..")){
            System.out.println("extensión de archivo inválida");
            return null;
        }
        img.setFecha(fecha);
    // Pasa a string (BLOB) el archivo
        try {
            //System.out.println("img = " + Base64.getEncoder().encodeToString(imagenes.getBytes()));
            img.setSrc(BlobProxy.generateProxy(imagenes.getInputStream(),
                                    imagenes.getSize()));
            return img;
        } catch (IOException ex) {}
        return null;
    }
    
    public boolean tamanioArchivoExcede(MultipartFile[] imagenes){
        
        Long tamanioBytes;
        long tamanioMB = 0;
        
        for (MultipartFile imagen : imagenes) {
            tamanioBytes = imagen.getSize();
            tamanioMB += tamanioBytes / (1024 * 1024);
        }
        System.out.println("tamanio en MB: " + tamanioMB);
        return tamanioMB >= 16;
    }
}