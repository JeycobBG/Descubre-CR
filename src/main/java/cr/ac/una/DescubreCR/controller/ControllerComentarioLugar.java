package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.ComentarioLugar;
import cr.ac.una.DescubreCR.domain.Lugar;
import cr.ac.una.DescubreCR.service.ILugarService;
import cr.ac.una.DescubreCR.service.IServiciosComentarioLugar;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/comentariosLugar")
public class ControllerComentarioLugar {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private IServiciosComentarioLugar comentariosLugarServ;
    
    @Autowired
    private ILugarService lugarService;

    @Value("${server.url:http://localhost:7000/apiComentarios}") // Establece el valor predeterminado como localhost:8080 si server.url no está definido
    private String urApiComentarios;
    
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    
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
              RedirectAttributes flash) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        ComentarioLugar comentario = new ComentarioLugar();

        comentario.setCodigo(generarCodigo());
        comentario.setContenido(contenido);
        comentario.setFecha(LocalDate.now());
        comentario.setCantidadLikes(0);
        comentario.setCantidadDislikes(0);
        comentario.setVisibilidad(visible);
        comentario.setEtiquetas(etiquetas);
        comentario.setLugar(lugarService.consultarEspPorCodigo(codLugar));
        comentario.setNombreUsuario(nombreUsuario);

        HttpEntity<ComentarioLugar> request = new HttpEntity<>(comentario, headers);
        ResponseEntity<String> response = restTemplate.exchange(urApiComentarios + "/nuevoComentario", HttpMethod.POST, request, String.class);
        
        if(response.equals(response.getStatusCode().isError())){
            flash.addFlashAttribute("error", "Ocurrió un error al guardar el comentario. Inténtelo de nuevo.");
        }else{
            flash.addFlashAttribute("exito", "¡El comentario se ha guardado con éxito!");
        }

        return "redirect:/lugares/consulta_individual?codigo=" + codLugar;
    }
    
    @GetMapping("/listar")
    public String listarComentarios(@RequestParam("codigoLugar") String codigoLugar, @PageableDefault(size = 10) Pageable pageable, Model modelo) {
        ResponseEntity<List<ComentarioLugar>> responseEntity = restTemplate.exchange(urApiComentarios + "/listarAPI?codigoLugar=" + codigoLugar, HttpMethod.GET, null, new ParameterizedTypeReference<List<ComentarioLugar>>() {});
        List<ComentarioLugar> comentarios = responseEntity.getBody();

        int totalElements = comentarios.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        List<Integer> opcionesCantidadPorPagina = Arrays.asList(5, 10, 25, 50, 100);

        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), totalElements);
        List<ComentarioLugar> comentariosPaginados = comentarios.subList(fromIndex, toIndex);

        Lugar lugar = lugarService.consultarEspPorCodigo(codigoLugar);
        Page<ComentarioLugar> paginaComentarios = new PageImpl<>(comentariosPaginados, pageable, totalElements);

        modelo.addAttribute("lugar", lugar);
        modelo.addAttribute("paginaComentarios", paginaComentarios);
        modelo.addAttribute("numPaginas", totalPages);
        modelo.addAttribute("currentPage", pageable.getPageNumber());
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
    
    @PutMapping("/actualizar")
    public String actualizarComentario(@RequestParam("codigo") String codigo,
              @RequestParam("contenido") String contenido,
              @RequestParam("visible") Boolean visible,
              @RequestParam("likes") int likes,
              @RequestParam("dislikes") int dislikes,
              @RequestParam("etiquetas") String etiquetas,
              @RequestParam("codLugar") String codLugar,
              @RequestParam("nombreUsuario") String nombreUsuario,
              RedirectAttributes flash) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        ComentarioLugar comentario = new ComentarioLugar();

        comentario.setCodigo(codigo);
        comentario.setContenido(contenido);
        comentario.setFecha(LocalDate.now());
        comentario.setCantidadLikes(likes);
        comentario.setCantidadDislikes(dislikes);
        comentario.setVisibilidad(visible);
        comentario.setEtiquetas(etiquetas);
        comentario.setLugar(lugarService.consultarEspPorCodigo(codLugar));
        comentario.setNombreUsuario(nombreUsuario);

        HttpEntity<ComentarioLugar> request = new HttpEntity<>(comentario, headers);
        ResponseEntity<String> response = restTemplate.exchange(urApiComentarios + "/actualizar", HttpMethod.PUT, request, String.class);
        
        if (response.getStatusCode().isError()) {
            flash.addFlashAttribute("error", "Ocurrió un error al actualizar el comentario. Inténtelo de nuevo.");
        } else {
            flash.addFlashAttribute("exito", "¡El comentario se ha actualizado con éxito!");
        }

        return "redirect:listar?codigoLugar=" + codLugar;
    }

    @DeleteMapping("/eliminar")
    public String eliminarComentario(@RequestParam("codigo") String codigo, @RequestParam("codigoLugar") String codigoLugar, RedirectAttributes flash) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(urApiComentarios + "/eliminar?codigo=" + codigo, HttpMethod.DELETE, request, String.class);
        
        if (response.getStatusCode().isError()) {
            flash.addFlashAttribute("error", "Ocurrió un error al eliminar el comentario. Inténtelo de nuevo.");
        } else {
            flash.addFlashAttribute("exito", "¡El comentario se ha eliminado con éxito!");
        }


        return "redirect:listar?codigoLugar=" + codigoLugar;
    }
    
    @GetMapping("/buscar")
    public String buscar(@RequestParam("nombre") String nombre, @RequestParam("codigoLugar") String codigoLugar , @PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        Lugar lugar = lugarService.consultarEspPorCodigo(codigoLugar);
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
    
    @GetMapping("/verDetalles")
    @ResponseBody
    public ComentarioLugar obtenerDetalles(@RequestParam("codigo") String codigo) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<ComentarioLugar> response = restTemplate.exchange(urApiComentarios + "/verDetalles?codigo=" + codigo, HttpMethod.GET, request, ComentarioLugar.class);

        return response.getBody();
    }

}