package sn.modelsis.cdmp.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
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

    @PostMapping()
    public ResponseEntity<ObservationDto> addObservation(
        @RequestBody ObservationDto observationDto,
        HttpServletRequest request) {
      //Observation observation = DtoConverter.convertToEntity(observationDto);
      ObservationDto result = observationService.saveNewObservation(observationDto);
      log.info("Observation create. Id:{} ", result.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ObservationDto> deleteObservation(
        @PathVariable Long id,
        HttpServletRequest request) {
      observationService.delete(id);
      log.warn("Observation deleted. Id:{}", id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

