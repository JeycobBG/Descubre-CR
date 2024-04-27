/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.domain;

import java.util.Date;

/**
 *
 * @author JEYCOB
 */
public class Usuario extends Persona{
    
    String nombreUsuario;
    String contraseña;
    String tipoUsuario;
    String correo;
    String canton;
    String provincia;
    Date fechaRegistro;


    public Usuario(String nombre, String apellido, String cedula, String idioma, String nacionalidad, Date fechaNacimiento, String telefono) {
        super(nombre, apellido, cedula, idioma, nacionalidad, fechaNacimiento, telefono);
    }
    
    

    public Usuario(String nombre, String apellido, String cedula, String idioma, String nacionalidad, Date fechaNacimiento, String telefono, String nombreUsuario, String contraseña, String tipoUsuario, String correo, String canton, String provincia, Date fechaRegistro) {
        super(nombre, apellido, cedula, idioma, nacionalidad, fechaNacimiento, telefono);
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.correo = correo;
        this.canton = canton;
        this.provincia = provincia;
        this.fechaRegistro = fechaRegistro;
    }
    
        public Usuario(String nombre, String apellido, String cedula, String idioma, String nacionalidad, Date fechaNacimiento, String telefono, String nombreUsuario, String contraseña, String tipoUsuario, String correo, String canton, String provincia) {
        super(nombre, apellido, cedula, idioma, nacionalidad, fechaNacimiento, telefono);
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.correo = correo;
        this.canton = canton;
        this.provincia = provincia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombreUsuario=" + nombreUsuario + ", contrase\u00f1a=" + contraseña + ", tipoUsuario=" + tipoUsuario + ", correo=" + correo + ", canton=" + canton + ", provincia=" + provincia + ", fechaRegistro=" + fechaRegistro + '}';
    }
}
