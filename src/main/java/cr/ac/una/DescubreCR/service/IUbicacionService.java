
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Ubicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author JEYCOB
 */

public interface IUbicacionService {
    public void guardar(Ubicacion ubicacion);

    public Page<Ubicacion> getUbicaciones(Pageable pageable);
    
    public Ubicacion getUbicacionById(String id);
    
    public void eliminar(String id);
}
