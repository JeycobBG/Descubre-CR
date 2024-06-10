package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.Album;
import cr.ac.una.DescubreCR.domain.Imagen;
import cr.ac.una.DescubreCR.service.AlbumImagenService;
import cr.ac.una.DescubreCR.service.AlbumService;
import cr.ac.una.DescubreCR.service.ImagenService;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author JEYCOB
 */
@Controller
@RequestMapping("/albums")
public class ControllerAlbum {
    
    @Autowired
    private AlbumService albumService;
    
    @GetMapping({"/formularioAlbum"})  //Deberá estar en el index.htmml
    public String agregarAlbum(){
        
        return "albums/form_album";
    }
    
    @PostMapping({"/guardar"})        //form_album
    public String guardar(
            @RequestParam("nombreAutor") String nombreAutor,
            @RequestParam("provincia") String provincia,
            @RequestParam("nombreLugar") String nombreLugar,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("etiquetas") String etiquetas,
            @RequestParam("imagenes") MultipartFile[] imagenes, RedirectAttributes redirectAtt){
        
        
        System.out.println("nombre autor:" + nombreAutor);
        System.out.println("provincia:" + provincia);
                
        
        
        
        Album album = new Album();
        
        album.setNombreAutor(nombreAutor);
        album.setProvincia(Integer.parseInt(provincia));
        album.setNombreLugar(nombreLugar);
        album.setDescripcion(descripcion);
        album.setEtiquetasAsociadas(etiquetas);
        album.setFechaCreacion(LocalDate.now());
        album.setId_autor(1);  // asociado al usuario correspondiente 

        boolean tamanoExcede;
        
        //informacionSqlAlbum = new AlbumService().guardarEnBD(album);
        
        tamanoExcede = new ImagenService().tamanioArchivoExcede(imagenes);
        
        List<Imagen> imgs = new ArrayList<>();
        if (!tamanoExcede) {
            for (MultipartFile imagen : imagenes) {
                //informacionSqlImagen = new ImagenService().guardar(imagen, LocalDate.now()); // se deben de guardar las imágenes correspondientes en BD también
                //informacionSqlAlbumImagen = new AlbumImagenService().guardar(); // se asocia una imagen o varias a un album
                imgs.add(new ImagenService().setImagen(imagen, LocalDate.now()));
            }
            album.setImagenes(imgs);
            albumService.guardarEnBD(album);
            redirectAtt.addFlashAttribute("exito", "Registro guardado con exito!!");
        }else{
            redirectAtt.addFlashAttribute("error", "El tamaño del archivo es muy grande!");
        } 
        return "redirect:/albums/listarAdmin";
    }
    
    @PostMapping({"/actualizar/{id}"})        //editar_album
    public String editar(
            @PathVariable String id,
            @RequestParam("nombreAutor") String nombreAutor,
            @RequestParam("provincia") String provincia,
            @RequestParam("nombreLugar") String nombreLugar,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("etiquetas") String etiquetas,
            @RequestParam("imagenesNuevas") MultipartFile[] imagenes, RedirectAttributes redirectAtt){
        
        boolean tamanoExcede;
        
        Album album = new Album();
       
        album.setId(Integer.parseInt(id));
        album.setNombreAutor(nombreAutor);
        album.setProvincia(Integer.parseInt(provincia));
        album.setNombreLugar(nombreLugar);
        album.setDescripcion(descripcion);
        album.setEtiquetasAsociadas(etiquetas);
        album.setFechaCreacion(LocalDate.now());
        album.setId_autor(1);
        
        //informacionSqlAlbum = new AlbumService().actualizarEnBD(album);
        
        
        List<Imagen> imgs = new AlbumImagenService().getImagenesOfAlbumById(id);
        tamanoExcede = new ImagenService().tamanioArchivoExcede(imagenes);
        if (!tamanoExcede) {
            for (MultipartFile imagen : imagenes) {
                //informacionSqlImagen = new ImagenService().guardar(imagen, LocalDate.now()); // se deben de guardar las imágenes correspondientes en BD también
                imgs.add(new ImagenService().setImagen(imagen, LocalDate.now()));
                
                /*if (informacionSqlImagen) {
                    informacionSqlAlbumImagen = new AlbumImagenService().agregarImagenesDeAlbumById(id); // se asocia una imagen o varias a un album
                }else{
                    informacionSqlImagen = true;
                    informacionSqlAlbumImagen = true;
                }*/
            }
            album.setImagenes(imgs);
            albumService.guardarEnBD(album);
            redirectAtt.addFlashAttribute("exito", "Registro actualizado con exito!!");
        }
        if(tamanoExcede){
            redirectAtt.addFlashAttribute("error", "El tamaño del archivo es muy grande!");
        }
        return "redirect:/albums/listarAdmin";
    }
    
    @GetMapping({"/listarAdmin"})         //lista de albums para administrador
    public String listarAdmin(@PageableDefault(size = 10, page = 0) Pageable pageable, Model modelo){
        
        //Page<Album> pagina = new AlbumService().listarAlbumsParaPaginacion(pageable);
        Page<Album> pagina = albumService.listarAlbumsParaPaginacion(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        
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
        return "albums/lista_albums_admin";
    }
    
    
    @GetMapping({"/listar"})         //lista de albums para usuario
    public String listar(@PageableDefault(size = 10, page = 0) Pageable pageable, Model modelo){
        
        Page<Album> pagina = albumService.listarAlbumsParaPaginacion(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        //Page<Album> pagina = new AlbumService().listarAlbumsParaPaginacion(pageable);
        
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
        
        return "albums/lista_albums";
    }
    
    @GetMapping("/{id}")  // display_album
    public String mostrar(@PathVariable("id") String id, Model model){
        
        //Album album = new AlbumService().getAlbumById(id);
        Optional<Album> albumOptional = albumService.getAlbumById(id);
        Album album = albumOptional.get();
                
                
        ArrayList<Imagen> imagenes = new AlbumImagenService().getImagenesOfAlbumById(id);
        
        model.addAttribute("album",album);
        model.addAttribute("imagenes", imagenes);
        
        return "albums/display_album";
    }
    
    @GetMapping("/eliminar/{id}") // eliminar Album
    public String eliminar(@PathVariable int id, Model model){
        
        //new AlbumService().eliminarAlbumById(String.valueOf(id));
        //new AlbumImagenService().eliminarAsociacionDeAlbumById(String.valueOf(id));
        //ArrayList<Album> albums = new AlbumService().listarAlbums();
        albumService.eliminarAlbumById(String.valueOf(id));
        List<Album> albums = albumService.listarAlbums();
        model.addAttribute("albums",albums);
        return "redirect:/albums/listarAdmin";
    }
    
   @GetMapping({"/editar/{id}"})
   public String editar(@PathVariable String id, Model model){
       
       //Album album = new AlbumService().getAlbumById(id);
       Optional<Album> albumOptional = albumService.getAlbumById(id);
       Album album = albumOptional.get();
       ArrayList<Imagen> imagenes = new AlbumImagenService().getImagenesOfAlbumById(id);
       
       model.addAttribute("album", album);
       model.addAttribute("imagenes", imagenes);
       // obtener los nuevos datos del formulario
       // Actualizar en BD
       return "albums/editar_album";
   }
   
   @GetMapping("/imagenes/eliminar/{id_imagen}/{id_album}")
   public String eliminarImagen(@PathVariable ("id_imagen") String id_imagen, @PathVariable ("id_album") String id_album, Model modelo){
       
       new AlbumImagenService().eliminarImagenDeAlbum(id_imagen, id_album);
       //Album album = new AlbumService().getAlbumById(id_album);
       Optional<Album> albumOptional = albumService.getAlbumById(id_album);
       Album album = albumOptional.get();
       
       modelo.addAttribute("imagenes", new AlbumImagenService().getImagenesOfAlbumById(id_album));
       modelo.addAttribute("album", album);
       String url = "redirect:/albums/editar/"+id_album;
       return url;
   }
}