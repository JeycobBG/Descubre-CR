/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.Articulo;
import cr.ac.una.DescubreCR.service.ArticuloServices;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/articulos")
public class ArticuloController {
    
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
    public String listarArticulos(Model model) throws SQLException {
        List<Articulo> listaArticulos = ArticuloServices.obtenerArticulos();
        model.addAttribute("articulos", listaArticulos);
        return "artic/listaArticulos";
    }

    @GetMapping("/listarAdmin")
    public String listarArticulosAdmin(Model model) throws SQLException {
        List<Articulo> listaArticulos = ArticuloServices.obtenerArticulos();
        model.addAttribute("articulos", listaArticulos);
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

    @GetMapping("/busquedaUsuario")
    public String buscarPorIdentificador(@RequestParam(name = "identificador", required = false) String identificador, Model model) {
        if (identificador != null && !identificador.isEmpty()) {
            try {
                Articulo articulo = ArticuloServices.obtenerArticuloPorIdentificador(identificador);
                if (articulo != null) {
                    List<Articulo> articulosFiltrados = new ArrayList<>();
                    articulosFiltrados.add(articulo);
                    model.addAttribute("articulos", articulosFiltrados);
                } else {
                    model.addAttribute("mensaje", "No se encontró ningún artículo con el  proporcionado");
                }
            } catch (SQLException e) {
                model.addAttribute("error", "Error al buscar el artículo por identificador");
            }
        } else {
            try {
                List<Articulo> todosArticulos = ArticuloServices.obtenerArticulos();
                model.addAttribute("articulos", todosArticulos);
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "artic/listaArticulos";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizacion(@PathVariable("id") int idArticulo, Model model) throws SQLException {
        Articulo articulo = ArticuloServices.obtenerArticuloPorID(idArticulo);
        if (articulo != null) {
            model.addAttribute("articulo", articulo);
            return "artic/actualizarArticulo";
        } else {
            return "redirect:/articulos/listar";
        }
    }

    @PostMapping("/eliminar-articulo")
    public String eliminarArticulo(@RequestParam("idArticulo") int idArticulo) throws SQLException {
        boolean eliminado = ArticuloServices.eliminarArticulo(idArticulo);
        return "redirect:/articulos/listar";
    }

    @PostMapping("/actualizar-articulo")
    public String actualizarArticulo(@ModelAttribute Articulo articulo) throws SQLException {
        ArticuloServices.actualizarArticulo(articulo);
        return "redirect:/articulos/listar";
    }
}
