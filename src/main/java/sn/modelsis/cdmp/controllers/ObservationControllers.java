package sn.modelsis.cdmp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.ObservationService;
import sn.modelsis.cdmp.util.DtoConverter;

/**
 *  @author SNDIAGNEF
 * REST Controller to handle Observation
 */
@RestController
@RequestMapping("/api/observations")
@RequiredArgsConstructor
@Slf4j
public class ObservationControllers {

    private final ObservationService observationService;

    private final DemandeService demandeService;

    @PostMapping()
    public ResponseEntity<ObservationDto> addObservation(
        @RequestBody ObservationDto observationDto,
        HttpServletRequest request) {
      //Observation observation = DtoConverter.convertToEntity(observationDto);
      ObservationDto result = observationService.saveNewObservation(observationDto);
      log.info("Observation create. Id:{} ", result.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @GetMapping("/observation-by-demandeid-and-status/{id}")
    public ResponseEntity<ObservationDto> getObservationByDemandeAndStatus(
            @PathVariable Long id,
           @Param("status") String status
    ) {
        Observation serachedObservation = new Observation();
        List<Observation> observationList;
        observationList = observationService.findObservationsByDemandeCession(id);
        if (observationList!=null){
            for (Observation observation :observationList ) {
                if(observation.getStatut().getLibelle().equals(status))
                    serachedObservation = observation ;
            }
        }else
            throw new CustomException("We can not find the Observation");

       return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(serachedObservation));
      
    }

  @GetMapping("/last-observation/{id}")
  public ResponseEntity<ObservationDto> getLastObservation(
      @PathVariable Long id) {
    List<Observation> observationList;
    observationList = observationService.findObservationsByDemandeCession(id);
    if (observationList == null)
      throw new CustomException("We can not find the Observation");
      int listeSize = observationList.size() -1;
      return ResponseEntity.status(HttpStatus.OK)
          .body(DtoConverter.convertToDto(observationList.get(listeSize)));
}

    @GetMapping
    public ResponseEntity<List<ObservationDto>> getAllObservations(
        HttpServletRequest request) {
     List<Observation> observations =
          observationService.findAll();
      log.info("All observations .");
       return ResponseEntity.status(HttpStatus.OK).body(observations.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

    }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ObservationDto> getObservation(@PathVariable Long id,
      HttpServletRequest request) {
    Observation observation = observationService.getObservation(id).orElse(null);
    log.info("Observation . Id:{}", id);
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(observation));
  }

    @GetMapping(value = "/{id}/demande-cession")
    public ResponseEntity<ObservationDto> findObservationsByDemandeCession(@PathVariable Long id,
                                                         HttpServletRequest request) {
        log.info("ObservationService.findObservationsByDemandeCession request stated");
        List<Observation> observationList;
        observationList = observationService.findObservationsByDemandeCession(id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto((Observation) observationList));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ObservationDto> deleteObservation(
        @PathVariable Long id,
        HttpServletRequest request) {
      observationService.delete(id);
      log.warn("Observation deleted. Id:{}", id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

