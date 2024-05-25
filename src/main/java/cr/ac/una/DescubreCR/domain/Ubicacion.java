
package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;

/**
 *
 * @author JEYCOB
 */

@Entity
@Table(name="tb_ubicacion")
public class Ubicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String direccion;
    
    @Column(nullable = false)
    private String nombreProvincia;
    
    @Column(nullable = false)
    private String canton;
    
    @Column(nullable = false)
    private String distrito;
    
    @Column(nullable = true)
    private String nombreAutor;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaCreacion;
    
    @ManyToOne
    @JoinColumn(name = "provincia")
    private Provincia provincia;
    
    @OneToOne
    @JoinColumn(name = "lugar")
    private Lugar lugarTuristico;
    
    
    public Ubicacion(){}

    public Ubicacion(int id, String direccion, String nombreProvincia, String canton, String distrito, String nombreAutor, LocalDate fechaCreacion, Provincia provincia, Lugar lugarTuristico) {
        this.id = id;
        this.direccion = direccion;
        this.nombreProvincia = nombreProvincia;
        this.canton = canton;
        this.distrito = distrito;
        this.nombreAutor = nombreAutor;
        this.fechaCreacion = fechaCreacion;
        this.provincia = provincia;
        this.lugarTuristico = lugarTuristico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Lugar getLugarTuristico() {
        return lugarTuristico;
    }

    public void setLugarTuristico(Lugar lugarTuristico) {
        this.lugarTuristico = lugarTuristico;
    }
}