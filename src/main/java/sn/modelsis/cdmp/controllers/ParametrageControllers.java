package sn.modelsis.cdmp.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
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

import sn.modelsis.cdmp.entities.Parametrage;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDto;
import sn.modelsis.cdmp.services.ParametrageService;
import sn.modelsis.cdmp.util.DtoConverter;

/**
 * @author SNDIAGNEF
 * REST Controller to handle Parametrage
 */
@RestController
@RequestMapping("/api/parametrages")
public class ParametrageControllers {

  private final Logger log = LoggerFactory.getLogger(ParametrageControllers.class);

  @Autowired
  private ParametrageService parametrageService;

  @PostMapping()
  public ResponseEntity<ParametrageDto> addParametrage(@RequestBody ParametrageDto parametrageDto,
      HttpServletRequest request) {
    Parametrage parametrage = DtoConverter.convertToEntity(parametrageDto);
    Parametrage result = parametrageService.save(parametrage);
    log.info("Parametrage create. Id:{} ", result.getIdParam());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

  @PutMapping()
  public ResponseEntity<ParametrageDto> updateParametrage(
      @RequestBody ParametrageDto parametrageDto, HttpServletRequest request) {
    Parametrage parametrage = DtoConverter.convertToEntity(parametrageDto);
    Parametrage result = parametrageService.save(parametrage);
    log.info("Parametrage updated. Id:{}", result.getIdParam());
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
  }

  @GetMapping
  public ResponseEntity<List<ParametrageDto>> getAllParametrages(HttpServletRequest request) {
    List<Parametrage> parametrages = parametrageService.findAll();
    log.info("All parametrages .");
    return ResponseEntity.status(HttpStatus.OK)
        .body(parametrages.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ParametrageDto> getParametrage(@PathVariable Long id,
      HttpServletRequest request) {
    Parametrage parametrage = parametrageService.getParametrage(id).orElse(null);
    log.info("Parametrage . Id:{}", id);
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(parametrage));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<ParametrageDto> deleteParametrage(@PathVariable Long id,
      HttpServletRequest request) {
    parametrageService.delete(id);
    log.warn("Parametrage deleted. Id:{}", id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}

