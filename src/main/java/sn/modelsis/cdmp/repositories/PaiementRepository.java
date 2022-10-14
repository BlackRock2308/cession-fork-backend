package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
