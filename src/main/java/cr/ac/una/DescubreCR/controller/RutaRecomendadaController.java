package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.RutaRecomendada;
import cr.ac.una.DescubreCR.service.IRutaRecomendadaServices;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/rutasRecomendadas")
public class RutaRecomendadaController {
    
    @Autowired
    private IRutaRecomendadaServices rutasServ;
    
    @GetMapping("/form_RutaRecomendada")
    public String agregar() {
        return "rutaRecomendada/formRutaRecomendada";
    }
    
    @GetMapping("/guardar_ruta")
    public String guardarRuta(
            RedirectAttributes flash,
            @RequestParam("codigoRuta") String codigoRuta,
            @RequestParam("comentario") String comentario,
            @RequestParam("montoEntradas") double montoEntradas,
            @RequestParam("puntuacion") int puntuacion,
            @RequestParam("dificultad") String dificultad,
            @RequestParam("tipo") String tipo,
            @RequestParam("transporte") String transporte,
            @RequestParam("lugares[]") String[] lugares) {

        boolean lugaresValidos = Arrays.stream(lugares).allMatch(lugar -> lugar != null && !lugar.trim().isEmpty() && !lugar.trim().equals(", "));

        if (!lugaresValidos) {
            flash.addFlashAttribute("error", "Todos los campos de lugares deben ser completados correctamente.");
            return "redirect:/rutasRecomendadas/form_RutaRecomendada";
        }

        String lugaresString = String.join(", ", lugares);

        RutaRecomendada ruta = new RutaRecomendada(codigoRuta, lugaresString, comentario, montoEntradas,
                                puntuacion, dificultad, tipo, transporte);

        if (rutasServ.obtenerPorCodigoRuta(Integer.parseInt(codigoRuta)) != null) {
            flash.addFlashAttribute("error", "No se ha podido registrar la ruta, cambiar código de ruta.");
        } else {
            rutasServ.guardar(ruta);
            flash.addFlashAttribute("exito", "Se ha registrado una ruta.");
        }

        return "redirect:/rutasRecomendadas/form_RutaRecomendada";
    }

    
    @GetMapping("/listarRutas")
    public String listar(@PageableDefault(size=5, page=0) Pageable pageable, Model model) throws SQLException {
        Page<RutaRecomendada> pagina = rutasServ.listar(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        model.addAttribute("pagina", pagina);

        List<Integer> opcionesCantidadPorPagina = Arrays.asList(5, 10, 25, 50, 100);
        int paginasTotal = pagina.getTotalPages();
        int paginaActual = pagina.getNumber();
        int inicio = Math.max(1, paginaActual);
        int termina = Math.min(paginaActual + 5, paginasTotal);
        
        if (paginasTotal > 0) {
            List<Integer> numPaginas = new ArrayList<>();
            for (int i = inicio; i <= termina; i++) {
                numPaginas.add(i);
            }
            model.addAttribute("numPaginas", numPaginas);
        }
        model.addAttribute("opcionesCantidadPorPagina", opcionesCantidadPorPagina);

        return "rutaRecomendada/lista";
    }
    
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("codigoRuta") String codigo, RedirectAttributes flash){
        
        if(rutasServ.eliminar(Integer.parseInt(codigo))){
            flash.addFlashAttribute("exito", "Se ha eliminado la ruta con código " + codigo + ".");
        } else {
            flash.addFlashAttribute("error", "No existe la ruta con código " + codigo + ".");
        }
        
        return "index";
    }
    
    @GetMapping("/actualizar")
    public String formularioActualizar(@RequestParam("codigoRuta") String codigo, Model modelo, RedirectAttributes flash) throws SQLException {
        RutaRecomendada ruta = rutasServ.obtenerPorCodigoRuta(Integer.parseInt(codigo));
        if (ruta != null) {
            modelo.addAttribute("ruta", ruta);

            // Convertir la cadena de lugares a una lista y agregarla al modelo
            List<String> lugaresList = Arrays.asList(ruta.getLugares().split(",\\s*"));
            modelo.addAttribute("lugaresList", lugaresList);
            modelo.addAttribute("cantidadLugares", lugaresList.size());

            return "rutaRecomendada/actualizarRutaRecomendada";
        } else {
            flash.addFlashAttribute("error", "No existe una ruta con ese código.");
            return "redirect:/index";
        }
    }

    @PostMapping("/actualizar-rutaRecomendada")
    public String actualizarRutaRecomendada(@ModelAttribute RutaRecomendada ruta) throws SQLException {
        rutasServ.guardar(ruta);
        return "index";
    }
    
    /*
    @PostMapping("/buscar-codigoRuta")
    public String buscarEventoTuristico(@RequestParam("id") int id, Model model) throws SQLException {
        RutaRecomendada ruta = rutasServ.getPorCodigoRuta(id);
        if (ruta != null) {
            model.addAttribute("ruta", ruta);
            return "eventuris/actualizarEventoTuristico";
        } else {
            return "eventuris/error";
        }
    }*/
    
}
