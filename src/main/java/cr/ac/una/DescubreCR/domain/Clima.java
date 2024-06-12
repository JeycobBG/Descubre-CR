package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;


@Entity
@Table(name = "tb_clima")
public class Clima {
    @Id
    private String codigo;

    private String temperatura;

    private String tipoClima;

    @Temporal(TemporalType.DATE)
    private LocalDate fecha;

    private String unidadC;

    private int indiceUV;

    private String porcentajeHumedad;

    public Clima() {
    }

    public Clima(String codigo, String temperatura, String tipoClima, LocalDate fecha, String unidadC, int indiceUV, String porcentajeHumedad) {
        this.codigo = codigo;
        this.temperatura = temperatura;
        this.tipoClima = tipoClima;
        this.fecha = fecha;
        this.unidadC = unidadC;
        this.indiceUV = indiceUV;
        this.porcentajeHumedad = porcentajeHumedad;
    }

    // Getters y setters...
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getTipoClima() {
        return tipoClima;
    }

    public void setTipoClima(String tipoClima) {
        this.tipoClima = tipoClima;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUnidadC() {
        return unidadC;
    }

    public void setUnidadC(String unidadC) {
        this.unidadC = unidadC;
    }

    public int getIndiceUV() {
        return indiceUV;
    }

    public void setIndiceUV(int indiceUV) {
        this.indiceUV = indiceUV;
    }

    public String getPorcentajeHumedad() {
        return porcentajeHumedad;
    }

    public void setPorcentajeHumedad(String porcentajeHumedad) {
        this.porcentajeHumedad = porcentajeHumedad;
    }
}
