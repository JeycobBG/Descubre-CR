/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;


import cr.ac.una.DescubreCR.domain.Clima;
import cr.ac.una.DescubreCR.service.IClimaServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jordi
 */
@Controller
@RequestMapping("/climas")
public class ClimaController {
    
    @Autowired
    private IClimaServices climaServ;
     
    @GetMapping({"/crearClima"})
    public String crear(
        RedirectAttributes flash,
        @RequestParam("temperatura") String temperatura,
        @RequestParam("tipoClima") String tipoClima,
        @RequestParam("fecha") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha,
        @RequestParam("unidadC") String unidadC,
        @RequestParam("indiceUV") int indiceUV,
        @RequestParam("porcentajeHumedad") String porcentajeHumedad){
        
        Random random = new Random();
        int rand = random.nextInt(9000) + 1000;
        String codigo =  String.valueOf(rand);
        
       Clima cli = new Clima(codigo,
        temperatura,tipoClima,fecha,
        unidadC,indiceUV,porcentajeHumedad
        
        );   
        
        if(climaServ.getClimaPorCode(codigo) != null){
            flash.addFlashAttribute("error","No se a podido registrar");
        }else{

            climaServ.guardar(cli);
            flash.addFlashAttribute("exito","Se a registrado un clima");
        }
        
        
        return "redirect:/usuarios/menuPrincipal";
    }
    
    @GetMapping("/verDetallesClima")
    @ResponseBody
    public Clima obtenerDetalles(@RequestParam("codigo") String codigo){
        return climaServ.buscar(codigo);
    }
    
    
    
    @GetMapping({"/formClima"})
    public String form(){
        return "clim/crearClima";
    }
    
    @GetMapping("/listarClimas")
    public String listar(@PageableDefault(size=5, page=0) Pageable pageable, Model model) throws SQLException {
        Page<Clima> pagina = climaServ.listar(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
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

        return "clim/verClimas";
    }
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("codigo") String codigo, RedirectAttributes flash){
         System.out.println("Si se");
        if(climaServ.eliminar(codigo)){
             System.out.println("Si se elimino");
            flash.addFlashAttribute("exito", "Se ha eliminado el clima con código " + codigo + ".");
        } else {
            System.out.println("Entro a false");
            flash.addFlashAttribute("error", "No existe el clima con código " + codigo + ".");
        }
        
        return "redirect:/usuarios/menuPrincipal";
    }
    
   @GetMapping("/actualizar")
    public String formularioActualizar(@RequestParam("codigo") String codigo, Model modelo, RedirectAttributes flash) throws SQLException {
    Clima clima = climaServ.getClimaPorCode(codigo);
        if (clima != null) {
            modelo.addAttribute("clima", clima);
            return "clim/modificarClima";
        } else {
            flash.addFlashAttribute("error", "No existe un clima con ese codigo ");
        return "redirect:/usuarios/menuPrincipal";
        }
    }
    
    @PostMapping("/actualizar-clima")
    public String actualizarClima(@ModelAttribute Clima clim) throws SQLException {
        climaServ.guardar(clim);
        return "redirect:/usuarios/menuPrincipal";
    }
   
    
}
