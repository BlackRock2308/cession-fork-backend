package sn.modelsis.cdmp.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

@RestController
@RequestMapping("api/pme")
@RequiredArgsConstructor
@Slf4j
public class PmeController {
  private final PmeService pmeService;

  @PostMapping()
  public ResponseEntity<PmeDto> savePme(@RequestBody PmeDto pmeDto,
                                              HttpServletRequest request) {
    log.info("PmeController:savePme request started");

    Pme pme = DtoConverter.convertToEntity(pmeDto);

    log.info("PmeController:savePme request body : {}", pme);

    Pme result = pmeService.savePme(pme);
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

  @GetMapping
  public ResponseEntity<List<PmeDto>> getAllPme(HttpServletRequest request) {
    log.info("PmeController:getAllPme request started....");
    List<Pme> pmeList = pmeService.findAllPme();

    return ResponseEntity.status(HttpStatus.OK)
            .body(pmeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<PmeDto> getPme(@PathVariable Long id,
                                       HttpServletRequest request) {
    log.info("PmeController:getPme request started....");
    Pme pme = pmeService.getPme(id).orElse(null);
    log.info("PmeController:getPme request params : id = {}", id);
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(pme));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<PmeDto> updatePme(@RequestBody PmeDto pmeDto, @PathVariable("id") Long id ,
                                          HttpServletRequest request) {
    log.info("PmeController:updatePme request started");
    Pme pme = DtoConverter.convertToEntity(pmeDto);
    pme.setIdPME(id);
    log.info("PmeController:updatePme Started with request params id={}", id);

    Pme result = pmeService.updatePme(id,pme);
    log.info("PmeController:updatePme updated with id = {} ", result.getIdPME());
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
  }

//  @PatchMapping(value = "/{id}")
//  public ResponseEntity<PmeDto> patchPme(@PathVariable Long id,@RequestBody PmeDto pmeDto,
//                                         HttpServletRequest request) {
//    log.info("PmeController:patchPme request started");
//    Pme pme = DtoConverter.convertToEntity(pmeDto);
//    pme.setIdPME(id);
//    Pme result = pmeService.savePme(pme);
//    log.info("Pme updated. Id:{}", result.getIdPME());
//    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
//  }



  @GetMapping(value = "/byutilisateur/{id}")
  public ResponseEntity<PmeDto> getPmeByUtilisateur(@PathVariable Long id,
                                                    HttpServletRequest request) {
    log.info("PmeController:getPmeByUtilisateur request started... ");
    Pme pme = pmeService.getPmeByUtilisateur(id).orElse(null);
    log.info("PmeController:getPmeByUtilisateur request params id : {} ", id);
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(pme));
  }



  @DeleteMapping(value = "/{id}")
  public ResponseEntity<String> deletePme(@PathVariable Long id,
                                          HttpServletRequest request) {
    log.info("PmeController:getPmeByUtilisateur request started... ");
    pmeService.deletePme(id);
    log.info("PmeController:deletePme with id = {}", id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pme Deleted Successfullly...");
  }

  @PatchMapping(value ="/{idDemande}/complete-demande")
  public ResponseEntity<DemandeCessionDto> complementerDemandeCession(@PathVariable("idDemande") Long idDemande) {

    log.info("PmeController:complementerDemandeCession request started... ");
    DemandeCession acceptedDemande = pmeService.complementerDemandeCession(idDemande);
    log.info("PmeController:complementerDemandeCession request params  {}", acceptedDemande.getIdDemande());

    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(acceptedDemande));
  }
  
  @PostMapping("/{id}/upload")
  @Operation(summary = "Upload file", description = "Upload a new file for Pme")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<PmeDto> addDocument(@PathVariable Long id,
      @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

    Optional<Pme> be = null;
    try {
      log.info("PmeController:addDocument request started");
      be = pmeService.upload(id, file, TypeDocument.valueOf(type));
      log.info("PmeController:addDocument uploading with request params id = {}", id);
    } catch (IOException e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    if (be.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    log.info("PmeController:addDocument saved in database with idPme = {}", be.get().getIdPME());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(be.get()));
  }

  public ResponseEntity<PmeDto> getPmeByUser(Long idUser){

    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }
}
