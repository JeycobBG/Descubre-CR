package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.Album;
import cr.ac.una.DescubreCR.domain.Imagen;
import cr.ac.una.DescubreCR.service.AlbumImagenService;
import cr.ac.una.DescubreCR.service.AlbumService;
import cr.ac.una.DescubreCR.service.ImagenService;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JEYCOB
 */
@Controller
@RequestMapping("/albums")
public class ControllerAlbum {
    @GetMapping({"/formularioAlbum"})  //Deberá estar en el index.htmml
    public String agregarAlbum(){
        System.out.println("albums/form_album");
        return "albums/form_album";
    }
    
    @PostMapping({"/guardar"})        //form_album
    public String guardar(
            @RequestParam("nombreAutor") String nombreAutor,
            @RequestParam("provincia") String provincia,
            @RequestParam("nombreLugar") String nombreLugar,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("etiquetas") String etiquetas,
            @RequestParam("imagenes") MultipartFile imagenes){
        
        new AlbumService().guardarEnBD( // lo envío a albumService para que vaya a BD.
                 nombreAutor,
                 Integer.parseInt(provincia),
                 nombreLugar,
                 descripcion,
                 etiquetas,
                 LocalDate.now());
        
        // se deben de guardar las imágenes correspondientes en BD también
        new ImagenService().guardar(imagenes, LocalDate.now());
        
        new AlbumImagenService().guardar();
        
        return "albums/form_album";
    }
    
    @PostMapping({"/editar/{id}"})        //form_album
    public String editar(
            @PathVariable String id,
            @RequestParam("nombreAutor") String nombreAutor,
            @RequestParam("provincia") String provincia,
            @RequestParam("nombreLugar") String nombreLugar,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("etiquetas") String etiquetas,
            @RequestParam("imagenes") MultipartFile imagenes){
        
        new AlbumService().actualizarEnBD( // lo envío a albumService para que vaya a BD.
                 id,
                 nombreAutor,
                 Integer.parseInt(provincia),
                 nombreLugar,
                 descripcion,
                 etiquetas,
                 LocalDate.now());
        
        // se deben de actualizar las imágenes correspondientes en BD también
        
        return "redirect:/albums/listar";
    }
    
    @GetMapping({"/listar"})         //lista_albums
    public String listar(Model model){
        
        ArrayList<Album> albums = new AlbumService().listarAlbums();
        model.addAttribute("albums",albums);
        return "albums/lista_albums";
    }
    
    @GetMapping("/{id}")  // display_album
    public String mostrar(@PathVariable("id") String id, Model model){
        System.out.println("path obtenido: " + id);
        
        Album album = new AlbumService().getAlbumById(id);
        ArrayList<Imagen> imagenes = new AlbumImagenService().getImagenesOfAlbumById(id);
        
        model.addAttribute("album",album);
        model.addAttribute("imagenes", imagenes);
        
        return "albums/display_album";
    }
    
    @GetMapping({"/eliminar/{id}"}) //lista_albums
    public String eliminar(@PathVariable String id, Model model){
        new AlbumService().eliminarAlbumById(id);
        
        ArrayList<Album> albums = new AlbumService().listarAlbums();
        model.addAttribute("albums",albums);
        return "redirect:/albums/listar";
    }
    
   @GetMapping({"/editar/{id}"})
   public String editar(@PathVariable String id, Model model){
       
       
       Album album = new AlbumService().getAlbumById(id);
       ArrayList<Imagen> imagenes = new AlbumImagenService().getImagenesOfAlbumById(id);
       model.addAttribute("album", album);
       model.addAttribute("imagenes", imagenes);
       // obtener los nuevos datos del formulario
       // Actualizar en BD
       return "albums/editar_album";
   }
}
