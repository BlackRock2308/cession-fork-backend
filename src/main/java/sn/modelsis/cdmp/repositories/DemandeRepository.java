package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Demande;

import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    //Optional<Demande> findDemandeById(Long id);
}
