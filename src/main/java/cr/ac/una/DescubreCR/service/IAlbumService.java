/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.Album;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author JEYCOB
 */
public interface IAlbumService {
    public void guardarEnBD(Album album);
    public List<Album> listarAlbums();
    public Page<Album> listarAlbumsParaPaginacion(Pageable pageable);
    public Optional getAlbumById(String id);
    public void eliminarAlbumById(String id);
}
