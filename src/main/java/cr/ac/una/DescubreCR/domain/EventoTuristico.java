package cr.ac.una.DescubreCR.domain;

import java.sql.Time;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/*
 * @author Gerald Calderón
 */
public class EventoTuristico {
    private int id;
    private String codigo;
    private String nombreEvento;
    private String descripcion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private int LugarForaneo;
    private String ubicacion;
    private String Titulo;
    private String nombreAutor;
     @DateTimeFormat(pattern = "HH:mm")
    private Time horaInicial;
    @DateTimeFormat(pattern = "HH:mm")
    private Time horaFinal;

    public EventoTuristico() {}

    public EventoTuristico(int id, String codigo, String nombreEvento, String descripcion, Date fecha, int LugarForaneo, String ubicacion, String Titulo, String nombreAutor, Time horaInicial, Time horaFinal) {
        this.id = id;
        this.codigo = codigo;
        this.nombreEvento = nombreEvento;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.LugarForaneo = LugarForaneo;
        this.ubicacion = ubicacion;
        this.Titulo = Titulo;
        this.nombreAutor = nombreAutor;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
    }
    
    public int getLugarForaneo() {
        return LugarForaneo;
    }

    public void setLugarForaneo(int LugarForaneo) {
        this.LugarForaneo = LugarForaneo;
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

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Time getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Time horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }

    
}
