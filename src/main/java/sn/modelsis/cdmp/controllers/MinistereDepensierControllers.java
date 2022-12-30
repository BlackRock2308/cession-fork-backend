package sn.modelsis.cdmp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.MinistereDepensier;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.entitiesDtos.MinistereDepensierDto;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.services.MinistereDepensierService;
import sn.modelsis.cdmp.util.DtoConverter;

/**
 * @author SNDIAGNEF
 * REST Controller to handle Ministere
 */
@RestController
@RequestMapping("/api/ministeres")
@RequiredArgsConstructor
public class MinistereDepensierControllers {

  private final Logger log = LoggerFactory.getLogger(MinistereDepensierControllers.class);


  private final MinistereDepensierService ministereService;

  @GetMapping
  public ResponseEntity<List<MinistereDepensierDto>> getAllMinistereDepensiers(HttpServletRequest request) {
    List<MinistereDepensier> ministeres = ministereService.findAll();
    log.info("All MinistereDepensiers .");
    return ResponseEntity.status(HttpStatus.OK)
        .body(ministeres.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

  }
  
  @GetMapping("/{code}")
  public ResponseEntity<MinistereDepensierDto> getMinisterByCode(@PathVariable String code) {
      log.debug("REST request to get MinistereDepensier : {}", code);
      MinistereDepensier md = ministereService.findByCode(code);
      return  ResponseEntity.ok().body(DtoConverter.convertToDto(md));
  }

    @PutMapping()
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict duplicated code")})
    public ResponseEntity<MinistereDepensierDto> updateMinistereDepensier(@RequestBody MinistereDepensierDto ministereDepensierDto,
                                                            HttpServletRequest request) {
        MinistereDepensier ministereDepensier = DtoConverter.convertToEntity(ministereDepensierDto);
        ministereDepensier = ministereService.save(ministereDepensier);
        if(ministereDepensier == null){
            log.info("MinistereDepensierControllers:ce code existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(DtoConverter.convertToDto(ministereDepensier));
        }
        log.info("MinistereDepensierControllers:ministeredepensier modifié");
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(ministereDepensier));
    }

    @PostMapping()
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict duplicated code")})
    public ResponseEntity<MinistereDepensierDto> addMinistereDepensier(@RequestBody MinistereDepensierDto ministereDepensierDto,
                                                               HttpServletRequest request) {
        MinistereDepensier ministereDepensier = DtoConverter.convertToEntity(ministereDepensierDto);
        ministereDepensier = ministereService.save(ministereDepensier);
        if(ministereDepensier == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(DtoConverter.convertToDto(ministereDepensier));
        }
        log.info("MinistereDepensierControllers:ministeredepensier créé");
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(ministereDepensier));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MinistereDepensierDto> deleteMinisteredepensier(@PathVariable Long id,
                                                    HttpServletRequest request) {
        ministereService.delete(id);
        log.warn("MinistereDepensierControllers: ministeredepensier supprimé. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

