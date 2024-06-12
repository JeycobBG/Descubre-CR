
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Usuario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author JEYCOB
 */

public interface IUsuariosServices {
    boolean guardar(Usuario user);
    
    boolean eliminar(int id);
    
    Page<Usuario> listar(Pageable pageable);
    
    boolean login(String nombre, String contraseña);
    
    List<Usuario> getServicios();
    
    Usuario obtenerPorId(int id);
    
    String encriptar(String passwordSinEncriptar);
    
    Usuario buscar(String cedula);
    
    Usuario buscarUsuario(String nombreUsuario);
    
}
