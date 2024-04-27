/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    
    public static Lugar consultarPorCodigo(String codigo) throws SQLException{
        return new DataLugar().consultarPorCodigo(codigo);
    }
    
    public static Lugar consultarPorNombre(String nombre) throws SQLException{
        return new DataLugar().consultarPorNombre(nombre);
    }
    
    public static boolean reguardar(Lugar lugar) throws SQLException{
        return new DataLugar().actualizar(lugar);
    }
    
    public static LinkedList<String> getCategorias(){
        return new DataLugar().getCategorias();
    }
    
    public static LinkedList<String> getProvincias(){
        return new DataLugar().getProvincias();
    }
    
}
