/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;

import cr.ac.una.DescubreCR.service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JEYCOB
 */
@Controller
@RequestMapping("/provincia")
public class ControllerProvincia {
    
    @Autowired
    private ProvinciaService provinciaService;
    
    @GetMapping({"/formularioProvincias"})
    public String mostrarFormulario(){
        return "/provincia/form_provincias";
    }
    
    @PostMapping("/guardar")
    public String mostrarFormulario(
        @RequestParam("provincia") String nombreProvincia,
        @RequestParam("imagen") MultipartFile imagen){
        
        System.out.println("entro a guardar");
        // service se encarga de manejar los datos de la provincia mejor...
        provinciaService.guardar(nombreProvincia, imagen);
        return "/provincia/form_provincias";
    }
}