package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Clima;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClimaServices {
    public void guardar(Clima clima);
    public List getClimas();
    boolean eliminar(String codigo);
    Page<Clima> listar(Pageable pageable);
    public Clima getClimaPorCode(String codigo);
    public Clima buscar(String codigo);
}
