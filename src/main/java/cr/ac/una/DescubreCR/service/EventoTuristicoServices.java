package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.DataEventoTuristico;
import cr.ac.una.DescubreCR.domain.EventoTuristico;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Gerald Calderón
 */


public class EventoTuristicoServices {
    
public static LinkedList<EventoTuristico> obtenerEventos() throws SQLException {
    LinkedList<EventoTuristico> eventos = new LinkedList<>();
    eventos = new DataEventoTuristico().obtenerEventos(); 
    return eventos;
}
    
public static EventoTuristico insertarEvento(EventoTuristico evento) {
    try {
        return new DataEventoTuristico().insertar(evento);
    } catch (SQLException ex) {
        Logger.getLogger(EventoTuristicoServices.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}

    
public static boolean eliminarEvento(int idEvento) {
    boolean resultado = false;
    try {
        DataEventoTuristico dataEvento = new DataEventoTuristico();
        resultado = dataEvento.eliminar(idEvento);
    } catch (SQLException ex) {
        Logger.getLogger(EventoTuristicoServices.class.getName()).log(Level.SEVERE, null, ex);
    }
    return resultado;
}

public static EventoTuristico obtenerEventoPorID(int idEvento) throws SQLException {
    return new DataEventoTuristico().obtenerPorID(idEvento);
}

public static EventoTuristico obtenerEventoPorCodigo(String codigo) throws SQLException {
    return new DataEventoTuristico().obtenerPorCodigo(codigo);
}

public static void actualizarEvento(EventoTuristico evento) throws SQLException {
    new DataEventoTuristico().actualizar(evento);
}



    
}
