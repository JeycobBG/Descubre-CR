/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Usuario;
import cr.ac.una.DescubreCR.jpa.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

@Service
public class UsuariosServices implements IUsuariosServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean guardar(Usuario user) {
        usuarioRepository.save(user);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public boolean login(String nombre, String contrasena) {
        String encryptedPassword = encriptar(contrasena);
        
        return usuarioRepository.findByNombreUsuarioAndContrasena(nombre, contrasena).isPresent();
    }

    @Override
    public List<Usuario> getServicios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public String encriptar(String passwordSinEncriptar) {
        return DigestUtils.md5Hex(passwordSinEncriptar);
    }

    @Override
    public Usuario buscar(String cedula) {
        return usuarioRepository.findByCedula(cedula).orElse(null);
    }
}
