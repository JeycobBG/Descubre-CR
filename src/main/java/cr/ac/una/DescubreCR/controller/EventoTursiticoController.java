/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.EventoTuristico;
import cr.ac.una.DescubreCR.service.EventoTuristicoServices;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JEYCOB
 */
@Controller
@RequestMapping("/eventos")
public class EventoTursiticoController {
    @GetMapping("/agregar")
    public String mostrarFormularioAgregarEvento() {
        return "eventuris/escribirEvento";
    }

    @PostMapping("/guardar-evento")
    public String guardarEvento(@RequestParam("codigo") String codigo,
                                @RequestParam("nombreEvento") String nombreEvento,
                                @RequestParam("descripcion") String descripcion,
                                @RequestParam("nombreAutor") String nombreAutor,
                                @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
                                @RequestParam("ubicacion") String ubicacion,
                                @RequestParam("titulo") String titulo,
                                @RequestParam("horaInicial") @DateTimeFormat(pattern = "HH:mm") Time horaInicial,
                                @RequestParam("horaFinal") @DateTimeFormat(pattern = "HH:mm") Time horaFinal,
                                @RequestParam("etiquetasAsociadas") String etiquetas) {
        EventoTuristico evento = new EventoTuristico();
        evento.setCodigo(codigo);
        evento.setNombreEvento(nombreEvento);
        evento.setDescripcion(descripcion);
        evento.setNombreAutor(nombreAutor);
        evento.setFecha(fecha);
        evento.setUbicacion(ubicacion);
        evento.setTitulo(titulo);
        evento.setHoraInicial(horaInicial);
        evento.setHoraFinal(horaFinal);
        evento.setEtiquetasAsociadas(Arrays.asList(etiquetas.split(",")));

        EventoTuristicoServices.insertarEvento(evento);

        return "redirect:/eventuris/listar";
    }


    @GetMapping("/listar")
    public String listarEventos(Model model) throws SQLException {
        List<EventoTuristico> listaEventos = EventoTuristicoServices.obtenerEventos();
        model.addAttribute("eventos", listaEventos);
        return "eventuris/listaEvento";
    }
    
 

    @GetMapping("/listarAdmin")
    public String listarEventosAdmin(Model model) throws SQLException {
        List<EventoTuristico> listaEventos = EventoTuristicoServices.obtenerEventos();
        model.addAttribute("eventos", listaEventos);
        return "eventuris/administrarEvento";
    }
    
 

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizacion(@PathVariable("id") int idEvento, Model model) throws SQLException {
        EventoTuristico evento = EventoTuristicoServices.obtenerEventoPorID(idEvento);
        if (evento != null) {
            model.addAttribute("evento", evento);
            return "eventuris/actualizarEvento";
        } else {
            return "redirect:/eventuris/listar";
        }
    }

    @PostMapping("/eliminar-evento")
    public String eliminarEvento(@RequestParam("idEvento") int idEvento) throws SQLException {
        boolean eliminado = EventoTuristicoServices.eliminarEvento(idEvento);
        return "redirect:/eventuris/listar";
    }

    @PostMapping("/actualizar-evento")
    public String actualizarEvento(@ModelAttribute EventoTuristico evento) throws SQLException {
        EventoTuristicoServices.actualizarEvento(evento);
        return "redirect:/eventuris/listar";
    }
}
