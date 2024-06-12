/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.ColaboradorEmpresarial;
import cr.ac.una.DescubreCR.jpa.ColaboradorEmpresarialRepository;
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
public class ColaboradorServices implements IColaboradorEmpresarialServices{
     @Autowired
    private ColaboradorEmpresarialRepository colabRep;
    
    @Override
    public void guardar(ColaboradorEmpresarial colab) {
        colabRep.save(colab);
    }
    
    @Override
    public List getColaboradorEmpresarial() {
       return colabRep.findAll();
       
    }
    
    @Override
    public boolean eliminar(String ide) {
        try {
            colabRep.deleteById(ide);
            return true; 
        } catch (EmptyResultDataAccessException e) {
            return false; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    @Override
    public ColaboradorEmpresarial getColaboradorEmpresarialPorCode(String ide) {
    Optional<ColaboradorEmpresarial> optionalColaborador= colabRep.findById(ide);
    return optionalColaborador.orElse(null); 
    }
    
    @Override
    public Page<ColaboradorEmpresarial> listar(Pageable pageable) {
        return colabRep.findAll(pageable);
    }

    @Override
    public ColaboradorEmpresarial buscar(String ide){
        Optional<ColaboradorEmpresarial> comentarioOptional = colabRep.findById(ide);
        return comentarioOptional.orElse(null);
    }
}
