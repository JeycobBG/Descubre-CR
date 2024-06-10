/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.data.AlbumData;
import cr.ac.una.DescubreCR.domain.Album;
import cr.ac.una.DescubreCR.jpa.AlbumRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author JEYCOB
 */
@Service
@Primary
public class AlbumService implements IAlbumService{
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Override
    public void guardarEnBD(Album album){
        albumRepo.save(album);
    }
    @Override
    public List<Album> listarAlbums(){
        return albumRepo.findAll();
    }
    @Override
    public Page<Album> listarAlbumsParaPaginacion(Pageable pageable){
        return albumRepo.findAll(pageable);
    }
    @Override
    public Optional<Album> getAlbumById(String id){
        return albumRepo.findById(Integer.valueOf(id));
    }
    @Override
    public void eliminarAlbumById(String id){
        albumRepo.deleteById(Integer.valueOf(id));
    }
}