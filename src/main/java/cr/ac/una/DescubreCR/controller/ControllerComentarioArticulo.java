package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.Articulo;
import cr.ac.una.DescubreCR.domain.ComentarioArticulo;
import cr.ac.una.DescubreCR.service.ArticuloServices;
import cr.ac.una.DescubreCR.service.IServiciosComentarioArticulo;
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
@RequestMapping("/comentariosArticulo")
public class ControllerComentarioArticulo {
    
    @Autowired
    private IServiciosComentarioArticulo comentariosArticuloServ;
    
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
              @RequestParam("idArticulo") int idArticulo,
              @RequestParam("nombreUsuario") String nombreUsuario,
              RedirectAttributes flash) throws SQLException{
        
        String codigo = generarCodigo();
        if(comentariosArticuloServ.existe(codigo)){
            flash.addFlashAttribute("error", "Ocurrió un error al guardar el comentario. Inténtelo de nuevo.");
        }
        else{
            ComentarioArticulo comentario = new ComentarioArticulo();
           
            
            comentario.setCodigo(codigo);
            comentario.setContenido(contenido);
            comentario.setFecha(LocalDate.now());
            comentario.setCantidadLikes(0);
            comentario.setCantidadDislikes(0);
            comentario.setVisibilidad(visible);
            comentario.setEtiquetas(etiquetas);
            comentario.setArticulo(ArticuloServices.obtenerArticuloPorID(idArticulo));
            comentario.setNombreUsuario(nombreUsuario);
            
            comentariosArticuloServ.guardar(comentario);
            flash.addFlashAttribute("exito", "¡El comentario se ha guardado con éxito!");
        }
        
        
        return "redirect:/articulos/consultaIndividual?id=" + idArticulo;
    }
    
    @GetMapping("/listar")
    public String listar(@RequestParam("idArticulo") int idArticulo, @PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        Articulo articulo = ArticuloServices.obtenerArticuloPorID(idArticulo);
        Page<ComentarioArticulo> pagina = comentariosArticuloServ.listar(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),idArticulo);
        
        modelo.addAttribute("articulo", articulo);
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
        
        return "comentarioArticulo/listar";
    }
    
    @GetMapping("/actualizar")
    public String actualizar (@RequestParam("codigo") String codigo, RedirectAttributes flash, Model modelo){
        ComentarioArticulo comentario = comentariosArticuloServ.buscar(codigo);
        
        if(comentario!=null){
            modelo.addAttribute("comentario", comentario);
            
            return "comentarioArticulo/actualizar";
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
              @RequestParam("idArticulo") int idArticulo,
              @RequestParam("cedulaUsuario") String cedulaUsuario,
              @RequestParam("nombreUsuario") String nombreUsuario,

              RedirectAttributes flash) throws SQLException{
        
        ComentarioArticulo comentario = new ComentarioArticulo();

        comentario.setCodigo(codigo);
        comentario.setContenido(contenido);
        comentario.setFecha(LocalDate.now());
        comentario.setCantidadLikes(likes);
        comentario.setCantidadDislikes(dislikes);
        comentario.setVisibilidad(visible);
        comentario.setEtiquetas(etiquetas);
        comentario.setArticulo(ArticuloServices.obtenerArticuloPorID(idArticulo));
        comentario.setNombreUsuario(nombreUsuario);
        
        comentariosArticuloServ.guardar(comentario);
        flash.addFlashAttribute("exito", "¡El comentario se ha actualizado con éxito!");
        
        return "redirect:listar?idArticulo=" + idArticulo;
    }
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("codigo") String codigo, @RequestParam("idArticulo") int idArticulo, RedirectAttributes flash){
        
        if(comentariosArticuloServ.eliminar(codigo)){
            flash.addFlashAttribute("exito", "Se ha eliminado el comentario con identificador " + idArticulo + ".");
        } else {
            flash.addFlashAttribute("error", "No existe el comentario con identificador " + idArticulo + ".");
        }
        
        return "redirect:listar?idArticulo=" + idArticulo;
    }
    
    @GetMapping("/verDetalles")
    @ResponseBody
    public ComentarioArticulo obtenerDetalles(@RequestParam("codigo") String codigo){
        return comentariosArticuloServ.buscar(codigo);
    }
    
    @GetMapping("/buscar")
    public String buscar(@RequestParam("nombre") String nombre, @RequestParam("identificador") String identificador , @PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        Articulo articulo = ArticuloServices.obtenerArticuloPorIdentificador(identificador);
        Page<ComentarioArticulo> pagina = comentariosArticuloServ.filtrarPorUsuario(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), articulo.getIdArticulo(), nombre);
        
        modelo.addAttribute("articulo", articulo);
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
        
        return "comentarioArticulo/listar";   
    }
    
}
