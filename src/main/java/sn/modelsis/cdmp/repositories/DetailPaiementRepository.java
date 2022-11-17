package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.DetailPaiement;

public interface DetailPaiementRepository extends JpaRepository<DetailPaiement, Long> {
}
