/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.exceptions.CustomException;
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
@Slf4j
public class ObservationServiceImpl implements ObservationService{
  private final ObservationRepository observationRepository;
  private final ObservationMapper observationMapper;


  @Override
  public ObservationDto saveNewObservation(ObservationDto observation) {
    Observation newObservation;
    try{
      log.info("ObservationService.saveNewObservation request started...");
      //LocalDateTime dateNewObservation = LocalDateTime.now();
      newObservation = observationMapper.mapToDto(observation);
      log.debug("ObservationService.saveNewObservation request params : {}", newObservation);
      observationRepository.save(newObservation);
    } catch (Exception ex){
      log.info("Exception occured while adding new Observation to database.Exception message : {}", ex.getMessage());
      throw new CustomException("Exception occured while adding an Observation");
    }
    return DtoConverter.convertToDto(newObservation);
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
