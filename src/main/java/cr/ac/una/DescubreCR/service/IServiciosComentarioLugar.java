/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.ComentarioLugar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author josue
 */
public interface IServiciosComentarioLugar{

    public void guardar(ComentarioLugar comentario);
    public Page<ComentarioLugar> listar(Pageable pageable, int idLugar);
    public boolean eliminar(String codigo);
    public boolean existe(String codigo);
    public ComentarioLugar buscar(String codigo);
    public Page<ComentarioLugar> filtrarPorUsuario(Pageable pageable, int idLugar, String nombreUsuario);
    
}
