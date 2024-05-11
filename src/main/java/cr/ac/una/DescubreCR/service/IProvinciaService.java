/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JEYCOB
 */
public interface IProvinciaService {
    public void guardar(String nombreProvincia, MultipartFile imagen);
    
    public List getProvincias();
    
    public void eliminar(int id);
}
