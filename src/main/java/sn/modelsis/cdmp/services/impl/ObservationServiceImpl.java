/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.repositories.ObservationRepository;
import sn.modelsis.cdmp.services.ObservationService;

/**
 * @author SNDIAGNEF
 *
 */
@Service
public class ObservationServiceImpl implements ObservationService{
  @Autowired
  private ObservationRepository observationRepository;
 
  @Override
  public Observation save(Observation observation) {
    return observationRepository.save(observation);
	}

  @Override
  public List<Observation> findAll(){
    return observationRepository.findAll();
  }

  @Override
  public Optional<Observation> getObservation(Long id) {
    return observationRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    observationRepository.deleteById(id);

  }

}
