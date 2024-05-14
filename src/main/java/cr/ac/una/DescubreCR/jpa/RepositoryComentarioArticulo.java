package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.ComentarioArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author josue
 */
public interface RepositoryComentarioArticulo extends JpaRepository<ComentarioArticulo, String>, JpaSpecificationExecutor<ComentarioArticulo>{
    
}
