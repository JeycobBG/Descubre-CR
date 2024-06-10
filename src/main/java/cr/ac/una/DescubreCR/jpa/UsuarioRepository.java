package cr.ac.una.DescubreCR.jpa;


import cr.ac.una.DescubreCR.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findByCedula(String cedula);
    Optional<Usuario> findByNombreUsuarioAndContrasena(String nombre, String contrasena);
}

