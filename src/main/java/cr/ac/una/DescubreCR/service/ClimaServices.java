/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Clima;
import cr.ac.una.DescubreCR.jpa.ClimaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jordi
 */
@Service
@Primary
public class ClimaServices implements IClimaServices{
    @Autowired
    private ClimaRepository climaRep;
    
    @Override
    public void guardar(Clima clima) {
        climaRep.save(clima);
    }
    
    @Override
    public List getClimas() {
       return climaRep.findAll();
       
    }
    
    @Override
    public boolean eliminar(String codigo) {
        try {
            climaRep.deleteById(codigo);
            return true; 
        } catch (EmptyResultDataAccessException e) {
            return false; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    @Override
    public Clima getClimaPorCode(String codigo) {
    Optional<Clima> optionalClima = climaRep.findById(codigo);
    return optionalClima.orElse(null); 
    }
    
    @Override
    public Page<Clima> listar(Pageable pageable) {
        return climaRep.findAll(pageable);
    }

    @Override
    public Clima buscar(String codigo){
        Optional<Clima> comentarioOptional = climaRep.findById(codigo);
        return comentarioOptional.orElse(null);
    }

}