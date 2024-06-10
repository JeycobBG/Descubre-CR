/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import cr.ac.una.DescubreCR.domain.Persona;
import cr.ac.una.DescubreCR.domain.Usuario;
import cr.ac.una.DescubreCR.service.IUsuariosServices;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


/**
 *
 * @author kvene
 */
@Controller
@RequestMapping("/usuarios")
public class userController {
   
    @Autowired
    private IUsuariosServices usuariosServices;
    
    //@Autowired
    //private IPersonaService personaServices;
    
    @GetMapping("/menuPrincipal")
    public String menuPrincipal(){
        return "index";
    }
    
    @GetMapping("/listaUsuarios")
    public String listar(@PageableDefault(size=5, page=0) Pageable pageable, Model model) throws SQLException {
        Page<Usuario> page = usuariosServices.listar(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        model.addAttribute("page", page);

        List<Integer> opcionesCantidadPorPagina = Arrays.asList(5, 10, 25, 50, 100);
        int paginasTotal = page.getTotalPages();
        int paginaActual = page.getNumber();
        int inicio = Math.max(1, paginaActual);
        int termina = Math.min(paginaActual + 5, paginasTotal);
        
        if (paginasTotal > 0) {
            List<Integer> numPaginas = new ArrayList<>();
            for (int i = inicio; i <= termina; i++) {
                numPaginas.add(i);
            }
            model.addAttribute("pageNumbers", numPaginas);
        }
        model.addAttribute("pageSizeOptions", opcionesCantidadPorPagina);

        return "usuario/lista_usuarios";
    }
    
    
    @GetMapping("/formRegistrar")
    public String agregar(){
        return "usuario/form_usuario";
    }


    @GetMapping("/registrar") 
    public String save( 
        RedirectAttributes flash, 
        @RequestParam("nombre") String nombre,
        @RequestParam("apellido") String apellido,
        @RequestParam("cedula") String cedula,
        @RequestParam("idioma") String idioma,
        @RequestParam("nacionalidad") String nacionalidad,
        @RequestParam("fechaNacimiento") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaNacimiento,
        @RequestParam("telefono") String telefono,
        @RequestParam("nombreUsuario") String nombreUsuario,
        @RequestParam("password") String contraseña,
        @RequestParam("tipoUsuario") String tipoUsuario,
        @RequestParam("correo") String correo) throws SQLException {

        Date fechaRegistro = new Date(); 
        Persona person = new Persona(
            nombre,
            apellido,
            cedula,
            idioma,
            nacionalidad,
            fechaNacimiento,
            telefono
        );
        Usuario user = new Usuario(
            nombreUsuario,
            usuariosServices.encriptar(contraseña),
            tipoUsuario,
            correo,
            fechaRegistro,
            person.getNombre(),
            person.getApellido(),
            person.getCedula(),
            person.getIdioma(),
            person.getNacionalidad(),
            person.getFechaNacimiento(),
            person.getTelefono()
        );

        
        if(usuariosServices.buscar(cedula) != null){
            flash.addFlashAttribute("error","Cédula ya existe");
        }else{
            if(usuariosServices.guardar(user)){
                flash.addFlashAttribute("exito","Se a registrado con éxito un usuario");
            }else{
                flash.addFlashAttribute("error","No se a podido registrar un usuario");
            }
        }
        
        

        return "redirect:/usuarios/formRegistrar"; // Redirigir a la página del formulario
    }


    
    @PostMapping("/login")
    public String login(@RequestParam("usuario") String usuario,
                        @RequestParam("password") String contrasena) throws SQLException{
        
        if(!usuariosServices.login(usuario,usuariosServices.encriptar(contrasena))){
            return "redirect:login";
        }
        return "index";
    }
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("per_id") int per_id, RedirectAttributes flash){

        if(!usuariosServices.eliminar(per_id)){
            return "error";
        }
        flash.addFlashAttribute("exito","Se elimino con exito un usuario");
        return "redirect:/usuarios/listaUsuarios";
  
    }
    
    @GetMapping("/buscarUsuario")
    public String buscarExistencia(@RequestParam("per_id") int per_id, Model model) throws SQLException, JsonProcessingException {
        Usuario usuario = usuariosServices.obtenerPorId(per_id);
        
        if(usuario == null){
            return "error";
        }
        model.addAttribute("usuario", usuario);
        
        return "usuario/modify_usuario"; 
    }

    
    @PostMapping("/modificar")
    public String modificarUsuario(
        RedirectAttributes flash, 
        @RequestParam("nombre") String nombre,
        @RequestParam("apellido") String apellido,
        @RequestParam("cedula") String cedula,
        @RequestParam("idioma") String idioma,
        @RequestParam("nacionalidad") String nacionalidad,
        @RequestParam("fechaNacimiento") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaNacimiento,
        @RequestParam("telefono") String telefono,
        @RequestParam("nombreUsuario") String nombreUsuario,
        @RequestParam("contraseña") String contraseña,
        @RequestParam("tipoUsuario") String tipoUsuario,
        @RequestParam("correo") String correo,
        @RequestParam("fechaRegistro") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaRegistro){

        Usuario usuarioExistente = usuariosServices.buscar(cedula);

        if (usuarioExistente != null) {
            usuarioExistente.setNombre(nombre);
            usuarioExistente.setApellido(apellido);
            usuarioExistente.setIdioma(idioma);
            usuarioExistente.setNacionalidad(nacionalidad);
            usuarioExistente.setFechaNacimiento(fechaNacimiento);
            usuarioExistente.setTelefono(telefono);

            usuarioExistente.setNombreUsuario(nombreUsuario);
            usuarioExistente.setContrasena(contraseña);
            usuarioExistente.setTipoUsuario(tipoUsuario);
            usuarioExistente.setCorreo(correo);
            usuarioExistente.setFechaRegistro(fechaRegistro);

            if (usuariosServices.guardar(usuarioExistente)) {
                flash.addFlashAttribute("exito", "Usuario modificado");
                return "redirect:/usuarios/listaUsuarios";
            }
        } else {
            flash.addFlashAttribute("error", "No se encontró el usuario");
        }

        return "error";
    }

    
}
