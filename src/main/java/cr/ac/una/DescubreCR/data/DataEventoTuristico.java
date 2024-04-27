/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;

import cr.ac.una.DescubreCR.domain.EventoTuristico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author JEYCOB
 */
public class DataEventoTuristico extends ConectarDB {
    
    private static final String TB_EVENTO_TURISTICO = "tb_evento_turistico";
    private static final String ID = "ID";
    private static final String CODIGO = "CODIGO";
    private static final String NOMBRE_EVENTO = "NOMBRE_EVENTO";
    private static final String DESCRIPCION = "DESCRIPCION";
    private static final String FECHA = "FECHA";
    private static final String UBICACION = "UBICACION";
    private static final String TITULO = "TITULO";
    private static final String NOMBRE_AUTOR = "NOMBRE_AUTOR";
    private static final String HORA_INICIAL = "HORA_INICIO";
    private static final String HORA_FINAL = "HORA_FINAL";
    private static final String ETIQUETAS_ASOCIADAS = "ETIQUETAS_ASOCIADAS";
    
public static void actualizar(EventoTuristico evento) throws SQLException {
    String sql = "UPDATE " + TB_EVENTO_TURISTICO + " SET " +
            CODIGO + " = ?," +
            NOMBRE_EVENTO + " = ?," +
            DESCRIPCION + " = ?," +
            NOMBRE_AUTOR + " = ?," +
            FECHA + " = ?," +
            TITULO + " = ?," +
            UBICACION + " = ?," +
            HORA_INICIAL + " = ?," +
            HORA_FINAL + " = ?," +
            ETIQUETAS_ASOCIADAS + " = ? WHERE " + ID + " = ?";
    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {
        statement.setString(1, evento.getCodigo());
        statement.setString(2, evento.getNombreEvento());
        statement.setString(3, evento.getDescripcion());
        statement.setString(4, evento.getNombreAutor());
      
        // Configurando java.sql.Date para la fecha
        java.util.Date utilDate = evento.getFecha();
        if (utilDate != null) {
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            statement.setDate(5, sqlDate);
        } else {
            statement.setNull(5, java.sql.Types.DATE);
        }

        statement.setString(6, evento.getTitulo());
        statement.setString(7, evento.getUbicacion());
        
        // Configurando java.sql.Time para la hora inicial y final
        if (evento.getHoraInicial() != null) {
            statement.setTime(8, java.sql.Time.valueOf(evento.getHoraInicial().toString()));
        } else {
            statement.setNull(8, java.sql.Types.TIME);
        }
        
        if (evento.getHoraFinal() != null) {
            statement.setTime(9, java.sql.Time.valueOf(evento.getHoraFinal().toString()));
        } else {
            statement.setNull(9, java.sql.Types.TIME);
        }
        
        statement.setString(10, String.join(",", evento.getEtiquetasAsociadas()));
        statement.setInt(11, evento.getId());
        
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("No se pudo actualizar el evento con ID: " + evento.getId());
        }
    }
}

    
    
public static EventoTuristico obtenerPorID(int idEvento) throws SQLException {
    String sql = "SELECT * FROM " + TB_EVENTO_TURISTICO + " WHERE ID = ?";
    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {
        statement.setInt(1, idEvento);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new EventoTuristico(
                    resultSet.getInt(ID),
                    resultSet.getString(CODIGO),
                    resultSet.getString(NOMBRE_EVENTO),
                    resultSet.getString(DESCRIPCION),
                    resultSet.getDate(FECHA),
                    resultSet.getString(UBICACION),
                    resultSet.getString(TITULO),
                    resultSet.getString(NOMBRE_AUTOR),
                    resultSet.getTime(HORA_INICIAL),
                    resultSet.getTime(HORA_FINAL),
                    Arrays.asList(resultSet.getString(ETIQUETAS_ASOCIADAS).split(","))
                );
            } else {
                return null;
            }
        }
    }
}

public static EventoTuristico obtenerPorCodigo(String codigo) throws SQLException {
    String sql = "SELECT * FROM " + TB_EVENTO_TURISTICO + " WHERE CODIGO = ?";
    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {
        statement.setString(1, codigo);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new EventoTuristico(
                    resultSet.getInt(ID),
                    resultSet.getString(CODIGO),
                    resultSet.getString(NOMBRE_EVENTO),
                    resultSet.getString(DESCRIPCION),
                    resultSet.getDate(FECHA),
                    resultSet.getString(UBICACION),
                    resultSet.getString(TITULO),
                    resultSet.getString(NOMBRE_AUTOR),
                    resultSet.getTime(HORA_INICIAL),
                    resultSet.getTime(HORA_FINAL),
                    Arrays.asList(resultSet.getString(ETIQUETAS_ASOCIADAS).split(","))
                );
            } else {
                return null;
            }
        }
    }
}

public boolean eliminar(int idEvento) throws SQLException {
    String sql = "DELETE FROM " + TB_EVENTO_TURISTICO + " WHERE ID = ?";
    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {
        statement.setInt(1, idEvento);
        int resultado = statement.executeUpdate();
        return (resultado == 1);
    }
}

public  EventoTuristico insertar(EventoTuristico evento) throws SQLException {
    String sql = "INSERT INTO " + TB_EVENTO_TURISTICO + " (" + 
        CODIGO + ", " + NOMBRE_EVENTO + ", " + DESCRIPCION + ", " + 
        NOMBRE_AUTOR + ", " + FECHA + ", " + TITULO + ", " + 
        UBICACION + ", " + HORA_INICIAL + ", " + HORA_FINAL + ", " + 
        ETIQUETAS_ASOCIADAS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, evento.getCodigo());
        statement.setString(2, evento.getNombreEvento());
        statement.setString(3, evento.getDescripcion());
        statement.setString(4, evento.getNombreAutor());
       // Configurando java.sql.Date para la fecha
        java.util.Date utilDate = evento.getFecha();
        if (utilDate != null) {
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            statement.setDate(5, sqlDate);
        } else {
            statement.setNull(5, java.sql.Types.DATE);
        }

        statement.setString(6, evento.getTitulo());
        statement.setString(7, evento.getUbicacion());
        
        // Configurando java.sql.Time para la hora inicial y final
        if (evento.getHoraInicial() != null) {
            statement.setTime(8, java.sql.Time.valueOf(evento.getHoraInicial().toString()));
        } else {
            statement.setNull(8, java.sql.Types.TIME);
        }
        
        if (evento.getHoraFinal() != null) {
            statement.setTime(9, java.sql.Time.valueOf(evento.getHoraFinal().toString()));
        } else {
            statement.setNull(9, java.sql.Types.TIME);
        }
        statement.setString(10, String.join(",", evento.getEtiquetasAsociadas()));
        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                evento.setId(generatedKeys.getInt(1));  // Assuming EventoTuristico has a setId() method
            } else {
                throw new SQLException("Creating event failed, no ID obtained.");
            }
        }
    }
    return evento;
}


public LinkedList<EventoTuristico> obtenerEventos() throws SQLException {
    LinkedList<EventoTuristico> eventos = new LinkedList<>();
    String sql = "SELECT * FROM " + TB_EVENTO_TURISTICO;
    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            EventoTuristico evento = new EventoTuristico(   
                resultSet.getInt(ID),
                resultSet.getString(CODIGO),
                resultSet.getString(NOMBRE_EVENTO),
                resultSet.getString(DESCRIPCION),
                resultSet.getDate(FECHA),
                resultSet.getString(UBICACION),
                resultSet.getString(TITULO),
                resultSet.getString(NOMBRE_AUTOR),
                resultSet.getTime(HORA_INICIAL),
                resultSet.getTime(HORA_FINAL),
                Arrays.asList(resultSet.getString(ETIQUETAS_ASOCIADAS).split(","))
            );
            eventos.add(evento);
        }
    }
    return eventos;
}
}
