package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Parametrage;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;

import java.util.Optional;

/**
 * @author SNDIAGNEF
 *
 */
public interface ParametrageRepository extends JpaRepository<Parametrage, Long> {


}
