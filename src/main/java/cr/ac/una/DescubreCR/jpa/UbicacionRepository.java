
package cr.ac.una.DescubreCR.jpa;
import cr.ac.una.DescubreCR.domain.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author JEYCOB
 */

public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer>{
    @Query("SELECT u FROM Ubicacion u ORDER BY u.id DESC LIMIT 1")
    public Ubicacion findLastUbicacion();
}
