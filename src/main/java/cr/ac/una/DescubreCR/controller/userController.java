/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.domain.Usuario;
import cr.ac.una.DescubreCR.service.UsuariosServices;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JEYCOB
 */

@Controller
@RequestMapping("/usuarios")
public class userController {
    @GetMapping("/formRegistrar")
    public String agregar(){
        return "usuario/form_usuario";
    }
    
    @GetMapping("/listaUsuarios")
    public String listar(Model model){
        System.out.println("Lista de Usuarios");
        LinkedList<Usuario> usuarios = UsuariosServices.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuario/lista_usuarios";
    }

    
    @GetMapping("/registrar")
    public String save( 
    
        @RequestParam("nombre") String nombre,
        @RequestParam("apellido") String apellido,
        @RequestParam("cedula") String cedula,
        @RequestParam("idioma") String idioma,
        @RequestParam("nacionalidad") String nacionalidad,
        @RequestParam("fechaNacimiento") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaNacimiento,
        @RequestParam("telefono") String telefono,
        @RequestParam("nombreUsuario") String nombreUsuario,
        @RequestParam("password") String contrasena,
        @RequestParam("tipoUsuario") String tipoUsuario,
        @RequestParam("correo") String correo,
        @RequestParam("canton") String canton,
        @RequestParam("provincia") String provincia) throws SQLException {

        Date fechaRegistro = new Date(); 
        Usuario user = new Usuario(nombre, apellido, cedula, idioma, nacionalidad, fechaNacimiento, telefono, nombreUsuario, contrasena, tipoUsuario, correo, canton, provincia, fechaRegistro);
        
        if(UsuariosServices.insertar(user)){
            System.out.println("Se inserto un usuario");
        }
        
       return "redirect:/index";
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
    public String eliminar(@RequestParam("cedula") String cedula){
        boolean elimino = UsuariosServices.eliminar(cedula);
        if(!elimino){
            return "error";
        }
        return "redirect:/usuarios/listaUsuarios";
    }
    
    @GetMapping("/buscarUsuario")
    public String buscarExistencia(@RequestParam("cedula") String cedula, Model model){
        Usuario usuario = UsuariosServices.buscar(cedula);
        if(usuario == null){
            return "error";
        }
        model.addAttribute("usuario", usuario);
        return "usuario/modify_usuario"; 
    }

        
    
    @PostMapping("/modificar")
    public String modificarUsuario(
        
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
        @RequestParam("canton") String canton,
        @RequestParam("provincia") String provincia,
        @RequestParam("fechaRegistro") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaRegistro){
        
        Usuario user = new Usuario(nombre, apellido, cedula, idioma, nacionalidad, fechaNacimiento,telefono, nombreUsuario, contraseña, tipoUsuario,correo, canton, provincia, fechaRegistro);
        
        user.setCedula(cedula);
        
        if(UsuariosServices.modificar(user)) {
            return "redirect:/usuarios/listaUsuarios";
        } else {
            return "error";
        }
    }
}
