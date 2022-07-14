package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Statut;

import java.util.Optional;

public interface StatutRepository extends JpaRepository<Statut, Long> {
    //Optional<Statut> findStatutById(Long id);
}
