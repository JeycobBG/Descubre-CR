package cr.ac.una.DescubreCR.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;
import org.springframework.data.annotation.Id;

@Entity
@Table(name="tb_usuarios")
public class Usuario extends Persona{
    @Id
    private String nombreUsuario;
    private String contraseña;
    private String tipoUsuario;
    private String correo;
    private Date fechaRegistro;

    public Usuario(String nombre, String apellido, String cedula, String idioma, String nacionalidad, Date fechaNacimiento, String telefono) {
        super(nombre, apellido, cedula, idioma, nacionalidad, fechaNacimiento, telefono);
    }

    public Usuario(String nombreUsuario, String contraseña, String tipoUsuario, String correo, Date fechaRegistro, String nombre, String apellido, String cedula, String idioma, String nacionalidad, Date fechaNacimiento, String telefono) {
        super(nombre, apellido, cedula, idioma, nacionalidad, fechaNacimiento, telefono);
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    
}
