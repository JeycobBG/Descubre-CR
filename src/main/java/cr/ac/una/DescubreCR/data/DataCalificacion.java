/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;

/**
 *
 * @author JasonVoorhees
 *//*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author JasonVoorhees
 */
import cr.ac.una.DescubreCR.domain.Calificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

public class DataCalificacion extends ConectarDB {

    private static final String TB_CALIFICACIONES = "tb_calificaciones";
    private static final String PUNTUACION = "puntuacion";
    private static final String FECHA = "fecha";
    private static final String CATEGORIA = "categoria";
    private static final String UTILIDAD = "utilidad";
    private static final String TIPO_USUARIO = "tipoUsuario";
    private static final String ETIQUETAS_ASOCIADAS = "etiquetasAsociadas";

public boolean insertarCalificacion(Calificacion calificacion) throws SQLException {
    String sql = "INSERT INTO " + TB_CALIFICACIONES + " (" + PUNTUACION + ", " + FECHA + ", " + CATEGORIA + ", "
            + UTILIDAD + ", " + TIPO_USUARIO + ", " + ETIQUETAS_ASOCIADAS + ") VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection connection = conectar();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, calificacion.getPuntuacion());
        Date fecha = calificacion.getFecha();     
        Timestamp timestamp = new Timestamp(fecha.getTime());
        statement.setTimestamp(2, timestamp);
        statement.setString(3, calificacion.getCategoria());
        statement.setString(4, calificacion.getUtilidad());
        statement.setString(5, calificacion.getTipoUsuario());
        statement.setString(6, calificacion.getEtiquetasAsociadas());

        int rowsInserted = statement.executeUpdate();
        return rowsInserted > 0;
    }
}


    public LinkedList<Calificacion> obtenerTodasLasCalificaciones() throws SQLException {
        LinkedList<Calificacion> calificaciones = new LinkedList<>();
        String sql = "SELECT * FROM " + TB_CALIFICACIONES;

        try (Connection connection = conectar();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Calificacion calificacion = new Calificacion();
                calificacion.setPuntuacion(resultSet.getInt(PUNTUACION));
                calificacion.setFecha(resultSet.getDate(FECHA));
                calificacion.setCategoria(resultSet.getString(CATEGORIA));
                calificacion.setUtilidad(resultSet.getString(UTILIDAD));
                calificacion.setTipoUsuario(resultSet.getString(TIPO_USUARIO));
                calificacion.setEtiquetasAsociadas(resultSet.getString(ETIQUETAS_ASOCIADAS));

                calificaciones.add(calificacion);
            }
        }

        return calificaciones;
    }

    public boolean eliminarCalificacion(int idCalificacion) throws SQLException {
        String sql = "DELETE FROM " + TB_CALIFICACIONES + " WHERE id_calificacion = ?";

        try (Connection connection = conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idCalificacion);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    public Calificacion consultarCalificacion(int idCalificacion) throws SQLException {
        String sql = "SELECT * FROM " + TB_CALIFICACIONES + " WHERE id_calificacion = ?";
        Calificacion calificacion = null;

        try (Connection connection = conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idCalificacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    calificacion = new Calificacion();
                    calificacion.setPuntuacion(resultSet.getInt(PUNTUACION));
                    calificacion.setFecha(resultSet.getDate(FECHA));
                    calificacion.setCategoria(resultSet.getString(CATEGORIA));
                    calificacion.setUtilidad(resultSet.getString(UTILIDAD));
                    calificacion.setTipoUsuario(resultSet.getString(TIPO_USUARIO));
                    calificacion.setEtiquetasAsociadas(resultSet.getString(ETIQUETAS_ASOCIADAS));
                }
            }
        }

        return calificacion;
    }

  public boolean actualizarCalificacion(Calificacion calificacion) throws SQLException {
    String sql = "UPDATE " + TB_CALIFICACIONES + " SET " +
                 PUNTUACION + " = ?, " +
                 FECHA + " = ?, " +
                 CATEGORIA + " = ?, " +
                 UTILIDAD + " = ?, " +
                 TIPO_USUARIO + " = ?, " +
                 ETIQUETAS_ASOCIADAS + " = ? " +
                 "WHERE id_calificacion = ?";

    try (Connection connection = conectar();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, calificacion.getPuntuacion());
        Date fecha = calificacion.getFecha();     
        Timestamp timestamp = new Timestamp(fecha.getTime());
        statement.setTimestamp(2, timestamp);
        statement.setString(3, calificacion.getCategoria());
        statement.setString(4, calificacion.getUtilidad());
        statement.setString(5, calificacion.getTipoUsuario());
        statement.setString(6, calificacion.getEtiquetasAsociadas());
        statement.setInt(7, calificacion.getId());

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    }
}
}
