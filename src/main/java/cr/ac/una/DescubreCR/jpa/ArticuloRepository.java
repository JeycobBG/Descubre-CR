package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author Gerald Calderón
 */
public interface ArticuloRepository extends JpaRepository<Articulo, Integer>,JpaSpecificationExecutor<Articulo> {
    
}
