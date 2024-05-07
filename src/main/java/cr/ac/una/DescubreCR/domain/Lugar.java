/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.domain;

import java.time.LocalTime;

/**
 *
 * @author josue
 */
public class Lugar {
    
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String dias_horario;
    private LocalTime hora_apertura;
    private LocalTime hora_cierre;
    private double precio_entrada;
    private String provincia;
    private String canton;
    private String distrito;
    private String calidad_recepcion_telefonica;
    private String imagen;

    public Lugar() {
    }

    public Lugar(String codigo, String nombre, String descripcion, String categoria, String dias_horario, LocalTime hora_apertura, LocalTime hora_cierre, double precio_entrada, String provincia, String canton, String distrito, String calidad_recepcion_telefonica, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.dias_horario = dias_horario;
        this.hora_apertura = hora_apertura;
        this.hora_cierre = hora_cierre;
        this.precio_entrada = precio_entrada;
        this.provincia = provincia;
        this.canton = canton;
        this.distrito = distrito;
        this.calidad_recepcion_telefonica = calidad_recepcion_telefonica;
        this.imagen = imagen;
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

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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

    public String getCalidad_recepcion_telefonica() {
        return calidad_recepcion_telefonica;
    }

    public void setCalidad_recepcion_telefonica(String calidad_recepcion_telefonica) {
        this.calidad_recepcion_telefonica = calidad_recepcion_telefonica;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
}
