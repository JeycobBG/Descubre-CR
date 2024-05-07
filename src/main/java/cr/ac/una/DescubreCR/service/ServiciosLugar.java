package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.DataLugar;
import cr.ac.una.DescubreCR.domain.Lugar;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author josue
 */
public class ServiciosLugar {
    
    public static void insertar(Lugar lugar) throws SQLException{
        new DataLugar().insertar(lugar);
    }
    
    public static LinkedList<Lugar> listar() throws SQLException{
        return new DataLugar().listar();
    }
    
    public static Page<Lugar> listarAdmin(Pageable pageable) throws SQLException{
        return new DataLugar().listarAdmin(pageable);
    }
    
    public static boolean eliminar(String nombre) throws SQLException{
         return new DataLugar().eliminar(nombre);
    }
    
    public static Lugar consultarEspPorCodigo(String codigo) throws SQLException{
        return new DataLugar().consultarEspPorCodigo(codigo);
    }
    
    public static Lugar consultarEspPorNombre(String nombre) throws SQLException{
        return new DataLugar().consultarEspPorNombre(nombre);
    }
    
    public static Page<Lugar> consultarPorNombre(Pageable pageable, String nombre) throws SQLException{
        return new DataLugar().consultarPorNombre(pageable, nombre);
    }
    
    public static boolean reguardar(Lugar lugar) throws SQLException{
        return new DataLugar().actualizar(lugar);
    }
    
    public static LinkedList<String> getCategorias(){
        return new DataLugar().getCategorias();
    }
    
}
