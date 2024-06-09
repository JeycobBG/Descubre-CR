package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Gerald Calderón
 */
@Entity
@Table(name="tb_articulo")
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idArticulo;
    private String identificador;
    private String titulo;
    private String tema;
    private String descripcion;
    private String nombreAutor;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;
    private String acercaDelAutor;
    private String textoArticulo;

    public Articulo() {
    }

    public Articulo(int idArticulo, String identificador, String titulo, String tema, String descripcion, String nombreAutor, LocalDate fecha, String acercaDelAutor, String textoArticulo) {
        this.idArticulo = idArticulo;
        this.identificador = identificador;
        this.titulo = titulo;
        this.tema = tema;
        this.descripcion = descripcion;
        this.nombreAutor = nombreAutor;
        this.fecha = fecha;
        this.acercaDelAutor = acercaDelAutor;
        this.textoArticulo = textoArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getAcercaDelAutor() {
        return acercaDelAutor;
    }

    public void setAcercaDelAutor(String acercaDelAutor) {
        this.acercaDelAutor = acercaDelAutor;
    }

    public String getTextoArticulo() {
        return textoArticulo;
    }

    public void setTextoArticulo(String textoArticulo) {
        this.textoArticulo = textoArticulo;
    }

     public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }
  
}
