package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.ComentarioEventoTuristico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author gerald
 */
public interface IServiciosComentarioEventoTuristico{

    public void guardar(ComentarioEventoTuristico comentario);
    public Page<ComentarioEventoTuristico> listar(Pageable pageable, int idEventoTuristico);
    public boolean eliminar(String codigo);
    public boolean existe(String codigo);
    public ComentarioEventoTuristico buscar(String codigo);
    public Page<ComentarioEventoTuristico> filtrarPorUsuario(Pageable pageable, int articulo, String nombreUsuario);
    
}
