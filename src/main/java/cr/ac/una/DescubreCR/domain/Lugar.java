package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalTime;

/**
 *
 * @author josue
 */
@Entity
@Table(name="tb_lugares")
public class Lugar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String dias_horario;
    
    @Temporal(TemporalType.TIME)
    private LocalTime hora_apertura;
    
    @Temporal(TemporalType.TIME)
    private LocalTime hora_cierre;
    
    private double precio_entrada;
    private String calidad_recep;
    private String imagen;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;
    
    public Lugar() {}

    public Lugar(String codigo, String nombre, String descripcion, String categoria, String dias_horario, LocalTime hora_apertura, LocalTime hora_cierre, double precio_entrada, String calidad_recep, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.dias_horario = dias_horario;
        this.hora_apertura = hora_apertura;
        this.hora_cierre = hora_cierre;
        this.precio_entrada = precio_entrada;
        this.calidad_recep = calidad_recep;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getDias_horario() {
        return dias_horario;
    }

    public void setDias_horario(String dias_horario) {
        this.dias_horario = dias_horario;
    }
    
     public LocalTime getHora_apertura() {
        return hora_apertura;
    }

    public void setHora_apertura(LocalTime hora_apertura) {
        this.hora_apertura = hora_apertura;
    }

    public LocalTime getHora_cierre() {
        return hora_cierre;
    }

    public void setHora_cierre(LocalTime hora_cierre) {
        this.hora_cierre = hora_cierre;
    }

    public double getPrecio_entrada() {
        return precio_entrada;
    }

    public void setPrecio_entrada(double precio_entrada) {
        this.precio_entrada = precio_entrada;
    }

    public String getCalidad_recepcion_telefonica() {
        return calidad_recep;
    }

    public void setCalidad_recepcion_telefonica(String calidad_recepcion_telefonica) {
        this.calidad_recep = calidad_recepcion_telefonica;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}
