/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.domain;

import java.util.Date;

/**
 *
 * @author Jordi
 */
public class Clima {
    
    private String codigo;
    private String temperatura;
    private String tipoClima;
    private Date fecha;
    private String unidadC;
    private int indiceUV;
    private String porcentajeHumedad;

    public Clima() {
    }

    
    
    public Clima(String codigo,String temperatura, String tipoClima, Date fecha, String unidadC, int indiceUV, String porcentajeHumedad) {
        this.codigo = codigo;
        this.temperatura = temperatura;
        this.tipoClima = tipoClima;
        this.fecha = fecha;
        this.unidadC = unidadC;
        this.indiceUV = indiceUV;
        this.porcentajeHumedad = porcentajeHumedad;
    }

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
