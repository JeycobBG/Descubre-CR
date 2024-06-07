/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Lugar;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Jamyr
 */
public interface ILugarService {
    
    public void insertar(Lugar lugar);
    public Page<Lugar> listar(Pageable pageable);
    public List<Lugar> listar();
    public void eliminar(int id);
    public Lugar consultarEspPorCodigo(String codigo);
    public Lugar consultarEspPorNombre(String nombre);
    public Page<Lugar> consultarPorNombre(Pageable pageable, String nombre);
    public List<String> getCategorias();
    
}
