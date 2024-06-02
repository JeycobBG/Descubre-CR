/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.RutaRecomendada;
import cr.ac.una.DescubreCR.jpa.RutaRecomendadaRepository;
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
 * @author kvene
 */
@Service
@Primary
public class RutaRecomendadaServices implements IRutaRecomendadaServices{
    
    @Autowired
    private RutaRecomendadaRepository servRuta;

    @Override
    public List<RutaRecomendada> getServicios() {
        return servRuta.findAll();
    }

    @Override
    public boolean eliminar(int codigo) {
        try {
            servRuta.deleteById(codigo);
            return true; 
        } catch (EmptyResultDataAccessException e) {
            return false; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    @Override
    public void guardar(RutaRecomendada ruta) {
        servRuta.save(ruta);
    }

    @Override
    public Page<RutaRecomendada> listar(Pageable pageable) {
        return servRuta.findAll(pageable);
    }

    @Override
    public RutaRecomendada obtenerPorCodigoRuta(int codigo) {
    Optional<RutaRecomendada> optionalEvento = servRuta.findById(codigo);
    return optionalEvento.orElse(null);
    }
    
}
