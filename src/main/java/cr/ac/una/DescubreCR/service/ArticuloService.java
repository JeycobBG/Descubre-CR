package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Articulo;
import cr.ac.una.DescubreCR.jpa.ArticuloRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gerald Calderón
 */
@Service
@Primary
public class ArticuloService implements IArticuloServices {
    @Autowired
    private ArticuloRepository artRep;
    
    public void guardar(Articulo articulo) {
        artRep.save(articulo);
    }

    
    public List getArticulos() {
       return artRep.findAll();
    }

    
    public void eliminar(int idArticulo) {
       artRep.deleteById(idArticulo);
    }

    public Articulo getArticuloPorId(int idArticulo) {
    Optional<Articulo> optionalEvento = artRep.findById(idArticulo);
    return optionalEvento.orElse(null); // O manejar de otra manera si prefieres
    }

  
    public Page<Articulo> listarArticulos(Pageable pageable) {
        return artRep.findAll(pageable);
    }

    public Page<Articulo> obtenerPorDescripción(String descripcion, Pageable pageable) {
    Specification<Articulo> spec = new Specification<Articulo>() {
        @Override
        public jakarta.persistence.criteria.Predicate toPredicate(Root<Articulo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            // No es necesario convertir el resultado de criteriaBuilder.like a Predicate
            Predicate likePredicate = criteriaBuilder.like(root.get("descripcion"), "%" + descripcion + "%");
            // No es necesario convertir el array de predicados, puedes pasar el predicado directamente
            return criteriaBuilder.and(likePredicate);
        }
    };

    return artRep.findAll(spec, pageable);
}



}
