package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.DataArticulo;
import cr.ac.una.DescubreCR.domain.Articulo;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ArticuloServices {
     
    public static LinkedList<Articulo> obtenerArticulos() throws SQLException {
    LinkedList<Articulo> articulos = new LinkedList<>();
    articulos = new DataArticulo().obtenerArticulos(); 
    return articulos;
    }
    
    public static boolean eliminarArticulo(int idArticulo) {
    boolean resultado = false;
    try {
        DataArticulo dataArticulo = new DataArticulo();
        resultado = dataArticulo.eliminar(idArticulo);
    } catch (SQLException ex) {
        Logger.getLogger(ArticuloServices.class.getName()).log(Level.SEVERE, null, ex);
    }
    return resultado;
    }


    public static Articulo insertarArticulo(Articulo articulo) {
        try {
            return new DataArticulo().insertar(articulo);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
   
    public static Articulo obtenerArticuloPorID(int idArticulo) throws SQLException {
        return DataArticulo.obtenerPorID(idArticulo);
    }
      
   public static Articulo obtenerArticuloPorIdentificador(String identificador) throws SQLException {
        return DataArticulo.obtenerPorIdentificador(identificador);
    }
    
    public static void actualizarArticulo(Articulo articulo) throws SQLException {
    DataArticulo.actualizar(articulo);
    }

    public static Page<Articulo> listarAdmin(Pageable pageable) throws SQLException{
        return new DataArticulo().obtenerArticulosAdmi(pageable);
    }
    
    public static Page<Articulo> obtenerPorTitulo(String titulo,Pageable pageable) throws SQLException{
        return new DataArticulo().obtenerPorTitulo(titulo,pageable);
    }



}
