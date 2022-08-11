package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
}
