package cr.ac.una.DescubreCR.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity (name = "tb_usuario")
@PrimaryKeyJoinColumn(name = "per_id")
public class Usuario extends Persona {

    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    @Column(name = "contrasena")
    private String contrasena;
    
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    
    @Column(name = "correo")
    private String correo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String contrasena, String tipoUsuario, String correo, Date fechaRegistro) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario(String nombreUsuario, String contrasena, String tipoUsuario, String correo, Date fechaRegistro, String cedula, String nombre, String apellido, String idioma, String nacionalidad, Date fechaNacimiento, String telefono) {
        super(cedula, nombre, apellido, idioma, nacionalidad, fechaNacimiento, telefono);
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters and setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
