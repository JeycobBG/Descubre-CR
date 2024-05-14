package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.ComentarioArticulo;
import cr.ac.una.DescubreCR.jpa.RepositoryComentarioArticulo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author gerald
 */
@Service
@Primary
public class ServiciosComentarioArticulo implements IServiciosComentarioArticulo{
    
    @Autowired
    private RepositoryComentarioArticulo repComentarioArticulo;
   
    public void guardar(ComentarioArticulo comentario){
        repComentarioArticulo.save(comentario);
    }
    
    @Override
    public Page<ComentarioArticulo> listar(Pageable pageable, int idArticulo){
        Specification<ComentarioArticulo> spec = new Specification<ComentarioArticulo>() {
            @Override
            public Predicate toPredicate(Root<ComentarioArticulo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicado = criteriaBuilder.equal(root.get("articulo").get("id"), idArticulo);
                
                return criteriaBuilder.and(predicado);
            }
        };
        
        return repComentarioArticulo.findAll(spec, pageable);
    }
    
    @Override
    public boolean eliminar(String codigo){
        try {
            repComentarioArticulo.deleteById(codigo);
            return true; 
        } catch (EmptyResultDataAccessException e) {
            return false; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    @Override
    public boolean existe(String codigo){
        Optional<ComentarioArticulo> comentarioOptional = repComentarioArticulo.findById(codigo);
        return comentarioOptional.isPresent();
    }
    
    
    @Override
    public ComentarioArticulo buscar(String codigo){
        Optional<ComentarioArticulo> comentarioOptional = repComentarioArticulo.findById(codigo);
        return comentarioOptional.orElse(null);
    }
    
    
    @Override
    public Page<ComentarioArticulo> filtrarPorUsuario(Pageable pageable, int idArticulo, String nombreUsuario){
        Specification<ComentarioArticulo> spec = new Specification<ComentarioArticulo>() {
            @Override
            public Predicate toPredicate(Root<ComentarioArticulo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicado1 = criteriaBuilder.equal(root.get("articulo").get("id"), idArticulo);
                Predicate predicado2 = criteriaBuilder.equal(root.get("nombre_usuario"), nombreUsuario);
                Predicate predicadoFinal = criteriaBuilder.and(predicado1, predicado2);
                
                return criteriaBuilder.and(predicadoFinal);
            }
        };
        
        return repComentarioArticulo.findAll(spec, pageable);
    } 
    
}
