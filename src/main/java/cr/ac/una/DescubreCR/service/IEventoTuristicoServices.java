package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.EventoTuristico;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Gerald Calderón
 */
public interface IEventoTuristicoServices {
    public void guardar(EventoTuristico eventoTuristico);
    public List getEventosTuristicos();
    public void eliminar(int cedula);
    public EventoTuristico getEventoPorId(int idEventoTuristico);
    public Page<EventoTuristico> listarEventoTuristicos(Pageable pageable);
    public Page<EventoTuristico> obtenerPorDescripción(String descripcion,Pageable pageable);
   
    
}
