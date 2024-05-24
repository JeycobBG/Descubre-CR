/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.DescubreCR.jpa;

import cr.ac.una.DescubreCR.domain.ComentarioLugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author josue
 */
public interface RepositoryComentarioLugar extends JpaRepository<ComentarioLugar, String>, JpaSpecificationExecutor<ComentarioLugar>{
    
}
