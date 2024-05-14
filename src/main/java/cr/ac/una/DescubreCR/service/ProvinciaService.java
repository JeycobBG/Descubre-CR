/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Provincia;
import cr.ac.una.DescubreCR.jpa.ProvinciaRepository;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.engine.jdbc.BlobProxy;

/**
 *
 * @author JEYCOB
 */
@Service
public class ProvinciaService implements IProvinciaService{

    @Autowired
    private ProvinciaRepository provinciaRepo;
    
    @Override
    public void guardar(String nombreProvincia, MultipartFile imagen) {
        
        Provincia provincia = new Provincia();
        
        provincia.setNombre(nombreProvincia);
        try {
            provincia.setImagen(BlobProxy.generateProxy(imagen.getInputStream(),
                                    imagen.getSize()));
            //provincia.setImagen(Base64.getEncoder().encodeToString(imagen.getBytes()));
            System.out.println("nombre: " + nombreProvincia);
            System.out.println("src img: " + provincia.getImagen());
            provinciaRepo.save(provincia);
        } catch (IOException ex) {}
    }

    @Override
    public List getProvincias() {
        return provinciaRepo.findAll();
    }
    
    @Override
    public Provincia getProvinciaByName(String name){
        return provinciaRepo.findByNombreEquals(name);
    }

    @Override
    public void eliminar(int id) {
    }   
}