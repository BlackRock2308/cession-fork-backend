package sn.modelsis.cdmp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.services.StatutService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pme")
@RequiredArgsConstructor
@Slf4j
public class PmeController {
  private final PmeService pmeService;

  private final StatutService statutService;

  @PostMapping()
  public ResponseEntity<PmeDto> savePme(@RequestBody PmeDto pmeDto,
                                              HttpServletRequest request) {

    log.info("PmeController:savePme request body : {}", pmeDto);
    Pme pme = DtoConverter.convertToEntity(pmeDto);

    Pme result = pmeService.savePme(pme);
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<PmeDto> updatePme(@RequestBody PmeDto pmeDto, @PathVariable("id") Long id ,
                                          HttpServletRequest request) {
    log.info("PmeController:updatePme Started with request params id={}", id);

    Pme pme = DtoConverter.convertToEntity(pmeDto);
    Pme result = pmeService.updatePme(id,pme);
    log.info("PmeController:updatePme updated with id = {} ", result.getIdPME());
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
  }

  @PatchMapping(value = "/{id}")
  public ResponseEntity<PmeDto> patchPme(@PathVariable Long id,@RequestBody PmeDto pmeDto,
                                         HttpServletRequest request) {

    Pme pme = DtoConverter.convertToEntity(pmeDto);
    pme.setIdPME(id);
    Pme result = pmeService.savePme(pme);
    log.info("Pme updated. Id:{}", result.getIdPME());
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
  }

  @GetMapping
  public ResponseEntity<List<PmeDto>> getAllPme(HttpServletRequest request) {
    List<Pme> pmeList = pmeService.findAllPme();
    log.info("PmeController:getAllPme fetching from database");
    return ResponseEntity.status(HttpStatus.OK)
        .body(pmeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<PmeDto> getPme(@PathVariable Long id, HttpServletRequest request) {
    log.info("PmeController:getPme request params : id = {}", id);
    Pme pme = pmeService.getPme(id).orElse(null);

    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(pme));
  }



  @DeleteMapping(value = "/{id}")
  public ResponseEntity<String> deletePme(@PathVariable Long id, HttpServletRequest request) {
    log.info("PmeController:deletePme with id = {}", id);
    pmeService.deletePme(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pme Deleted Successfullly...");
  }
  
  @PostMapping("/{id}/upload")
  @Operation(summary = "Upload file", description = "Upload a new file for Pme")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<PmeDto> addDocument(@PathVariable Long id,
      @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

    Optional<Pme> be = null;
    try {
      log.info("PmeController:addDocument uploading with request params id = {}", id);
      be = pmeService.upload(id, file, TypeDocument.valueOf(type));
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
}
