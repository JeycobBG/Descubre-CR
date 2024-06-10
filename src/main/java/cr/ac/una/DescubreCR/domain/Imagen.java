package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JEYCOB
 */
@Entity
@Table(name = "tb_imagen")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    private Blob src; // para validar que no sea igual, y evitar que se repitan las imágenes
    
    @Transient
    private String src_String;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso")
    private LocalDate fecha;
    
    @ManyToMany(mappedBy = "imagenes") 
    private List<Album> albums;

    public Imagen(){
        albums = new ArrayList<>();
    }
    
    public Imagen(int id, Blob src, LocalDate fecha) {
        this.id = id;
        this.src = src;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Blob getSrc() {
        return src;
    }

    public void setSrc(Blob src) {
        this.src = src;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public String getSrc_String() {
        return src_String;
    }

    public void setSrc_String(String src_String) {
        this.src_String = src_String;
    }
}