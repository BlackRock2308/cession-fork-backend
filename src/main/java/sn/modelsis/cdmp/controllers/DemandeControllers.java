package sn.modelsis.cdmp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.Util.DtoConverter;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.entitiesDtos.StatutDto;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.StatutService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

  @Autowired
  private StatutService statutService;

  @PostMapping()
  public ResponseEntity<DemandeDto> addDemande(@RequestBody DemandeDto demandeDto,
                                               HttpServletRequest request) {
    Demande demande = DtoConverter.convertToEntity(demandeDto);
    Demande result = demandeService.save(demande);
    log.info("demande create. Id:{} ", result.getIdDemande());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

  @PatchMapping
  public ResponseEntity<DemandeDto> rejetAdhesion(@RequestBody DemandeDto demandeDto, StatutDto statutDto, HttpServletRequest request) {
    Statut statut = DtoConverter.convertToEntity(statutDto);
    Demande demande = DtoConverter.convertToEntity(demandeDto);
    demande.setStatut(statut);
    statut.setLibelle("Rejetée");
    statut.setCode("1");
    demande.setStatut(statut);
    statutService.save(statut);
    Demande result=demandeService.save(demande);
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));

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
    log.info("All Demande .");
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
}
