/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author JEYCOB
 */
@Controller
public class MainController {
    
    // Modulo Usuario
    @GetMapping({"/","/home","/index"})
    public String inicio(){
        return "login";
    }
    // Final Modulo Usuario
    
    
    
    
    
}
