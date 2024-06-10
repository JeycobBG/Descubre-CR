/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JEYCOB
 */
@Entity
@Table(name = "tb_album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nombreAutor;
    
    private int provincia;
    
    private String nombreLugar;
    
    private String descripcion;
    
    private String etiquetasAsociadas;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
             name = "tb_album_imagen",
             joinColumns = @JoinColumn(name = "id_album"),
             inverseJoinColumns = @JoinColumn(name = "id_imagen")
     )
    private List<Imagen> imagenes;
    
    @Temporal(TemporalType.DATE)
    private LocalDate fechaCreacion;
    
    private int id_autor;
    
    public Album(){
        imagenes = new ArrayList<>();
    }

    public Album(int id, String nombreAutor, int provincia, String nombreLugar, String descripcion, String etiquetasAsociadas, ArrayList<Imagen> imagenes, LocalDate fechaCreacion, int id_autor) {
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

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
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
