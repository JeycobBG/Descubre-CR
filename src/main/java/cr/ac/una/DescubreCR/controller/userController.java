/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.Persona;
import cr.ac.una.DescubreCR.domain.Usuario;
import cr.ac.una.DescubreCR.service.UsuariosServices;
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
    

    @GetMapping("/listaUsuarios")
    public String findAll(@PageableDefault(size=5, page=0) Pageable pageable, Model model) throws SQLException{
        Page<Usuario> page = UsuariosServices.listar(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        model.addAttribute("page", page);
        List<Integer> pageSizeOptions = Arrays.asList(10,20, 50, 100);
        
        var totalPages = page.getTotalPages();
        var currentPage = page.getNumber();
        var start = Math.max(1, currentPage);
        var end = Math.min(currentPage + 5, totalPages);

        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                    pageNumbers.add(i);
            }

            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("pageSizeOptions", pageSizeOptions);


        return "usuario/lista_usuarios";
    }
    
    
    @GetMapping("/formRegistrar")
    public String agregar(){
        return "usuario/form_usuario";
    }
    /*
    @GetMapping("/listaUsuarios")
    public String listar(Model model){
        System.out.println("Lista de Usuarios");
        LinkedList<Usuario> usuarios = UsuariosServices.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuario/lista_usuarios";
    }*/

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
            contraseña,
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

        if(buscarExistencia(user.getCedula())){
            flash.addFlashAttribute("error","Cédula ya existe");
        }else{
            if(UsuariosServices.insertar(person)){
                if(UsuariosServices.insertar(user) ){
                    flash.addFlashAttribute("exito","Se a registrado con éxito un usuario");
                }else{
                    flash.addFlashAttribute("error","No se a podido registrar un usuario");
                }
            }else{
                flash.addFlashAttribute("error","No se a podido registrar un usuario");
            }
        }
        
        

        return "redirect:/usuarios/formRegistrar"; // Redirigir a la página del formulario
    }


    
    @GetMapping("/login")
    public String login(@RequestParam("usuario") String usuario,
                        @RequestParam("password") String contraseña) throws SQLException{
        
        if(!UsuariosServices.login(usuario,UsuariosServices.encriptar(contraseña))){
            return "redirect:login";
        }
        return "redirect:index";
    }
    
    @GetMapping("/eliminar")
    public String eliminar(RedirectAttributes flash, 
        @RequestParam("cedula") String cedula){

        if(!UsuariosServices.eliminar(cedula)){
            return "error";
        }
        flash.addFlashAttribute("exito","Se elimino con exito un usuario");
        return "redirect:/usuarios/listaUsuarios";
  
    }
    
    @GetMapping("/buscarUsuario")
    public String buscarExistencia(@RequestParam("cedula") String cedula, Model model){
        Usuario usuario = UsuariosServices.buscar(cedula);
        System.out.println("Cedula = " +  cedula);
        if(usuario == null){
            return "error";
        }
        model.addAttribute("usuario", usuario);
        return "usuario/modify_usuario"; 
    }

    public boolean buscarExistencia(String cedula){
        return UsuariosServices.buscar(cedula) != null;
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
        
        Usuario user = new Usuario(
            nombreUsuario,
            contraseña,
            tipoUsuario,
            correo,
            fechaRegistro,
            nombre,
            apellido,
            cedula,
            idioma,
            nacionalidad,
            fechaNacimiento,
            telefono
        );
        System.out.println("Controller");

        if(UsuariosServices.modificar(user)) {
            flash.addFlashAttribute("exito","Usuario modificado");
            return "redirect:/usuarios/listaUsuarios";
        }
        return "error";
    }

    
}
