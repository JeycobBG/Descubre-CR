package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.EventoTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author Gerald Calderón
 */
public interface EventoTuristicoRepository extends JpaRepository<EventoTuristico, Integer>,JpaSpecificationExecutor<EventoTuristico> {
    
}
