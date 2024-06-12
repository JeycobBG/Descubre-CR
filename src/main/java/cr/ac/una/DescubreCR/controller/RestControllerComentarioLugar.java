package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.service.LugarService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import cr.ac.una.DescubreCR.domain.ComentarioLugar;
import cr.ac.una.DescubreCR.domain.Lugar;
import cr.ac.una.DescubreCR.service.IServiciosComentarioLugar;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/apiComentarios")
public class RestControllerComentarioLugar {

    @Autowired
    private IServiciosComentarioLugar comentariosLugarServ;

    @Autowired
    private LugarService lugarService;

    @PostMapping("/nuevoComentario")
    public ResponseEntity<?> guardarNuevoComentario(@RequestBody ComentarioLugar comentario) throws SQLException {
        
        if (comentariosLugarServ.existe(comentario.getCodigo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ocurrió un error al guardar el comentario. Inténtelo de nuevo.");
        } else {
            comentario.setFecha(LocalDate.now());
            comentario.setCantidadLikes(0);
            comentario.setCantidadDislikes(0);
            comentario.setLugar(lugarService.consultarEspPorCodigo(comentario.getLugar().getCodigo()));

            comentariosLugarServ.guardar(comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body("¡El comentario se ha guardado con éxito!");
        }
    }

    @GetMapping("/listarAPI")
    public ResponseEntity<List<ComentarioLugar>> listar(@RequestParam("codigoLugar") String codigoLugar) throws SQLException {
        Lugar lugar = lugarService.consultarEspPorCodigo(codigoLugar);
        List<ComentarioLugar> comentarios = comentariosLugarServ.listar(lugar);

        return ResponseEntity.ok(comentarios);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarComentario(@RequestBody ComentarioLugar comentario) throws SQLException {
        if (comentariosLugarServ.buscar(comentario.getCodigo()) != null) {
            comentario.setFecha(LocalDate.now());
            comentario.setLugar(lugarService.consultarEspPorCodigo(comentario.getLugar().getCodigo()));

            comentariosLugarServ.guardar(comentario);
            return ResponseEntity.ok("¡El comentario se ha actualizado con éxito!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un comentario con el código solicitado.");
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarComentario(@RequestParam("codigo") String codigo) {
        if (comentariosLugarServ.eliminar(codigo)) {
            return ResponseEntity.ok("Se ha eliminado el comentario con código " + codigo + ".");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el comentario con código " + codigo + ".");
        }
    }
    
    @GetMapping("/verDetalles")
    @ResponseBody
    public ComentarioLugar obtenerDetalles(@RequestParam("codigo") String codigo) {
        return comentariosLugarServ.buscar(codigo);
    }

}