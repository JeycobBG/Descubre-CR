package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author josue
 */
@Entity
@Table(name="tb_comentarioLugar")
public class ComentarioLugar {
    
    @Id
    private String codigoComentario;
    
    private String contenido;
    
    @Temporal(TemporalType.DATE)
    private LocalDate fecha;
    
    private int cantidadLikes;
    private int cantidadDislikes;
    private boolean visibilidad;
    private String etiquetas;
    
    
    /*@ManyToOne
    @JoinColumn(name="id_usuario", nullable=false)
    private Usuario usuario;*/
    
    private String nombreUsuario;
    
    @ManyToOne
    @JoinColumn(name="id_lugar", nullable=false)
    private Lugar lugar;

    public ComentarioLugar() {
    }

    public ComentarioLugar(String codigoComentario, String contenido, LocalDate fecha, int cantidadLikes, int cantidadDislikes, boolean visibilidad, String etiquetas, String nombreUsuario, Lugar lugar) {
        this.codigoComentario = codigoComentario;
        this.contenido = contenido;
        this.fecha = fecha;
        this.cantidadLikes = cantidadLikes;
        this.cantidadDislikes = cantidadDislikes;
        this.visibilidad = visibilidad;
        this.etiquetas = etiquetas;
        this.nombreUsuario = nombreUsuario;
        this.lugar = lugar;
    }

    public String getCodigo() {
        return codigoComentario;
    }

    public void setCodigo(String codigo) {
        this.codigoComentario = codigo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getCantidadLikes() {
        return cantidadLikes;
    }

    public void setCantidadLikes(int cantidadLikes) {
        this.cantidadLikes = cantidadLikes;
    }

    public int getCantidadDislikes() {
        return cantidadDislikes;
    }

    public void setCantidadDislikes(int cantidadDislikes) {
        this.cantidadDislikes = cantidadDislikes;
    }

    public boolean isVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }
    
}
