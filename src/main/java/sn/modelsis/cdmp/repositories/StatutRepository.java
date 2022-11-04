package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.Statuts;

public interface StatutRepository extends JpaRepository<Statut, Long> {
  // Optional<Statut> findStatutById(Long id);

    Statut findByLibelle(String statutLibelle);

    Statut findByCode(String code);
}
