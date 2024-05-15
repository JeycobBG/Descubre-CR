/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.DataClimas;
import cr.ac.una.DescubreCR.domain.Clima;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordi
 */
public class ClimaServices {
    public static void insertar(Clima cli) {
        try {
            new DataClimas().insertar(cli);
        } catch (SQLException ex) {
            Logger.getLogger(ClimaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static LinkedList<Clima> getClimas(){
        LinkedList<Clima> climas;
        try {
            climas = new DataClimas().getClimas();
        } catch (SQLException ex) {
            climas = null;
            Logger.getLogger(ClimaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
       return climas;
    }
        
    public static boolean eliminar(String codigo) {
        boolean result = true;
        
        try {
            result = new DataClimas().eliminar(codigo);
        } catch (SQLException ex) {
            Logger.getLogger(ClimaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static boolean modificar(Clima clim) {
        boolean modificado = false;
        try {
            modificado = new DataClimas().modificar(clim);
        } catch (SQLException ex) {
            Logger.getLogger(ClimaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modificado;
    }
    
    public static Clima buscar(String codigo) {
        
         Clima usuario = null;
        try {
            usuario = new DataClimas().buscar(codigo);
            if(usuario != null){
                System.out.println("Clima encontrado ");
            }else{
                System.out.println("El clima no existe");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClimaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Clima en buscar services = "+codigo);
        return usuario;
    }
}
