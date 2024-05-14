/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Provincia;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JEYCOB
 */
public interface IProvinciaService {
    public void guardar(String nombreProvincia, MultipartFile imagen);
    
    public List getProvincias();
    
    public Provincia getProvinciaByName(String name);
    
    public void eliminar(int id);
}
