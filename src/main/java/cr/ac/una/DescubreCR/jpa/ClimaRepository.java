package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.Clima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClimaRepository extends JpaRepository<Clima, String>,JpaSpecificationExecutor<Clima> {
    
}
