/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

/**
 *
 * @author JasonVoorhees
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author JasonVoorhees
 */



import cr.ac.una.DescubreCR.data.DataCalificacion;
import cr.ac.una.DescubreCR.domain.Calificacion;

import java.sql.SQLException;
import java.util.LinkedList;

public class ServiciosCalificacion {

    public static void insertarCalificacion(Calificacion calificacion) throws SQLException {
        new DataCalificacion().insertarCalificacion(calificacion);
    }

    public static LinkedList<Calificacion> obtenerTodasLasCalificaciones() throws SQLException {
        return new DataCalificacion().obtenerTodasLasCalificaciones();
    }

    public static boolean eliminarCalificacion(int idCalificacion) throws SQLException {
        return new DataCalificacion().eliminarCalificacion(idCalificacion);
    }

    public static Calificacion consultarCalificacion(int idCalificacion) throws SQLException {
        return new DataCalificacion().consultarCalificacion(idCalificacion);
    }

    public static boolean actualizarCalificacion(Calificacion calificacion) throws SQLException {
        return new DataCalificacion().actualizarCalificacion(calificacion);
    }
}
