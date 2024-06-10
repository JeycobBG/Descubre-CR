/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author JEYCOB
 */
public interface AlbumRepository extends JpaRepository<Album, Integer>{
    
}
