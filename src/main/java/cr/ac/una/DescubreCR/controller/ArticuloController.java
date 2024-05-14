package cr.ac.una.DescubreCR.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.ac.una.DescubreCR.domain.Articulo;
import cr.ac.una.DescubreCR.service.ArticuloServices;
import cr.ac.una.DescubreCR.service.IServiciosComentarioArticulo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
@RequestMapping("/articulos")
public class ArticuloController {
    
    @Autowired
    private IServiciosComentarioArticulo comentariosArticuloServ;
    
    
    @GetMapping("/agregar")
    public String mostrarFormularioAgregarArticulo() {
        return "artic/escribirArticulo";
    }

    @PostMapping("/guardar-articulo")
    public String guardarArticulo(@RequestParam("identificador") String identificador,
            @RequestParam("titulo") String titulo,
            @RequestParam("tema") String tema,
            @RequestParam("nombreAutor") String nombreAutor,
            @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @RequestParam("acercaDelAutor") String acercaDelAutor,
            @RequestParam("textoArticulo") String textoArticulo) {

        Articulo articulo = new Articulo();
        articulo.setIdentificador(identificador);
        articulo.setTitulo(titulo);
        articulo.setTema(tema);
        articulo.setNombreAutor(nombreAutor);
        articulo.setFecha(fecha);
        articulo.setAcercaDelAutor(acercaDelAutor);
        articulo.setTextoArticulo(textoArticulo);

        ArticuloServices.insertarArticulo(articulo);

        return "redirect:/articulos/listar";
    }

    @GetMapping("/listar")
    public String listarArticulos(@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) throws SQLException {
    Page<Articulo> pagina = ArticuloServices.listarAdmin(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
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

    return "artic/listaArticulos";
}

    @GetMapping("/listarAdmin")
    public String listarArticulosAdmin(@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) throws SQLException {
    Page<Articulo> pagina = ArticuloServices.listarAdmin(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
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

    return "artic/administrarArticulo";
}

    @PostMapping("/buscar-articulo")
    public String buscarArticulo(@RequestParam("idArticulo") int idArticulo, Model model) throws SQLException {
        Articulo articulo = ArticuloServices.obtenerArticuloPorID(idArticulo);
        if (articulo != null) {
            model.addAttribute("articulo", articulo);
            return "artic/actualizarArticulo";
        } else {
            return "artic/buscarArticulo";
        }
    }

    @GetMapping("/buscarPorTitulo")
    public String buscarPorTitulo(@RequestParam(name = "titulo", required = false) String titulo,@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) throws SQLException {
    Page<Articulo> pagina;
    pagina = ArticuloServices.obtenerPorTitulo(titulo,PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    model.addAttribute("pagina", pagina);
    model.addAttribute("titulo", titulo);
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

    return "artic/listaArticulos";
}

    @GetMapping("/buscarPorTituloAdmin")
    public String buscarPorTituloAdmin(@RequestParam(name = "titulo", required = false) String titulo,@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) throws SQLException {
    Page<Articulo> pagina;
    pagina = ArticuloServices.obtenerPorTitulo(titulo,PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    model.addAttribute("pagina", pagina);
    model.addAttribute("titulo", titulo);

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
    return "artic/administrarArticulo";
}

   @GetMapping("/actualizar")
    public String formularioActualizar(@RequestParam("id") int id, Model modelo, RedirectAttributes flash) throws SQLException {
    Articulo articulo = ArticuloServices.obtenerArticuloPorID(id);

    if (articulo != null) {
        modelo.addAttribute("articulo", articulo);
        return "artic/actualizarArticulo";
    } else {
        flash.addFlashAttribute("error", "No existe el artículo con ese id ");
        return "redirect:/listarAdmin";
    }
  }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("id") int codigo, RedirectAttributes flash) throws SQLException{
        
        if(ArticuloServices.eliminarArticulo(codigo)){
            flash.addFlashAttribute("exito", "Se ha eliminado el articulo");
        } else {
            flash.addFlashAttribute("error", "No existe el articulo " );
        }
        
        return "redirect:/articulos/listarAdmin";
    }

    @PostMapping("/actualizar-articulo")
    public String actualizarArticulo(@ModelAttribute Articulo articulo) throws SQLException {
        ArticuloServices.actualizarArticulo(articulo);
        return "redirect:/articulos/listarAdmin";
    }
    
    @GetMapping("/verDetallesArticulo")
    @ResponseBody
    public Articulo obtenerDetallesArticulo(@RequestParam("idArticulo") int id) throws SQLException, JsonProcessingException {
        return ArticuloServices.obtenerArticuloPorID(id);
    }
    
        
    @GetMapping("/consultaIndividual")
    public String infoIndividual(@RequestParam("id") int id,@PageableDefault(size=15, page=0) Pageable pageable, Model modelo) throws SQLException{
       
        modelo.addAttribute("articulo", ArticuloServices.obtenerArticuloPorID(id));
        modelo.addAttribute("paginaComentarios", comentariosArticuloServ.listar(pageable, id));

        return "artic/articuloIndividual";
    }
}
