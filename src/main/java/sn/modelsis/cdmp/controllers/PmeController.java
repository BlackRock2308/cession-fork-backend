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
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.services.StatutService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pme")
public class PmeController {
  private final Logger log = LoggerFactory.getLogger(PmeController.class);
  @Autowired
  private PmeService pmeService;

  @Autowired
  private DemandeService demandeService;

  @Autowired
  private StatutService statutService;

  @PostMapping()
  public ResponseEntity<PmeDto> addPme(@RequestBody PmeDto pmeDto,DemandeDto demandeDto, HttpServletRequest request) {
    Pme pme = DtoConverter.convertToEntity(pmeDto);
   Demande demande=DtoConverter.convertToEntity(demandeDto);
    demande.setPme(pme);
    Statut statut=new Statut();
    statut.setLibelle("Soumise");
    statut.setCode("1");
    demande.setStatut(statut);
    Pme result = pmeService.save(pme);
    statutService.save(statut);
    demandeService.save(demande);
    log.info("Pme created. Id:{} ", result.getIdPME());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<PmeDto> updatePme(@RequestBody PmeDto pmeDto, HttpServletRequest request) {
    Pme pme = DtoConverter.convertToEntity(pmeDto);
    Pme result = pmeService.save(pme);
    log.info("Pme updated. Id:{}", result.getIdPME());
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
  }

  @GetMapping
  public ResponseEntity<List<PmeDto>> getAllPme(HttpServletRequest request) {
    List<Pme> pmeList = pmeService.findAll();
    log.info("All Pme .");
    return ResponseEntity.status(HttpStatus.OK)
        .body(pmeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<PmeDto> getPme(@PathVariable Long id, HttpServletRequest request) {
    Pme pme = pmeService.getPme(id).orElse(null);
    log.info("Pme . Id:{}", id);
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(pme));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<PmeDto> deletePme(@PathVariable Long id, HttpServletRequest request) {
    pmeService.delete(id);
    log.warn("Pme deleted. Id:{}", id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
