package cr.ac.una.DescubreCR.domain;

import java.time.LocalDate;

/**
 *
 * @author JEYCOB
 */
public class Imagen {
    private int id;
    private String src; // para validar que no sea igual, y evitar que se repitan las imágenes
    private LocalDate fecha;

    public Imagen(){}
    
    public Imagen(int id, String src, LocalDate fecha) {
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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
