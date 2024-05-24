package cr.ac.una.DescubreCR.controller;
import cr.ac.una.DescubreCR.domain.Calificacion;
import cr.ac.una.DescubreCR.service.ServiciosCalificacion;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;

@Controller

@RequestMapping("/calificaciones")
public class ControllerCalificacion {

    @GetMapping("/form_registrar")
    public String registrar() {
        return "califi/form_registrar";
    }

 @PostMapping("/form_registrar")
public String guardar(@RequestParam("puntuacion") int puntuacion,
                      @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
                      @RequestParam("categoria") String categoria,
                      @RequestParam("utilidad") String utilidad,
                      @RequestParam("tipo_usuario") String tipoUsuario,
                      @RequestParam("etiquetas_asociadas") String etiquetasAsociadas) throws SQLException {

    // Convertir LocalDate a Date
    Date fechaConvertida = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());

    Calificacion calificacion = new Calificacion();
    calificacion.setPuntuacion(puntuacion);
    calificacion.setFecha(fechaConvertida); // Usar la fecha convertida
    calificacion.setCategoria(categoria);
    calificacion.setUtilidad(utilidad);
    calificacion.setTipoUsuario(tipoUsuario);
    calificacion.setEtiquetasAsociadas(etiquetasAsociadas);

    ServiciosCalificacion.insertarCalificacion(calificacion);

    return "redirect:form_registrar";
}

@GetMapping("/listarCalificacion")
public String listar(Model modelo) throws SQLException {
    LinkedList<Calificacion> calificaciones = ServiciosCalificacion.obtenerTodasLasCalificaciones();
    modelo.addAttribute("calificaciones", calificaciones);
    return "califi/listar_calificacion";
}

    
    @GetMapping("/consulta_eliminar")
    public String consulta_eliminar() {
        return "calificacion/consulta_eliminar";
    }


    @GetMapping("/consulta_actualizar")
    public String consulta_actualizar() {
        return "calificacion/consulta_actualizar";
    }

    @PostMapping("/consulta_actualizar")
    public String formulario_actualizar(@RequestParam("id_calificacion") int idCalificacion, Model modelo) throws SQLException {
        Calificacion calificacionEditar = ServiciosCalificacion.consultarCalificacion(idCalificacion);

        if (calificacionEditar != null) {
            modelo.addAttribute("calificacion", calificacionEditar);
            return "calificacion/form_actualizar";
        } else {
            return "redirect:consulta_actualizar?error=No existe la calificación con ID " + idCalificacion;
        }
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Calificacion calificacion) throws SQLException {
        ServiciosCalificacion.actualizarCalificacion(calificacion);
        return "redirect:/";
    }
}
