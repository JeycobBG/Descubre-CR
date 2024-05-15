package cr.ac.una.DescubreCR.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.ac.una.DescubreCR.domain.EventoTuristico;
import cr.ac.una.DescubreCR.service.IEventoTuristicoServices;
import cr.ac.una.DescubreCR.service.ServiciosLugar;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/eventoTuristico")
public class EventoTuristicoController {
    
    @Autowired
    private IEventoTuristicoServices evenTurisSer;
    
    @GetMapping("/agregar")
    public String mostrarFormularioAgregarArticulo(Model model) throws SQLException {
        model.addAttribute("lugar",ServiciosLugar.listar());
        return "eventuris/escribirEventoTuristico";
    }
    
    @PostMapping("/guardar-eventoTuristico")
    public String guardarEventoTuristico(@RequestParam("codigo") String codigo,
        @RequestParam("nombreEvento") String nombreEvento,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
        @RequestParam("lugar") String lugarCodigo,
        @RequestParam("titulo") String titulo,
        @RequestParam("nombreAutor") String nombreAutor,
        @RequestParam("horaInicial") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaInicial,
        @RequestParam("horaFinal") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaFinal) throws SQLException {

    EventoTuristico eventoTuristico = new EventoTuristico();
    eventoTuristico.setCodigo(codigo);
    eventoTuristico.setNombreEvento(nombreEvento);
    eventoTuristico.setDescripcion(descripcion);
    eventoTuristico.setFecha(fecha);
    eventoTuristico.setLugar(ServiciosLugar.consultarEspPorCodigo(lugarCodigo));
    eventoTuristico.setTitulo(titulo);
    eventoTuristico.setNombreAutor(nombreAutor);
    eventoTuristico.setHoraInicial(horaInicial);
    eventoTuristico.setHoraFinal(horaFinal);

    evenTurisSer.guardar(eventoTuristico);
    return "redirect:/eventoTuristico/listar";
}


    @GetMapping("/listar")
    public String listarEventosTuristicos(@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) throws SQLException {
    Page<EventoTuristico> pagina = evenTurisSer.listarEventoTuristicos(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    model.addAttribute("pagina", pagina);

    List<Integer> opcionesCantidadPorPagina = Arrays.asList(5, 10, 25, 50, 100);
    var paginasTotal = pagina.getTotalPages();
    var paginaActual = pagina.getNumber();
    var inicio = Math.max(1, paginaActual);
    var termina = Math.min(paginaActual + 5, paginasTotal);

    if (paginasTotal > 0) {
        List<Integer> numPaginas = new ArrayList<>();
        for (int i = inicio; i <= termina; i++) {
            numPaginas.add(i);
        }
        model.addAttribute("numPaginas", numPaginas);
    }

    model.addAttribute("opcionesCantidadPorPagina", opcionesCantidadPorPagina);

    return "eventuris/listaEventosTuristicos";
}

    @GetMapping("/listarAdmin")
    public String listarEventosTuristicosAdmin(@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) throws SQLException {
    Page<EventoTuristico> pagina = evenTurisSer.listarEventoTuristicos(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    model.addAttribute("pagina", pagina);

    List<Integer> opcionesCantidadPorPagina = Arrays.asList(5, 10, 25, 50, 100);
    var paginasTotal = pagina.getTotalPages();
    var paginaActual = pagina.getNumber();
    var inicio = Math.max(1, paginaActual);
    var termina = Math.min(paginaActual + 5, paginasTotal);

    if (paginasTotal > 0) {
        List<Integer> numPaginas = new ArrayList<>();
        for (int i = inicio; i <= termina; i++) {
            numPaginas.add(i);
        }
        model.addAttribute("numPaginas", numPaginas);
    }

    model.addAttribute("opcionesCantidadPorPagina", opcionesCantidadPorPagina);

    return "eventuris/administrarEventosTuristicos";
}

    @PostMapping("/buscar-EventoTuristico")
    public String buscarEventoTuristico(@RequestParam("id") int id, Model model) throws SQLException {
        EventoTuristico eventoTuristico = evenTurisSer.getEventoPorId(id);
        if (eventoTuristico != null) {
            model.addAttribute("eventoTuristico", eventoTuristico);
            return "eventuris/actualizarEventoTuristico";
        } else {
            return "eventuris/error";
        }
    }

    @GetMapping("/buscarPorDescripcion")
    public String buscarPorDescripcion(@RequestParam(name = "descripcion", required = false) String descripcion,@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) throws SQLException {
    Page<EventoTuristico> pagina;
    pagina = evenTurisSer.obtenerPorDescripción(descripcion,PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    model.addAttribute("pagina", pagina);
    model.addAttribute("descripcion", descripcion);
    List<Integer> opcionesCantidadPorPagina = Arrays.asList(5, 10, 25, 50, 100);
    var paginasTotal = pagina.getTotalPages();
    var paginaActual = pagina.getNumber();
    var inicio = Math.max(1, paginaActual);
    var termina = Math.min(paginaActual + 5, paginasTotal);

    if (paginasTotal > 0) {
        List<Integer> numPaginas = new ArrayList<>();
        for (int i = inicio; i <= termina; i++) {
            numPaginas.add(i);
        }
        model.addAttribute("numPaginas", numPaginas);
    }

    model.addAttribute("opcionesCantidadPorPagina", opcionesCantidadPorPagina);

    return "eventuris/listaEventosTuristicos";
}

    @GetMapping("/buscarPorDescripcionAdmin")
    public String buscarPorDescripcionAdmin(@RequestParam(name = "descripcion", required = false) String descripcion,@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) throws SQLException {
    Page<EventoTuristico> pagina;
    pagina = evenTurisSer.obtenerPorDescripción(descripcion,PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    model.addAttribute("pagina", pagina);
    model.addAttribute("descripcion", descripcion);
    List<Integer> opcionesCantidadPorPagina = Arrays.asList(5, 10, 25, 50, 100);
    var paginasTotal = pagina.getTotalPages();
    var paginaActual = pagina.getNumber();
    var inicio = Math.max(1, paginaActual);
    var termina = Math.min(paginaActual + 5, paginasTotal);

    if (paginasTotal > 0) {
        List<Integer> numPaginas = new ArrayList<>();
        for (int i = inicio; i <= termina; i++) {
            numPaginas.add(i);
        }
        model.addAttribute("numPaginas", numPaginas);
    }

    model.addAttribute("opcionesCantidadPorPagina", opcionesCantidadPorPagina);

    return "eventuris/administrarEventosTuristicos";
}

   @GetMapping("/actualizar")
    public String formularioActualizar(@RequestParam("id") int id, Model modelo, RedirectAttributes flash) throws SQLException {
    EventoTuristico eventoTuristico = evenTurisSer.getEventoPorId(id);

    if (eventoTuristico != null) {
        modelo.addAttribute("eventoTuristico", eventoTuristico);
        modelo.addAttribute("lugares", ServiciosLugar.listar());
        return "eventuris/actualizarEventoTuristico";
    } else {
        flash.addFlashAttribute("error", "No existe el evento turistico con ese id ");
        return "redirect:/listarEventoTuristicosAdmin";
    }
  }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("id") int id, RedirectAttributes flash) throws SQLException{
        evenTurisSer.eliminar(id);
  
        return "redirect:/eventoTuristico/listarAdmin";
    }

 /*   @PostMapping("/actualizar-eventoTuristico")
    public String actualizarEventoTuristico(@ModelAttribute EventoTuristico eventoTuristico) throws SQLException {
        evenTurisSer.guardar(eventoTuristico);
        return "redirect:/eventoTuristico/listarAdmin";
    }
  */  
    @PostMapping("/actualizar-eventoTuristico")
    public String actualizarEventoTuristico(@RequestParam("id") int id,
        @RequestParam("codigo") String codigo,
        @RequestParam("nombreEvento") String nombreEvento,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
        @RequestParam("lugar") String lugarCodigo,
        @RequestParam("titulo") String titulo,
        @RequestParam("nombreAutor") String nombreAutor,
        @RequestParam("horaInicial") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaInicial,
        @RequestParam("horaFinal") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaFinal) throws SQLException {

    EventoTuristico eventoTuristico = new EventoTuristico();
    eventoTuristico.setId(id);
    eventoTuristico.setCodigo(codigo);
    eventoTuristico.setNombreEvento(nombreEvento);
    eventoTuristico.setDescripcion(descripcion);
    eventoTuristico.setFecha(fecha);
    eventoTuristico.setLugar(ServiciosLugar.consultarEspPorCodigo(lugarCodigo));
    eventoTuristico.setTitulo(titulo);
    eventoTuristico.setNombreAutor(nombreAutor);
    eventoTuristico.setHoraInicial(horaInicial);
    eventoTuristico.setHoraFinal(horaFinal);

    evenTurisSer.guardar(eventoTuristico);
    return "redirect:/eventoTuristico/listarAdmin";
}
    
    @GetMapping("/verDetallesEventoTuristico")
    @ResponseBody
    public EventoTuristico obtenerDetallesEventoTuristico(@RequestParam("id") int id) throws SQLException, JsonProcessingException {
        return evenTurisSer.getEventoPorId(id);
    }
    
        
    @GetMapping("/consultaIndividual")
    public String infoIndividual(@RequestParam("id") int id, Model modelo) throws SQLException{
        modelo.addAttribute("eventoTuristico", evenTurisSer.getEventoPorId(id));
        System.out.println(evenTurisSer.getEventoPorId(id).getNombreEvento());
        return "eventuris/eventoTuristicoIndividual";
    }
}
