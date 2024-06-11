/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author kvene
 */
@Entity
@Table(name="tb_rutas_recomendadas")
public class RutaRecomendada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String lugares;
    private String comentario;
    private double montoEntradas;
    private int puntuacion;
    private String dificultad;
    private String tipo;
    private String transporte;

    public RutaRecomendada() {
    }

    public RutaRecomendada(int id, String lugares, String comentario, double montoEntradas, int puntuacion, String dificultad, String tipo, String transporte) {
        this.id = id;
        this.lugares = lugares;
        this.comentario = comentario;
        this.montoEntradas = montoEntradas;
        this.puntuacion = puntuacion;
        this.dificultad = dificultad;
        this.tipo = tipo;
        this.transporte = transporte;
    }

    public RutaRecomendada(String lugares, String comentario, double montoEntradas, int puntuacion, String dificultad, String tipo, String transporte) {
        this.lugares = lugares;
        this.comentario = comentario;
        this.montoEntradas = montoEntradas;
        this.puntuacion = puntuacion;
        this.dificultad = dificultad;
        this.tipo = tipo;
        this.transporte = transporte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLugares() {
        return lugares;
    }

    public void setLugares(String lugares) {
        this.lugares = lugares;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getMontoEntradas() {
        return montoEntradas;
    }

    public void setMontoEntradas(double montoEntradas) {
        this.montoEntradas = montoEntradas;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    @Override
    public String toString() {
        return "RutaRecomendada{" + "id=" + id + ", lugares=" + lugares + ", comentario=" + comentario + ", montoEntradas=" + montoEntradas + ", puntuacion=" + puntuacion + ", dificultad=" + dificultad + ", tipo=" + tipo + ", transporte=" + transporte + '}';
    }
    
}
