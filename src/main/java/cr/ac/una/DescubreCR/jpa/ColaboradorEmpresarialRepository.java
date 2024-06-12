package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.ColaboradorEmpresarial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ColaboradorEmpresarialRepository extends JpaRepository<ColaboradorEmpresarial, String>, JpaSpecificationExecutor<ColaboradorEmpresarial> {
    
}
