/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.ac.una.DescubreCR.domain.ColaboradorEmpresarial;
import cr.ac.una.DescubreCR.service.IColaboradorEmpresarialServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/colaboradores")
public class ColaboradorController {
    @Autowired
    private IColaboradorEmpresarialServices colabServ;
     
    @GetMapping({"/crearColaborador"})
    public String crear(
        RedirectAttributes flash,
        @RequestParam("nombreEmpresa") String nombreEmpresa,
        @RequestParam("descripcionEmpresa") String descripcionEmpresa,
        @RequestParam("direccionEmpresa") String direccionEmpresa,
        @RequestParam("telefonoEmpresa") String telefonoEmpresa,
        @RequestParam("sitioWeb") String sitioWeb,
        @RequestParam("estadoAprobacion") String estadoAprobacion,
        @RequestParam("tipoColaborador") String tipoColaborador,
        @RequestParam("redesSociales") String redesSociales) {
        
        Random random = new Random();
        int rand = random.nextInt(9000) + 1000;
        String ide =  String.valueOf(rand);

        ColaboradorEmpresarial cola = new ColaboradorEmpresarial(ide,
        nombreEmpresa,descripcionEmpresa,direccionEmpresa,
                telefonoEmpresa,sitioWeb,estadoAprobacion,
                tipoColaborador,redesSociales
        
        );   
  
       if(colabServ.getColaboradorEmpresarialPorCode(ide) != null){
            flash.addFlashAttribute("error","No se a podido registrar");
        }else{

            colabServ.guardar(cola);
            flash.addFlashAttribute("exito","Se a registrado un Colaborador");
        }
        
        
        
        
        return "redirect:/usuarios/menuPrincipal";
    }
    
    @GetMapping({"/formColaborador"})
    public String form(){
        return "colab/crearColaborador";
    }
    
    @GetMapping("/verDetallesColaboradorEmpresarial")
    @ResponseBody
    public ColaboradorEmpresarial obtenerDetalles(@RequestParam("ide") String ide) throws SQLException, JsonProcessingException {
        return colabServ.buscar(ide);
    }
    
    @GetMapping("/listaColaboradores")
    public String listar(@PageableDefault(size=5, page=0) Pageable pageable, Model model) throws SQLException {
        Page<ColaboradorEmpresarial> pagina = colabServ.listar(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
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

        return "colab/verColaboradores";
    }
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("ide") String ide, RedirectAttributes flash){
        if(colabServ.eliminar(ide)){
            flash.addFlashAttribute("exito", "Se ha eliminado el colaborador con código " + ide + ".");
        } else {
            flash.addFlashAttribute("error", "No existe el colaborador con código " + ide + ".");
        }
        
        return "redirect:/usuarios/menuPrincipal";
    }
    
    @GetMapping("/actualizar")
    public String formularioActualizar(@RequestParam("ide") String ide, Model modelo, RedirectAttributes flash) throws SQLException {
    ColaboradorEmpresarial colab = colabServ.getColaboradorEmpresarialPorCode(ide);
        if (colab != null) {
            modelo.addAttribute("colaboradorEmpresarial", colab);
            return "colab/modificarColaborador";
        } else {
            flash.addFlashAttribute("error", "No existe un colaborador con ese codigo ");

        return "redirect:/usuarios/menuPrincipal";
        }
    }
    
    @PostMapping("/modificarColaborador")
    public String actualizarColaborador(@ModelAttribute ColaboradorEmpresarial colab) throws SQLException {
        colabServ.guardar(colab);

        return "redirect:/usuarios/menuPrincipal";
    }
 
}
