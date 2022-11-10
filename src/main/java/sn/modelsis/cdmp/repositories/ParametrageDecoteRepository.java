package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;

import java.util.Optional;

@Repository
public interface ParametrageDecoteRepository extends JpaRepository<ParametrageDecote, Long> {

    Optional<ParametrageDecote> findParametrageByBorneInf(Long borneInf);

    Optional<ParametrageDecote> findParametrageByBorneSup(Long borneSup);
}
