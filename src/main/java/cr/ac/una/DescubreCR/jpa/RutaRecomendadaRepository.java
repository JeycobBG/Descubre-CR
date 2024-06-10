package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.RutaRecomendada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RutaRecomendadaRepository extends JpaRepository<RutaRecomendada, Integer> ,JpaSpecificationExecutor<RutaRecomendada> {
    
}
