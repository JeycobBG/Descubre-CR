/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.domain;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author JEYCOB
 */
public class Album {
    private int id;
    private String nombreAutor;
    private int provincia;
    private String nombreLugar;
    private String descripcion;
    private String etiquetasAsociadas;
    private ArrayList<String> imagenes;
    private LocalDate fechaCreacion;
    private int id_autor;
    
    public Album(){}

    public Album(int id, String nombreAutor, int provincia, String nombreLugar, String descripcion, String etiquetasAsociadas, ArrayList<String> imagenes, LocalDate fechaCreacion, int id_autor) {
        this.id = id;
        this.nombreAutor = nombreAutor;
        this.provincia = provincia;
        this.nombreLugar = nombreLugar;
        this.descripcion = descripcion;
        this.etiquetasAsociadas = etiquetasAsociadas;
        this.imagenes = imagenes;
        this.fechaCreacion = fechaCreacion;
        this.id_autor = id_autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEtiquetasAsociadas() {
        return etiquetasAsociadas;
    }

    public void setEtiquetasAsociadas(String etiquetasAsociadas) {
        this.etiquetasAsociadas = etiquetasAsociadas;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }
}
