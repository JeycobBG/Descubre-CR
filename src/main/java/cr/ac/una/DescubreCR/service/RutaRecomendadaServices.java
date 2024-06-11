/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.RutaRecomendada;
import cr.ac.una.DescubreCR.jpa.RutaRecomendadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutaRecomendadaServices {

    @Autowired
    private RutaRecomendadaRepository repository;

    public List<RutaRecomendada> findAll() {
        return repository.findAll();
    }

    public Optional<RutaRecomendada> findById(Integer id) {
        return repository.findById(id);
    }

    public RutaRecomendada save(RutaRecomendada ruta) {
        return repository.save(ruta);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}

