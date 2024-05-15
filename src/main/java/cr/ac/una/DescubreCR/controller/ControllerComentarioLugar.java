package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.ComentarioLugar;
import cr.ac.una.DescubreCR.domain.Lugar;
import cr.ac.una.DescubreCR.service.IServiciosComentarioLugar;
import cr.ac.una.DescubreCR.service.ServiciosLugar;
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
 * @author josue
 */
@Controller
@RequestMapping("/comentariosLugar")
public class ControllerComentarioLugar {
    
    @Autowired
    private IServiciosComentarioLugar comentariosLugarServ;
    
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
              @RequestParam("codLugar") String codLugar,
              @RequestParam("nombreUsuario") String nombreUsuario,
              RedirectAttributes flash) throws SQLException{
        
        String codigo = generarCodigo();

        if(comentariosLugarServ.existe(codigo)){
            flash.addFlashAttribute("error", "Ocurrió un error al guardar el comentario. Inténtelo de nuevo.");
        }
        else{
            ComentarioLugar comentario = new ComentarioLugar();

            comentario.setCodigo(codigo);
            comentario.setContenido(contenido);
            comentario.setFecha(LocalDate.now());
            comentario.setCantidadLikes(0);
            comentario.setCantidadDislikes(0);
            comentario.setVisibilidad(visible);
            comentario.setEtiquetas(etiquetas);
            comentario.setLugar(ServiciosLugar.consultarEspPorCodigo(codLugar));
            comentario.setNombreUsuario(nombreUsuario);
            
            comentariosLugarServ.guardar(comentario);
            flash.addFlashAttribute("exito", "¡El comentario se ha guardado con éxito!");
        }
        
        return "redirect:/lugares/consulta_individual?codigo=" + codLugar;
    }
    
    @GetMapping("/listar")
    public String listar(@RequestParam("codigoLugar") String codigoLugar, @PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        Lugar lugar = ServiciosLugar.consultarEspPorCodigo(codigoLugar);
        Page<ComentarioLugar> pagina = comentariosLugarServ.listar(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), lugar.getId());
        
        modelo.addAttribute("lugar", lugar);
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
        
        return "comentarioLugar/listar";
    }
    
    @GetMapping("/actualizar")
    public String actualizar (@RequestParam("codigo") String codigo, RedirectAttributes flash, Model modelo){
        ComentarioLugar comentario = comentariosLugarServ.buscar(codigo);
        
        if(comentario!=null){
            modelo.addAttribute("comentario", comentario);
            
            return "comentarioLugar/actualizar";
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
              @RequestParam("codLugar") String codLugar,
              @RequestParam("nombreUsuario") String nombreUsuario,
              RedirectAttributes flash) throws SQLException{
        
        ComentarioLugar comentario = new ComentarioLugar();

        comentario.setCodigo(codigo);
        comentario.setContenido(contenido);
        comentario.setFecha(LocalDate.now());
        comentario.setCantidadLikes(likes);
        comentario.setCantidadDislikes(dislikes);
        comentario.setVisibilidad(visible);
        comentario.setEtiquetas(etiquetas);
        comentario.setLugar(ServiciosLugar.consultarEspPorCodigo(codLugar));
        comentario.setNombreUsuario(nombreUsuario);
        
        comentariosLugarServ.guardar(comentario);
        flash.addFlashAttribute("exito", "¡El comentario se ha actualizado con éxito!");
        
        return "redirect:listar?codigoLugar=" + codLugar;
    }
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("codigo") String codigo, @RequestParam("codigoLugar") String codigoLugar, RedirectAttributes flash){
        
        if(comentariosLugarServ.eliminar(codigo)){
            flash.addFlashAttribute("exito", "Se ha eliminado el comentario con código " + codigo + ".");
        } else {
            flash.addFlashAttribute("error", "No existe el comentario con código " + codigo + ".");
        }
        
        return "redirect:listar?codigoLugar=" + codigoLugar;
    }
    
    @GetMapping("/verDetalles")
    @ResponseBody
    public ComentarioLugar obtenerDetalles(@RequestParam("codigo") String codigo){
        return comentariosLugarServ.buscar(codigo);
    }
    
    @GetMapping("/buscar")
    public String buscar(@RequestParam("nombre") String nombre, @RequestParam("codigoLugar") String codigoLugar , @PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        Lugar lugar = ServiciosLugar.consultarEspPorCodigo(codigoLugar);
        Page<ComentarioLugar> pagina = comentariosLugarServ.filtrarPorUsuario(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), lugar.getId(), nombre);
        
        modelo.addAttribute("lugar", lugar);
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
        
        return "comentarioLugar/listar";   
    }
    
}
