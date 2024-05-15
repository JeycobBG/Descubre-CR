package cr.ac.una.DescubreCR.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.ac.una.DescubreCR.data.DataClimas;
import cr.ac.una.DescubreCR.domain.Clima;
import cr.ac.una.DescubreCR.service.ClimaServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Jordi
 */
@Controller
@RequestMapping("/climas")
public class ClimaController {
     
    @GetMapping({"/crearClima"})
    public String crear(
            
        @RequestParam("codigo") String codigo,
        @RequestParam("temperatura") String temperatura,
        @RequestParam("tipoClima") String tipoClima,
        @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
        @RequestParam("unidadC") String unidadC,
        @RequestParam("indiceUV") int indiceUV,
        @RequestParam("porcentajeHumedad") String porcentajeHumedad){
        
        Random random = new Random();
        int rand = random.nextInt(9000) + 1000;
        String ide =  String.valueOf(rand);
        String vista = "redirect:/climas/listaClima";
        Clima cli = new Clima(codigo,
        temperatura,tipoClima,fecha,
                unidadC,indiceUV,porcentajeHumedad
        
        );   

        try {
            new DataClimas().insertar(cli);
        } catch (SQLException ex) {
            Logger.getLogger(DataClimas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vista;
    }
    
    @GetMapping({"/formClima"})
    public String form(){
        return "clim/crearClima";
    }
    
    @GetMapping("/verDetallesClima")
    @ResponseBody
    public Clima obtenerDetalles(@RequestParam("codigo") String codigo) throws SQLException, JsonProcessingException {
        return ClimaServices.buscar(codigo);
    }
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("codigo") String codigo){
        String vista = "redirect:/climas/listaClima";
        boolean elimino = ClimaServices.eliminar(codigo);
        if(!elimino){
            vista = "error";
        }
        return vista;
    }
    
    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable String codigo, Model model) throws SQLException {
        System.out.println("Codigo a borrar= "+codigo);
        ClimaServices.eliminar(codigo);
    return "redirect:/climas/listaClima";
    }
    
    @GetMapping("/actualizar/{codigo}")
    public String actualizar(@PathVariable String codigo, Model model) throws SQLException {
    return "redirect:/climas/buscarClima?codigo="+codigo;
    }
    
    @GetMapping("/eliminarForm")
    public String eliminar(){
        return "clim/eliminarClima";
    }
    
    @GetMapping("/listaClima")
    public String listar(Model model){
        System.out.println("listado Climas");
        LinkedList<Clima> climas = ClimaServices.getClimas();
        model.addAttribute("climas", climas);
        return "clim/verClimas";
    }
    
    @GetMapping("/actualizarClima")
    public String buscar(){
        return "clima/buscarClima";
    }
    
    @GetMapping("/buscarClima")
    public String buscar(@RequestParam("codigo") String codigo, Model model){
        
        Clima cli = ClimaServices.buscar(codigo);
        if(cli == null){
            return "error";
        }
        model.addAttribute("clima", cli);
        return "clim/modificarClima";
    }

    @PostMapping("/modificarClima")
    public String modificarClima(
        @RequestParam("codigo") String codigo,
        @RequestParam("temperatura") String temperatura,
        @RequestParam("tipoClima") String tipoClima,
        @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
        @RequestParam("unidadC") String unidadC,
        @RequestParam("indiceUV") int indiceUV,
        @RequestParam("porcentajeHumedad") String porcentajeHumedad) {
        
        Clima cli = new Clima(codigo, temperatura, tipoClima, fecha, unidadC, indiceUV, porcentajeHumedad);

        boolean modificado = ClimaServices.modificar(cli);

        if (modificado) {
            return "redirect:/climas/listaClima";
        } else {
            return "error";
        }
    }
   
    
}
