/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.mappers.ObservationMapper;
import sn.modelsis.cdmp.repositories.ObservationRepository;
import sn.modelsis.cdmp.services.ObservationService;
import sn.modelsis.cdmp.util.DtoConverter;

/**
 * @author SNDIAGNEF
 *
 */
@Service
@RequiredArgsConstructor
public class ObservationServiceImpl implements ObservationService{
  private final ObservationRepository observationRepository;

  private final ObservationMapper observationMapper;


  @Override
  public ObservationDto save(Observation observation) {
     observationRepository.save(observation);
    return DtoConverter.convertToDto(observation);
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
