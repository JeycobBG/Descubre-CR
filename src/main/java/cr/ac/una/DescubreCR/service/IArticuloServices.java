package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Articulo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Gerald Calderón
 */
public interface IArticuloServices {
    public void guardar(Articulo articulo);
    public List getArticulos();
    public void eliminar(int idArticulo);
    public Articulo getArticuloPorId(int idArticulo);
    public Page<Articulo> listarArticulos(Pageable pageable);
    public Page<Articulo> obtenerPorDescripción(String descripcion,Pageable pageable);
   
    
}
