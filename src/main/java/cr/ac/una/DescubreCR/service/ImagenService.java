package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.ImagenData;
import cr.ac.una.DescubreCR.domain.Imagen;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JEYCOB
 */
public class ImagenService {
    public void guardar(MultipartFile imagenes, LocalDate fecha){
        
        Imagen img = new Imagen();
        String fileName = StringUtils.cleanPath(imagenes.getOriginalFilename());
        
        if(fileName.contains("..")){
            System.out.println("extensión de archivo inválida");
        }
        img.setFecha(fecha);
    // Pasa a string (BLOB) el archivo
        try {
            //System.out.println("img = " + Base64.getEncoder().encodeToString(imagenes.getBytes()));
            img.setSrc(Base64.getEncoder().encodeToString(imagenes.getBytes()));
            new ImagenData().insertar(img);
        } catch (IOException ex) {}
    }
}
