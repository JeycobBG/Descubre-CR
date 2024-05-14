package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.ComentarioArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author gerald
 */
public interface IServiciosComentarioArticulo{

    public void guardar(ComentarioArticulo comentario);
    public Page<ComentarioArticulo> listar(Pageable pageable, int idArticulo);
    public boolean eliminar(String codigo);
    public boolean existe(String codigo);
    public ComentarioArticulo buscar(String codigo);
    public Page<ComentarioArticulo> filtrarPorUsuario(Pageable pageable, int articulo, String nombreUsuario);
    
}
