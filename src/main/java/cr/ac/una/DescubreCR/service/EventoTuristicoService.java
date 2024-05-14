package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.EventoTuristico;
import cr.ac.una.DescubreCR.jpa.EventoTuristicoRepository;
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
public class EventoTuristicoService implements IEventoTuristicoServices {
    @Autowired
    private EventoTuristicoRepository eventTuristRep;
    
    public void guardar(EventoTuristico eventoTuristico) {
        eventTuristRep.save(eventoTuristico);
    }

    
    public List getEventosTuristicos() {
       return eventTuristRep.findAll();
    }

    
    public void eliminar(int idEventoTuristico) {
       eventTuristRep.deleteById(idEventoTuristico);
    }

    public EventoTuristico getEventoPorId(int idEventoTuristico) {
    Optional<EventoTuristico> optionalEvento = eventTuristRep.findById(idEventoTuristico);
    return optionalEvento.orElse(null); // O manejar de otra manera si prefieres
    }

  
    public Page<EventoTuristico> listarEventoTuristicos(Pageable pageable) {
        return eventTuristRep.findAll(pageable);
    }

public Page<EventoTuristico> obtenerPorDescripción(String descripcion, Pageable pageable) {
    Specification<EventoTuristico> spec = new Specification<EventoTuristico>() {
        @Override
        public jakarta.persistence.criteria.Predicate toPredicate(Root<EventoTuristico> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            // No es necesario convertir el resultado de criteriaBuilder.like a Predicate
            Predicate likePredicate = criteriaBuilder.like(root.get("descripcion"), "%" + descripcion + "%");
            // No es necesario convertir el array de predicados, puedes pasar el predicado directamente
            return criteriaBuilder.and(likePredicate);
        }
    };

    return eventTuristRep.findAll(spec, pageable);
}



}
