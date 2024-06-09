/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Lugar;
import cr.ac.una.DescubreCR.jpa.LugarRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jamyr
 */
@Service
@Primary
public class LugarService implements ILugarService{
    
    @Autowired
    private LugarRepository lugarRepo;

    @Override
    public void insertar(Lugar lugar) {
        lugarRepo.save(lugar);
    }

    @Override
    public Page<Lugar> listar(Pageable pageable) {
      return lugarRepo.findAll(pageable);
    }
    
    @Override 
    public List<Lugar> listar(){
        return lugarRepo.findAll();
    }

    @Override
    public void eliminar(int id) {
       lugarRepo.deleteById(id);
    }
    
    @Override
    public Lugar tomar(int id) {
        return lugarRepo.getReferenceById(id);
    }

    @Override
    public Lugar consultarEspPorCodigo(String codigo) {
       return lugarRepo.findByCodigo(codigo);
    }

    @Override
    public Lugar consultarEspPorNombre(String nombre) {
      return lugarRepo.findByNombre(nombre);
    }

    @Override
    public Page<Lugar> consultarPorNombre(Pageable pageable, String nombre) {
      return lugarRepo.findByNombre(pageable, nombre);
    }
    
    @Override
    public List<String> getCategorias(){
        List<String> categorias = new ArrayList();
        
        categorias.add("Restaurante");
        categorias.add("Playa");
        categorias.add("Catarata");
        categorias.add("Río");
        categorias.add("Piscina");
        categorias.add("Canopy");
        categorias.add("Estadio");
        categorias.add("Museo");
        
        return categorias;
    }
    
}
