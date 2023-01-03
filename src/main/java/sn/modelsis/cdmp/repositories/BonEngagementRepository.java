package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.BonEngagement;

import java.util.Optional;

public interface BonEngagementRepository extends JpaRepository<BonEngagement, Long> {
  // Optional<BonEngagement> findBonEngagementById(Long id);


    Optional<BonEngagement> findByNomMarche(String nomMarche);


    Optional<BonEngagement> findByMontantCreance(double montantCreance);
}
