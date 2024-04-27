package cr.ac.una.DescubreCR.domain;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Gerald Calderón
 */
public class Articulo {
    private int idArticulo;
    private String identificador;
    private String titulo;
    private String tema;
    private String nombreAutor;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private String acercaDelAutor;
    private String textoArticulo;

    public Articulo() {
    }

    public Articulo(int idArticulo,String identificador, String titulo, String tema, String nombreAutor, Date fecha, String acercaDelAutor, String textoArticulo) {
        this.idArticulo = idArticulo;
        this.identificador = identificador;
        this.titulo = titulo;
        this.tema = tema;
        this.nombreAutor = nombreAutor;
        this.fecha = fecha;
        this.acercaDelAutor = acercaDelAutor;
        this.textoArticulo = textoArticulo;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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

  
}
