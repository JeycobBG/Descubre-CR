package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.DataColaboradores;
import cr.ac.una.DescubreCR.domain.ColaboradorEmpresarial;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordi
 */
public class ColaboradorServices {
    public static void insertar(ColaboradorEmpresarial colab) {
        try {
            new DataColaboradores().insertar(colab);
        } catch (SQLException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static LinkedList<ColaboradorEmpresarial> getColaboradores(){
        LinkedList<ColaboradorEmpresarial> colaboradores;
        try {
            colaboradores = new DataColaboradores().getColaboradores();
        } catch (SQLException ex) {
            colaboradores = null;
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
       return colaboradores;
    }
        
    public static boolean eliminar(String ide) {
        boolean result = true;
        
        try {
            result = new DataColaboradores().eliminar(ide);
        } catch (SQLException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static boolean modificar(ColaboradorEmpresarial cola) {
        boolean modificado = false;
        try {
            modificado = new DataColaboradores().modificar(cola);
        } catch (SQLException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modificado;
    }
    
    public static ColaboradorEmpresarial buscar(String ide) {
        
         ColaboradorEmpresarial usuario = null;
        try {
            usuario = new DataColaboradores().buscar(ide);
            if(usuario != null){
                System.out.println("Colaborador encontrado ");
            }else{
                System.out.println("El colaborador no existe");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Identificador en buscar services = "+ide);
        return usuario;
    }
}
