package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.RutaRecomendada;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRutaRecomendadaServices {
    
    void guardar(RutaRecomendada ruta);
    
    List<RutaRecomendada> getServicios();
    
    boolean eliminar(int codigo);
   
    Page<RutaRecomendada> listar(Pageable pageable);
    
    RutaRecomendada obtenerPorCodigoRuta(int codigo);
}
