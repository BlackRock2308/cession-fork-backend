package sn.modelsis.cdmp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author SNDIAGNEF
 * REST Controller to handle Demande
 */
@RestController
@RequestMapping("/api/demandes")
public class DemandeControllers {

  private final Logger log = LoggerFactory.getLogger(DemandeControllers.class);

  @Autowired
  private DemandeService demandeService;


  @PostMapping()
  public ResponseEntity<DemandeDto> addDemande(@RequestBody DemandeDto demandeDto,
                                               HttpServletRequest request) {
    Demande demande = DtoConverter.convertToEntity(demandeDto);
    Demande result = demandeService.save(demande);
    log.info("demande create. Id:{} ", result.getIdDemande());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

  @PatchMapping(value ="/{id}/reject")
  public ResponseEntity<DemandeDto> rejetAdhesion(@RequestBody DemandeDto demandeDto, HttpServletRequest request) {
    DemandeDto demandeDto1=demandeService.rejetAdhesion(demandeDto);
    return ResponseEntity.status(HttpStatus.OK).body(demandeDto1);
  }

  @PatchMapping(value = "/{id}/accept")
  public ResponseEntity<DemandeDto> validerAdhesion(@RequestBody DemandeDto demandeDto, HttpServletRequest request) {
    DemandeDto demandeDto1=demandeService.validerAdhesion(demandeDto);
    return ResponseEntity.status(HttpStatus.OK).body(demandeDto1);
  }




  @PutMapping(value = "/{id}")
  public ResponseEntity<DemandeDto> updateDemande(@RequestBody DemandeDto demandeDto,
      HttpServletRequest request) {
    Demande demande = DtoConverter.convertToEntity(demandeDto);
    Demande result = demandeService.save(demande);
    log.info("Demande updated. Id:{}", result.getIdDemande());
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
  }

  @GetMapping
  public ResponseEntity<List<DemandeDto>> getAllDemande(HttpServletRequest request) {
    List<Demande> demandeList = demandeService.findAll();
    log.info("All Requests .");
    return ResponseEntity.status(HttpStatus.OK)
        .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<DemandeDto> getDemande(@PathVariable Long id, HttpServletRequest request) {
    Demande demande = demandeService.getDemande(id).orElse(null);
    log.info("Demande . Id:{}", id);
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(demande));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<DemandeDto> deleteDemande(@PathVariable Long id,
      HttpServletRequest request) {
    demandeService.delete(id);
    log.warn("Demande deleted. Id:{}", id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
  
  @PostMapping("/{id}/upload")
  @Operation(summary = "Upload file", description = "Upload a new file for Demande")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<DemandeDto> addDocument(@PathVariable Long id,
      @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

    Optional<Demande> be = null;
    try {
      be = demandeService.upload(id, file, TypeDocument.valueOf(type));
    } catch (IOException e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    if (be.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    log.info("Document added. Id:{} ", be.get().getIdDemande());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(be.get()));
  }
}
