package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.ComentarioEventoTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author josue
 */
public interface RepositoryComentarioEventoTuristico extends JpaRepository<ComentarioEventoTuristico, String>, JpaSpecificationExecutor<ComentarioEventoTuristico>{
    
}
