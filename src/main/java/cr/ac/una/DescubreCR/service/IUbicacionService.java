
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Ubicacion;
import java.util.List;

/**
 *
 * @author JEYCOB
 */

public interface IUbicacionService {
    public void guardar(Ubicacion ubicacion);
    
    public List getUbicaciones();
    
    public void eliminar(int id);
}
