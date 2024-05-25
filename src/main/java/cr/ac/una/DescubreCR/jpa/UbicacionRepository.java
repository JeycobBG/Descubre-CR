
package cr.ac.una.DescubreCR.jpa;
import cr.ac.una.DescubreCR.domain.Lugar;
import cr.ac.una.DescubreCR.domain.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author JEYCOB
 */

public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer>{

    public Ubicacion findByLugarTuristico(Lugar lugar);
}
