package sn.modelsis.cdmp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.ObservationService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<ObservationDto>> getAllObservations(
        HttpServletRequest request) {
     List<Observation> observations =
          observationService.findAll();
      log.info("All observations .");
        List<Observation> observationList = observationService.findObservationsByDemandeCession(1L);
        var x =2;
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

