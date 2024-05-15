package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.ComentarioLugar;
import cr.ac.una.DescubreCR.jpa.RepositoryComentarioLugar;
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
 * @author josue
 */
@Service
@Primary
public class ServiciosComentarioLugar implements IServiciosComentarioLugar{
    
    @Autowired
    private RepositoryComentarioLugar repComentarioLugar;
    
    @Override
    public void guardar(ComentarioLugar comentario){
        repComentarioLugar.save(comentario);
    }
    
    @Override
    public Page<ComentarioLugar> listar(Pageable pageable, int idLugar){
        Specification<ComentarioLugar> spec = new Specification<ComentarioLugar>() {
            @Override
            public Predicate toPredicate(Root<ComentarioLugar> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicado = criteriaBuilder.equal(root.get("lugar").get("id"), idLugar);
                
                return criteriaBuilder.and(predicado);
            }
        };
        
        return repComentarioLugar.findAll(spec, pageable);
    }
    
    @Override
    public boolean eliminar(String codigo){
        try {
            repComentarioLugar.deleteById(codigo);
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
        Optional<ComentarioLugar> comentarioOptional = repComentarioLugar.findById(codigo);
        return comentarioOptional.isPresent();
    }
    
    @Override
    public ComentarioLugar buscar(String codigo){
        Optional<ComentarioLugar> comentarioOptional = repComentarioLugar.findById(codigo);
        return comentarioOptional.orElse(null);
    }
    
    @Override
    public Page<ComentarioLugar> filtrarPorUsuario(Pageable pageable, int idLugar, String nombreUsuario){
        Specification<ComentarioLugar> spec = new Specification<ComentarioLugar>() {
            @Override
            public Predicate toPredicate(Root<ComentarioLugar> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicado1 = criteriaBuilder.equal(root.get("lugar").get("id"), idLugar);
                Predicate predicado2 = criteriaBuilder.equal(root.get("nombreUsuario"), nombreUsuario);
                //Predicate predicado2 = criteriaBuilder.equal(root.get("usuario").get("nombreUsuario"), nombreUsuario);
                Predicate predicadoFinal = criteriaBuilder.and(predicado1, predicado2);
                
                return criteriaBuilder.and(predicadoFinal);
            }
        };
        
        return repComentarioLugar.findAll(spec, pageable);
    } 
    
}
