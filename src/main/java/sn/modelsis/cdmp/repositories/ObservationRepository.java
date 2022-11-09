package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Observation;

import java.util.List;

/**
 * @author SNDIAGNEF
 *
 */
public interface ObservationRepository extends JpaRepository<Observation, Long> {


    List<Observation> findAllObservationByIdDemande(Long IdDemande);
}
