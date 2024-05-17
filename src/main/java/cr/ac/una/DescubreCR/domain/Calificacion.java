/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.domain;

/**
 *
 * @author JasonVoorhees
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author JasonVoorhees
 */
public class Calificacion {
    private int id;
    private int puntuacion;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private String categoria;
    private String utilidad;
    private String tipoUsuario;
    private String etiquetasAsociadas;

    // Constructor vacío
    public Calificacion() {
    }

    // Constructor con todos los atributos
    public Calificacion(int puntuacion, Date fecha, String categoria, String utilidad, String tipoUsuario, String etiquetasAsociadas) {
        this.puntuacion = puntuacion;
        this.fecha = fecha;
        this.categoria = categoria;
        this.utilidad = utilidad;
        this.tipoUsuario = tipoUsuario;
        this.etiquetasAsociadas = etiquetasAsociadas;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(String utilidad) {
        this.utilidad = utilidad;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEtiquetasAsociadas() {
        return etiquetasAsociadas;
    }

    public void setEtiquetasAsociadas(String etiquetasAsociadas) {
        this.etiquetasAsociadas = etiquetasAsociadas;
    }
}

