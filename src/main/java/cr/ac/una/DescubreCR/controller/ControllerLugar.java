package cr.ac.una.DescubreCR.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cr.ac.una.DescubreCR.domain.Lugar;
import cr.ac.una.DescubreCR.domain.Ubicacion;
import cr.ac.una.DescubreCR.service.IServiciosComentarioLugar;
import cr.ac.una.DescubreCR.service.ProvinciaService;
import cr.ac.una.DescubreCR.service.ServiciosLugar;
import cr.ac.una.DescubreCR.service.UbicacionService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author josue
 */
@Controller
@RequestMapping("/lugares")
public class ControllerLugar {
    
    @Autowired
    private IServiciosComentarioLugar comentariosLugarServ;
    
    @Autowired
    private UbicacionService ubicacionService;
    
    @Autowired
    private ProvinciaService provinciaService;
    
    @GetMapping("/form_registrar")
    public String registrar(Model modelo){
        modelo.addAttribute("categorias", ServiciosLugar.getCategorias());
        
        return "lugar/form_registrar";
    }
    
    @PostMapping("/form_registrar")
    public String guardar(@RequestParam("codigo") String codigo,
              @RequestParam("nombre") String nombre,
              @RequestParam("descripcion") String descripcion,
              @RequestParam("categoria") String categoria,
              @RequestParam("diasHorario[]") String[] dias_horario,
              @RequestParam("hora_apertura") String hora_aperturaStr,
              @RequestParam("hora_cierre") String hora_cierreStr,
              @RequestParam("precio_entr") double precio_entrada,
              
              @RequestParam("direccion") String direccion,
              @RequestParam("calidad_recep") String calidad_recepcion,
              @RequestParam(name="imagen", required=false) MultipartFile imagen,
              RedirectAttributes flash) throws SQLException {

        if(ServiciosLugar.consultarEspPorCodigo(codigo).getCodigo()!=null){
            flash.addFlashAttribute("error", "Ya existe un lugar con el codigo ingresado.");
        }
        else if(LocalTime.parse(hora_aperturaStr).isAfter(LocalTime.parse(hora_cierreStr))){
            flash.addFlashAttribute("error", "La hora de apertura es posterior a la hora de cierre. Corrija los datos.");
        }
        else{
            Lugar lugar = new Lugar();
            String dias_horarioStr = String.join(", ", dias_horario);
            LocalTime horaApertura = LocalTime.parse(hora_aperturaStr);
            LocalTime horaCierre = LocalTime.parse(hora_cierreStr);

            if(!imagen.isEmpty()){
                String ruta = "C://DescubreCR//lugares//imagenes";

                try {
                    Path ruta_absoluta = Paths.get(ruta + "//" + imagen.getOriginalFilename());
                    Files.write(ruta_absoluta, imagen.getBytes());
                    lugar.setImagen(imagen.getOriginalFilename());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Ubicacion ubicacion = new Ubicacion();
        
            ubicacion.setNombreAutor("AutorDeVariableGlobal");
            ubicacion.setDireccion(direccion);
            ubicacion.setNombreProvincia("Provincia1");
            ubicacion.setCanton("Canton1");
            ubicacion.setDistrito("Distrito1");
            ubicacion.setFechaCreacion(LocalDate.now());
            //ubicacion.setProvincia(provinciaService.getProvinciaByName(provincia));
            ubicacion.setLugarTuristico(lugar);
            
            lugar.setCodigo(codigo);
            lugar.setNombre(nombre);
            lugar.setDescripcion(descripcion);
            lugar.setCategoria(categoria);
            lugar.setDias_horario(dias_horarioStr);
            lugar.setHora_apertura(horaApertura);
            lugar.setHora_cierre(horaCierre);
            lugar.setPrecio_entrada(precio_entrada);
            lugar.setCalidad_recepcion_telefonica(calidad_recepcion);
            
            //ServiciosLugar.insertar(lugar);
            ubicacionService.guardar(ubicacion);
         
            flash.addFlashAttribute("exito", "¡El lugar se ha guardado con éxito!");
        }
        
        return "redirect:form_registrar";
    }
    
    @GetMapping("/listar")
    public String listar(@PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        Page<Lugar> pagina = ServiciosLugar.listarAdmin(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        modelo.addAttribute("pagina", pagina);
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
        
        
        return "lugar/listar";
    }
    
    @GetMapping("/listar_admin")
    public String listarAdmin(@PageableDefault(size=5, page=0) Pageable pageable, Model modelo) throws SQLException{
        Page<Lugar> pagina = ServiciosLugar.listarAdmin(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        modelo.addAttribute("pagina", pagina);
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
        
        return "lugar/listar_admin";
    }
    
    @GetMapping("/consulta_eliminar")
    public String eliminar(@RequestParam("codigo") String codigo,
            RedirectAttributes flash) throws SQLException{
        ServiciosLugar.eliminar(codigo);
        
        return "redirect:listar_admin";
    }
    
    @GetMapping("/consulta_actualizar")
    public String formularioActualizar(@RequestParam("codigo") String codigo, Model modelo, RedirectAttributes flash) throws SQLException, JsonProcessingException{
        Lugar lugar_editar = ServiciosLugar.consultarEspPorCodigo(codigo);
        Ubicacion ubicacion = lugar_editar.getUbicacion();
        modelo.addAttribute("ubicacion", ubicacion);
        modelo.addAttribute("categorias", ServiciosLugar.getCategorias());
        
        if(lugar_editar.getCodigo()!=null){
            Lugar lugarParaJson = new Lugar();
            
            lugarParaJson.setUbicacion(lugar_editar.getUbicacion());
            
            lugarParaJson.setDias_horario(lugar_editar.getDias_horario());
            ObjectMapper objectMapper = new ObjectMapper();
            
            String lugarJson = objectMapper.writeValueAsString(lugarParaJson);
            modelo.addAttribute("lugar", lugar_editar);
            modelo.addAttribute("lugarJson", lugarJson);
            
            return "lugar/form_actualizar";
        }
        else{
            flash.addFlashAttribute("error", "No existe el lugar con código " + codigo);
            return "redirect:listar_admin";
        }
      
    }
    
    @GetMapping("/consulta_verDetalles")
    @ResponseBody
    public Lugar obtenerDetallesLugar(@RequestParam("codigo") String codigo) throws SQLException, JsonProcessingException {
        return ServiciosLugar.consultarEspPorCodigo(codigo);
    }
    
    @PostMapping("/actualizar")
    public String actualizar(@RequestParam("codigo") String codigo,@RequestParam("id_Ubicacion") String id,
              @RequestParam("nombre") String nombre,
              @RequestParam("descripcion") String descripcion,
              @RequestParam("categoria") String categoria,
              @RequestParam("diasHorario[]") String[] dias_horario,
              @RequestParam("hora_apertura") String hora_aperturaStr,
              @RequestParam("hora_cierre") String hora_cierreStr,
              @RequestParam("precio_entr") double precio_entrada,
              @RequestParam("provincia") String provincia,
              @RequestParam("canton") String canton,
              @RequestParam("distrito") String distrito,
              @RequestParam("direccion") String direccion,
              @RequestParam("calidad_recep") String calidad_recepcion,
              @RequestParam(name="imagen", required=false) MultipartFile imagen,
              RedirectAttributes flash) throws SQLException {
       
        if(LocalTime.parse(hora_aperturaStr).isAfter(LocalTime.parse(hora_cierreStr))){
            flash.addFlashAttribute("error", "La hora de apertura es posterior a la hora de cierre. Corrija los datos.");
        }
        else{
            Lugar lugar = new Lugar();
            String dias_horarioStr = String.join(", ", dias_horario);
            LocalTime horaApertura = LocalTime.parse(hora_aperturaStr);
            LocalTime horaCierre = LocalTime.parse(hora_cierreStr);

            if(!imagen.isEmpty()){
                String ruta = "C://DescubreCR//lugares//imagenes";

                try {
                    Path ruta_absoluta = Paths.get(ruta + "//" + imagen.getOriginalFilename());
                    Files.write(ruta_absoluta, imagen.getBytes());
                    lugar.setImagen(imagen.getOriginalFilename());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            lugar.setCodigo(codigo);
            lugar.setNombre(nombre);
            lugar.setDescripcion(descripcion);
            lugar.setCategoria(categoria);
            lugar.setDias_horario(dias_horarioStr);
            lugar.setHora_apertura(horaApertura);
            lugar.setHora_cierre(horaCierre);
            lugar.setPrecio_entrada(precio_entrada);
            lugar.setCalidad_recepcion_telefonica(calidad_recepcion);

            Ubicacion ubicacion = new Ubicacion();
            
            ubicacion.setId(Integer.parseInt(id));
            ubicacion.setNombreAutor("AutorDeVariableGlobal");
            
            ubicacion.setDireccion(direccion);
            ubicacion.setNombreProvincia(provincia);
            ubicacion.setCanton(canton);
            ubicacion.setDistrito(distrito);
            ubicacion.setFechaCreacion(LocalDate.now());
            ubicacion.setProvincia(provinciaService.getProvinciaByName(provincia));

            ubicacionService.guardar(ubicacion);
            //lugar.setUbicacion(ubicacion);
            ServiciosLugar.reguardar(lugar);
            flash.addFlashAttribute("exito", "¡El lugar se ha actualizado con éxito!");
        }
        
        return "redirect:listar_admin";
    }
    
    @GetMapping("/consulta_buscar")
    public String buscar(@PageableDefault(size=5, page=0) Pageable pageable, @RequestParam("nombre") String nombre, Model modelo) throws SQLException{
        Page<Lugar> pagina = ServiciosLugar.consultarPorNombre(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), nombre);
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("nombre", nombre);
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
        
        return "lugar/listar";
    }
    
    @GetMapping("/consulta_buscarAdmin")
    public String buscarAdmin(@PageableDefault(size=5, page=0) Pageable pageable, @RequestParam("nombre") String nombre, Model modelo) throws SQLException{
        Page<Lugar> pagina = ServiciosLugar.consultarPorNombre(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), nombre);
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("nombre", nombre);
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
        
        return "lugar/listar_admin";
    }
    
    @GetMapping("/consulta_individual")
    public String infoIndividual(@RequestParam("codigo") String codigo, @PageableDefault(size=15, page=0) Pageable pageable, Model modelo) throws SQLException{
        Lugar lugar = ServiciosLugar.consultarEspPorCodigo(codigo);
        Ubicacion ubicacion = lugar.getUbicacion();
        
        
        
        modelo.addAttribute("ubicacion", ubicacion);
        modelo.addAttribute("lugar", lugar);
        modelo.addAttribute("paginaComentarios", comentariosLugarServ.listar(pageable, lugar.getId()));
        
        return "lugar/info_individual";
    }
    
}
