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
import sn.modelsis.cdmp.Util.DtoConverter;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.services.ConventionService;

/**
 * REST Controller to handle Convention
 */
@RestController
@RequestMapping("/api/conventions")
public class ConventionControllers {

    private final Logger log = LoggerFactory.getLogger(ConventionControllers.class);

    @Autowired
    private ConventionService conventionService;
    
  @PostMapping()
  public ResponseEntity<ConventionDto> addConvention(@RequestBody ConventionDto conventionDto,
      HttpServletRequest request) {
    Convention convention = DtoConverter.convertToEntity(conventionDto);
    Convention result = conventionService.save(convention);
    log.info("Convention create. Id:{} ", result.getIdConvention());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

    @PutMapping()
    public ResponseEntity<ConventionDto> updateConvention(@RequestBody ConventionDto conventionDto,
        HttpServletRequest request) {
      Convention convention = DtoConverter.convertToEntity(conventionDto);
      Convention result = conventionService.save(convention);
      log.info("Convention updated. Id:{}", result.getIdConvention());
      return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
    }
   
    @GetMapping
    public ResponseEntity<List<ConventionDto>> getAllConventions(
        HttpServletRequest request) {
     List<Convention> conventions =
          conventionService.findAll();
      log.info("All conventions .");
       return ResponseEntity.status(HttpStatus.OK).body(conventions.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
      
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<ConventionDto> getConvention(
        @PathVariable Long id,
        HttpServletRequest request) {
      Convention convention = conventionService.getConvention(id).orElse(null);
      log.info("Convention . Id:{}", id);
      return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(convention));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ConventionDto> deleteConvention(
        @PathVariable Long id,
        HttpServletRequest request) {
      conventionService.delete(id);
      log.warn("Convention deleted. Id:{}", id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

