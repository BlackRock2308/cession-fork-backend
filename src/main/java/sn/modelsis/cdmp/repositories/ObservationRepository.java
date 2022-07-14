package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Observation;

public interface ObservationRepository extends JpaRepository<Observation, Long> {
}
