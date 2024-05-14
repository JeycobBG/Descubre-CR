
package cr.ac.una.DescubreCR.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cr.ac.una.DescubreCR.domain.Provincia;
import cr.ac.una.DescubreCR.domain.Ubicacion;
import cr.ac.una.DescubreCR.service.ProvinciaService;
import cr.ac.una.DescubreCR.service.UbicacionService;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author JEYCOB
 */

@Controller
@RequestMapping("/ubicacion")
public class ControllerUbicacion {
    @Autowired
    private UbicacionService ubicacionService;
    
    @Autowired
    private ProvinciaService provinciaService;

// --------------------------------------------------------------------
    @GetMapping("/formularioUbicacion")  // formulario
    public String mostrarFormulario(){
        return "/ubicacion/form_ubicacion";
    }
// --------------------------------------------------------------------
    @PostMapping("/guardar")             // Guardar en POST
    public String guardar(
            @RequestParam("nombreAutor") String nombreAutor,
            @RequestParam("direccion") String direccion,
            @RequestParam("provincia") String provincia,
            @RequestParam("canton") String canton,
            @RequestParam("distrito") String distrito, Model modelo, RedirectAttributes redirectAttr){
        
        Ubicacion ubicacion = new Ubicacion();
        
        ubicacion.setNombreAutor(nombreAutor);
        ubicacion.setDireccion(direccion);
        ubicacion.setNombreProvincia(provincia);
        ubicacion.setCanton(canton);
        ubicacion.setDistrito(distrito);
        ubicacion.setFechaCreacion(LocalDate.now());
        ubicacion.setProvincia(provinciaService.getProvinciaByName(provincia));
        
        ubicacionService.guardar(ubicacion);
        redirectAttr.addFlashAttribute("exito", "Registro guardado con Exito!!");
        
        
        //return "/ubicacion/form_ubicacion";
        return "redirect:/ubicacion/listarAdmin";
    }
// --------------------------------------------------------------------
    
    @PostMapping("/guardar/{id}")             // Guardar en POST
    public String actualizar(
            @PathVariable("id") String id,
            @RequestParam("nombreAutor") String nombreAutor,
            @RequestParam("direccion") String direccion,
            @RequestParam("provincia") String provincia,
            @RequestParam("canton") String canton,
            @RequestParam("distrito") String distrito, Model modelo, RedirectAttributes redirectAttr){
        
        Ubicacion ubicacion = new Ubicacion();
        
        ubicacion.setId(Integer.parseInt(id));
        ubicacion.setNombreAutor(nombreAutor);
        ubicacion.setDireccion(direccion);
        ubicacion.setNombreProvincia(provincia);
        ubicacion.setCanton(canton);
        ubicacion.setDistrito(distrito);
        ubicacion.setFechaCreacion(LocalDate.now());
        ubicacion.setProvincia(provinciaService.getProvinciaByName(provincia));
        
        ubicacionService.guardar(ubicacion);
        redirectAttr.addFlashAttribute("exito", "Registro guardado con Exito!!");
        
        
        //return "/ubicacion/form_ubicacion";
        return "redirect:/ubicacion/listarAdmin";
    }
// --------------------------------------------------------------------
    
    @GetMapping("/listar")             // Listar
    public String listar(@PageableDefault(size = 10, page = 0) Pageable pageable, Model modelo){
        
        Page<Ubicacion> pagina = ubicacionService.getUbicaciones(pageable);
        
        modelo.addAttribute("pagina", pagina);
        var totalPages = pagina.getTotalPages();
        var currentPage = pagina.getNumber();
        
        var start = Math.max(1, currentPage);
        var end = Math.min(currentPage+5, totalPages);
        
        if(totalPages > 0){
            List<Integer> pageNumbers = new ArrayList<>();
            for(int i = start; i <= end; i++){
                pageNumbers.add(i);
            }
            modelo.addAttribute("pageNumbers", pageNumbers);
        }
        
        List<Integer> pageSizeOptions = Arrays.asList(10, 20, 50, 100);
        modelo.addAttribute("pageSizeOptions", pageSizeOptions);
        return "/ubicacion/lista_ubicaciones";
    }
// --------------------------------------------------------------------
    @GetMapping("/listarAdmin")        // Listar Administrador
    public String listarAdmin(@PageableDefault(size = 10, page = 0) Pageable pageable, Model modelo){
        
        Page<Ubicacion> pagina = ubicacionService.getUbicaciones(pageable);
        
        modelo.addAttribute("pagina", pagina);
        var totalPages = pagina.getTotalPages();
        var currentPage = pagina.getNumber();
        
        var start = Math.max(1, currentPage);
        var end = Math.min(currentPage+5, totalPages);
        
        if(totalPages > 0){
            List<Integer> pageNumbers = new ArrayList<>();
            for(int i = start; i <= end; i++){
                pageNumbers.add(i);
            }
            modelo.addAttribute("pageNumbers", pageNumbers);
        }
        
        List<Integer> pageSizeOptions = Arrays.asList(10, 20, 50, 100);
        modelo.addAttribute("pageSizeOptions", pageSizeOptions);
        
        return "/ubicacion/lista_ubicaciones_admin";
    }
// --------------------------------------------------------------------
    @GetMapping("/eliminar/{id}")      // Eliminar Ubicacion
    public String eliminar(@PathVariable("id") String id, RedirectAttributes redirectAttr){
        ubicacionService.eliminar(id);
        //redirectAttr.addFlashAttribute("exito", "Registro eliminado con Exito!!");
        return "redirect:/ubicacion/listarAdmin";
    }
    
// --------------------------------------------------------------------
    
    @GetMapping("/editar/{id}")    // Actualizar Ubicacion
    public String editar(@PathVariable("id") String id, RedirectAttributes redirectAttr, Model modelo){
        
        Ubicacion ubicacion_editar = ubicacionService.getUbicacionById(id);
        Provincia provincia = provinciaService.getProvinciaByName(ubicacion_editar.getNombreProvincia());
        
        Blob img = provincia.getImagen();
        
        String imagen;
        byte[] losBytes = {};
        try {
            byte[] bytes = img.getBytes(1, (int) img.length());
            losBytes = bytes;
        } catch (SQLException ex) {
            System.out.println("Error parseando la imagen blob");
        }
        imagen = Base64.getEncoder().encodeToString(losBytes);
        
        Ubicacion ubicacionParaJson = new Ubicacion();
        ubicacionParaJson.setNombreProvincia(ubicacion_editar.getNombreProvincia());
        ubicacionParaJson.setCanton(ubicacion_editar.getCanton());
        ubicacionParaJson.setDistrito(ubicacion_editar.getDistrito());
        
        ObjectMapper objectMapper = new ObjectMapper();
            
        String ubicacionJson = "";
        
        try {
            ubicacionJson = objectMapper.writeValueAsString(ubicacionParaJson);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        
        
        modelo.addAttribute("prov", imagen);
        modelo.addAttribute("ubicacion", ubicacion_editar);
        modelo.addAttribute("ubicacionJson", ubicacionJson);
        
        
        redirectAttr.addFlashAttribute("exito", "Registro eliminado con Exito!!");
        
        return "/ubicacion/editar_ubicacion";
    }

// --------------------------------------------------------------------
    
    @GetMapping("/{id}")    // mostrar Ubicacion
    public String verUbicacion(Model modelo, @PathVariable ("id") String id){
        Ubicacion ubicacion = ubicacionService.getUbicacionById(id);
        Provincia provincia = provinciaService.getProvinciaByName(ubicacion.getNombreProvincia());
        Blob img = provincia.getImagen();
        
        String imagen;
        byte[] losBytes = {};
        try {
            byte[] bytes = img.getBytes(1, (int) img.length());
            losBytes = bytes;
        } catch (SQLException ex) {
            System.out.println("Error parseando la imagen blob");
        }
        imagen = Base64.getEncoder().encodeToString(losBytes);
        
        modelo.addAttribute("provincia", imagen);
        modelo.addAttribute("ubicacion", ubicacion);
        return "/ubicacion/display_ubicacion";
    }    
}