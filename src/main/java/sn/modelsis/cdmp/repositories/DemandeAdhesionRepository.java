package sn.modelsis.cdmp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.modelsis.cdmp.entities.DemandeAdhesion;

public interface DemandeAdhesionRepository extends JpaRepository<DemandeAdhesion,Long> {
    
    List<DemandeAdhesion> findAllByPmeIdPME(Long id);
}
