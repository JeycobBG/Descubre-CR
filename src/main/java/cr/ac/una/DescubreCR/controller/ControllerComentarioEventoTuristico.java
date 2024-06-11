package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.ComentarioEventoTuristico;
import cr.ac.una.DescubreCR.domain.EventoTuristico;
import cr.ac.una.DescubreCR.service.IEventoTuristicoServices;
import cr.ac.una.DescubreCR.service.IServiciosComentarioEventoTuristico;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author gerald
 */
@Controller
@RequestMapping("/comentariosEventoTuristico")
public class ControllerComentarioEventoTuristico {
    
    @Autowired
    private IServiciosComentarioEventoTuristico comentariosEventServ;
    
    @Autowired
    private IEventoTuristicoServices eventService;
    
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static SecureRandom random = new SecureRandom();
    
    public static String generarCodigo() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
        }
        return sb.toString();
    }
    
    @PostMapping("/nuevoComentario")
    public String guardarNuevoComentario(@RequestParam("contenido") String contenido,
              @RequestParam("visible") Boolean visible,
              @RequestParam("etiquetas") String etiquetas,
              @RequestParam("idEventoTuristico") int idEventoTuristico,
              @RequestParam("nombreUsuario") String nombreUsuario,
              RedirectAttributes flash) throws SQLException{
        
        String codigo = generarCodigo();
        if(comentariosEventServ.existe(codigo)){
            flash.addFlashAttribute("error", "Ocurrió un error al guardar el comentario. Inténtelo de nuevo.");
        }
        else{
            ComentarioEventoTuristico comentario = new ComentarioEventoTuristico();
           
            
            comentario.setCodigo(codigo);
            comentario.setContenido(contenido);
            comentario.setFecha(LocalDate.now());
            comentario.setCantidadLikes(0);
            comentario.setCantidadDislikes(0);
            comentario.setVisibilidad(visible);
            comentario.setEtiquetas(etiquetas);
            comentario.setEventoTuristico(eventService.getEventoPorId(idEventoTuristico));
            comentario.setNombreUsuario(nombreUsuario);
            
            comentariosEventServ.guardar(comentario);
            flash.addFlashAttribute("exito", "¡El comentario se ha guardado con éxito!");
        }
        
        
        return "redirect:/eventoTuristico/consultaIndividual?id=" + idEventoTuristico;
    }
    
    @GetMapping("/listar")
    public String listar(@RequestParam("idEventoTuristico") int idEventoTuristico, @PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        EventoTuristico eventoTuristico = eventService.getEventoPorId(idEventoTuristico);
        Page<ComentarioEventoTuristico> pagina = comentariosEventServ.listar(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),idEventoTuristico);
        
        modelo.addAttribute("eventoTuristico", eventoTuristico);
        modelo.addAttribute("paginaComentarios", pagina);
        List<Integer> opcionesCantidadPorPagina = Arrays.asList(5,10, 25,50,100);
        
        var paginasTotal = pagina.getTotalPages();
        var paginaActual = pagina.getNumber();
        var inicio = Math.max(1, paginaActual);
        var termina = Math.min(paginaActual + 5, paginasTotal);
        
        if(paginasTotal > 0){
            List<Integer> numPaginas = new ArrayList<>();
            for(int i=inicio; i<=termina; i++){
                numPaginas.add(i);
            }
            
            modelo.addAttribute("numPaginas", numPaginas);
        }
        
        modelo.addAttribute("opcionesCantidadPorPagina", opcionesCantidadPorPagina);
        
        return "comentarioEventoTuristico/listar";
    }
    
    @GetMapping("/actualizar")
    public String actualizar (@RequestParam("codigo") String codigo, RedirectAttributes flash, Model modelo){
        ComentarioEventoTuristico comentario = comentariosEventServ.buscar(codigo);
        
        if(comentario!=null){
            modelo.addAttribute("comentario", comentario);
            
            return "comentarioEventoTuristico/actualizar";
        }else{
            flash.addFlashAttribute("error", "No existe un comentario con el codigo solicitado.");
            
            return "redirect:listar";
        }
        
    }
    
    @PostMapping("/actualizar")
    public String actualizar(@RequestParam("codigo") String codigo,
              @RequestParam("contenido") String contenido,
              @RequestParam("visible") Boolean visible,
              @RequestParam("likes") int likes,
              @RequestParam("dislikes") int dislikes,
              @RequestParam("etiquetas") String etiquetas,
              @RequestParam("id") int idEventoTuristico,
              @RequestParam("nombreUsuario") String nombreUsuario,

              RedirectAttributes flash) throws SQLException{
        
        ComentarioEventoTuristico comentario = new ComentarioEventoTuristico();

        comentario.setCodigo(codigo);
        comentario.setContenido(contenido);
        comentario.setFecha(LocalDate.now());
        comentario.setCantidadLikes(likes);
        comentario.setCantidadDislikes(dislikes);
        comentario.setVisibilidad(visible);
        comentario.setEtiquetas(etiquetas);
        comentario.setEventoTuristico(eventService.getEventoPorId(idEventoTuristico));
        comentario.setNombreUsuario(nombreUsuario);
        
        comentariosEventServ.guardar(comentario);
        flash.addFlashAttribute("exito", "¡El comentario se ha actualizado con éxito!");
        
        return "redirect:listar?idEventoTuristico=" + idEventoTuristico;
    }
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("codigo") String codigo, @RequestParam("idEventoTuristico") int idEventoTuristico, RedirectAttributes flash){
        
        if(comentariosEventServ.eliminar(codigo)){
            flash.addFlashAttribute("exito", "Se ha eliminado el comentario con identificador " + idEventoTuristico + ".");
        } else {
            flash.addFlashAttribute("error", "No existe el comentario con identificador " + idEventoTuristico + ".");
        }
        
        return "redirect:listar?idEventoTuristico=" + idEventoTuristico;
    }
    
    @GetMapping("/verDetalles")
    @ResponseBody
    public ComentarioEventoTuristico obtenerDetalles(@RequestParam("codigo") String codigo){
        return comentariosEventServ.buscar(codigo);
    }
    
    @GetMapping("/buscar")
    public String buscar(@RequestParam("nombre") String nombre, @RequestParam("idEventoTuristico") int idEventoTuristico , @PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        EventoTuristico eventoTuristico = eventService.getEventoPorId(idEventoTuristico);
        Page<ComentarioEventoTuristico> pagina = comentariosEventServ.filtrarPorUsuario(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),idEventoTuristico, nombre);
        
        modelo.addAttribute("eventoTuristico", eventoTuristico);
        modelo.addAttribute("nombre", nombre);
        modelo.addAttribute("paginaComentarios", pagina);
        List<Integer> opcionesCantidadPorPagina = Arrays.asList(5,10, 25,50,100);
        
        var paginasTotal = pagina.getTotalPages();
        var paginaActual = pagina.getNumber();
        var inicio = Math.max(1, paginaActual);
        var termina = Math.min(paginaActual + 5, paginasTotal);
        
        if(paginasTotal > 0){
            List<Integer> numPaginas = new ArrayList<>();
            for(int i=inicio; i<=termina; i++){
                numPaginas.add(i);
            }
            
            modelo.addAttribute("numPaginas", numPaginas);
        }
        
        modelo.addAttribute("opcionesCantidadPorPagina", opcionesCantidadPorPagina);
        
        return "comentarioEventoTuristico/listar";   
    }
    
}
