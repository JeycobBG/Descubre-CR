
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Ubicacion;
import cr.ac.una.DescubreCR.jpa.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author JEYCOB
 */


@Service
public class UbicacionService implements IUbicacionService{

    @Autowired
    private UbicacionRepository ubicacionRepo;
    
    @Override // guarda y actualiza
    public void guardar(Ubicacion ubicacion) {
        ubicacionRepo.save(ubicacion);
    }

    @Override
    public Page<Ubicacion> getUbicaciones(Pageable pageable) {
        return ubicacionRepo.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }
    
    @Override
    public Ubicacion getUbicacionById(String id){
        return ubicacionRepo.getReferenceById(Integer.parseInt(id));
    }
    
    @Override
    public void eliminar(int id) {
        
    }
    
}