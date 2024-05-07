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
    @GetMapping({"/","/home","/login"})
    public String inicio(){
        return "login";
    }
    // Final Modulo Usuario
    
    
    
    
    
}
