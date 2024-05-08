package cr.ac.una.DescubreCR.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import cr.ac.una.DescubreCR.data.DataColaboradores;
import cr.ac.una.DescubreCR.domain.ColaboradorEmpresarial;
import cr.ac.una.DescubreCR.service.ColaboradorServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Jordi
 */
@Controller
@RequestMapping("/colaboradores")
public class ColaboradorController {
     
    @GetMapping({"/crearColaborador"})
    public String crear(
        
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
        String vista = "colab/verColaboradores";
        ColaboradorEmpresarial cola = new ColaboradorEmpresarial(ide,
        nombreEmpresa,descripcionEmpresa,direccionEmpresa,
                telefonoEmpresa,sitioWeb,estadoAprobacion,
                tipoColaborador,redesSociales
        
        );   
  
        try {
            new DataColaboradores().insertar(cola);
        } catch (SQLException ex) {
            Logger.getLogger(DataColaboradores.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vista;
    }
    
    @GetMapping({"/formColaborador"})
    public String form(){
        return "colab/crearColaborador";
    }
    
    @GetMapping("/verDetallesColaborador")
    @ResponseBody
    public ColaboradorEmpresarial obtenerDetalles(@RequestParam("ide") String ide) throws SQLException, JsonProcessingException {
        return ColaboradorServices.buscar(ide);
    }
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("ide") String ide){
        String vista = "redirect:/colaboradores/listaColaboradores";
        System.out.println("Identificador= "+ide);
        System.out.println("Controller Colaborador");
        boolean elimino = ColaboradorServices.eliminar(ide);
        System.out.println("elimino? "+elimino);
        if(!elimino){
            vista = "error";
        }
        System.out.println("vista="+vista);
        return vista;
    }
    
    @GetMapping("/eliminar/{ide}")
    public String eliminar(@PathVariable String ide, Model model) throws SQLException {
    ColaboradorServices.eliminar(ide);
    return "redirect:/colaboradores/listaColaboradores";
    }
    
    @GetMapping("/actualizar/{ide}")
    public String actualizar(@PathVariable String ide, Model model) throws SQLException {
    return "redirect:/colaboradores/buscarColaborador?ide="+ide;
    }
    
    @GetMapping("/eliminarForm")
    public String eliminar(){
        return "colab/eliminarColaborador";
    }
    
    @GetMapping("/listaColaboradores")
    public String listar(Model model){
        System.out.println("listado Colaboradores");
        LinkedList<ColaboradorEmpresarial> colaboradores = ColaboradorServices.getColaboradores();
        model.addAttribute("colaboradores", colaboradores);
        return "colab/verColaboradores";
    }
    
    @GetMapping("/actualizarColaborador")
    public String buscar(){
        return "colab/buscarColaborador";
    }
    
    @GetMapping("/buscarColaborador")
    public String buscar(@RequestParam("ide") String ide, Model model){
        
        ColaboradorEmpresarial cola = ColaboradorServices.buscar(ide);
        if(cola == null){
            return "error";
        }
        model.addAttribute("colaboradorEmpresarial", cola);
        return "colab/modificarColaborador";
    }

    @PostMapping("/modificarColaborador")
    public String modificarColaborador(
            
        @RequestParam("ide") String ide,
        @RequestParam("nombreEmpresa") String nombreEmpresa,
        @RequestParam("descripcionEmpresa") String descripcionEmpresa,
        @RequestParam("direccionEmpresa") String direccionEmpresa,
        @RequestParam("telefonoEmpresa") String telefonoEmpresa,
        @RequestParam("sitioWeb") String sitioWeb,
        @RequestParam("estadoAprobacion") String estadoAprobacion,
        @RequestParam("tipoColaborador") String tipoColaborador,
        @RequestParam("redesSociales") String redesSociales){
        
        System.out.println("hola 1= ");
        String vista = "redirect:/index";
         ColaboradorEmpresarial cola = new ColaboradorEmpresarial(ide,
        nombreEmpresa,descripcionEmpresa,direccionEmpresa,
                telefonoEmpresa,sitioWeb,estadoAprobacion,
                tipoColaborador,redesSociales
        
        ); 
         

        cola.setIde(ide);
        System.out.println("Identificador de colaborador a modificar " + ide);
        boolean modificado = ColaboradorServices.modificar(cola);
        
        System.out.println("hola 3= ");
        System.out.println("Proceso listo y modificado = " + modificado);
        
        if(modificado) {
             return vista;
        } else {
            return "error";
        }
    }
   
    
}
