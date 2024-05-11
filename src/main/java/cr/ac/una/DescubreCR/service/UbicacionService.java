
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Ubicacion;
import cr.ac.una.DescubreCR.jpa.UbicacionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
        
    }

    @Override
    public List getUbicaciones() {
        return ubicacionRepo.findAll();
    }

    @Override
    public void eliminar(int id) {
        
    }
    
}