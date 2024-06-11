package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.ComentarioEventoTuristico;
import cr.ac.una.DescubreCR.jpa.RepositoryComentarioEventoTuristico;
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
public class ServiciosComentarioEventoTuristico implements IServiciosComentarioEventoTuristico{
    
    @Autowired
    private RepositoryComentarioEventoTuristico repComentarioEvent;
   
    public void guardar(ComentarioEventoTuristico comentario){
        repComentarioEvent.save(comentario);
    }
    
    @Override
    public Page<ComentarioEventoTuristico> listar(Pageable pageable, int idArticulo){
        Specification<ComentarioEventoTuristico> spec = new Specification<ComentarioEventoTuristico>() {
            @Override
            public Predicate toPredicate(Root<ComentarioEventoTuristico> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicado = criteriaBuilder.equal(root.get("eventoTuristico").get("id"), idArticulo);
                
                return criteriaBuilder.and(predicado);
            }
        };
        
        return repComentarioEvent.findAll(spec, pageable);
    }
    
    @Override
    public boolean eliminar(String codigo){
        try {
            repComentarioEvent.deleteById(codigo);
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
        Optional<ComentarioEventoTuristico> comentarioOptional = repComentarioEvent.findById(codigo);
        return comentarioOptional.isPresent();
    }
    
    
    @Override
    public ComentarioEventoTuristico buscar(String codigo){
        Optional<ComentarioEventoTuristico> comentarioOptional = repComentarioEvent.findById(codigo);
        return comentarioOptional.orElse(null);
    }
    
    
    @Override
    public Page<ComentarioEventoTuristico> filtrarPorUsuario(Pageable pageable, int idEventoTuristico, String nombreUsuario){
        Specification<ComentarioEventoTuristico> spec = new Specification<ComentarioEventoTuristico>() {
            @Override
            public Predicate toPredicate(Root<ComentarioEventoTuristico> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicado1 = criteriaBuilder.equal(root.get("eventoTuristico").get("id"), idEventoTuristico);
                Predicate predicado2 = criteriaBuilder.equal(root.get("nombreUsuario"), nombreUsuario);
                Predicate predicadoFinal = criteriaBuilder.and(predicado1, predicado2);
                
                return criteriaBuilder.and(predicadoFinal);
            }
        };
        
        return repComentarioEvent.findAll(spec, pageable);
    } 
    
}
