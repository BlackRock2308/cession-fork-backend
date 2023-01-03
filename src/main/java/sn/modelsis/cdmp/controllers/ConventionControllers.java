package sn.modelsis.cdmp.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.entitiesDtos.TextConventionDto;
import sn.modelsis.cdmp.mappers.ConventionMapper;
import sn.modelsis.cdmp.services.ConventionService;
import sn.modelsis.cdmp.services.ParametrageDecoteService;
import sn.modelsis.cdmp.services.UtilisateurService;
import sn.modelsis.cdmp.util.DtoConverter;

/**
 * @author SNDIAGNEF
 * REST Controller to handle Convention
 */
@RestController
@RequestMapping("/api/conventions")
@Slf4j
@RequiredArgsConstructor
public class ConventionControllers {

    final private ConventionService conventionService;

    private final ParametrageDecoteService decoteService;

    private final ConventionMapper conventionMapper;

    private final UtilisateurService utilisateurService;


  @PostMapping()
  public ResponseEntity<ConventionDto> addConvention(@RequestBody ConventionDto conventionDto,
      HttpServletRequest request) {

    Convention result = conventionService.save(conventionDto);
    log.info("ConventionControllers:addConvention saved in database with Id:{} ", result.getIdConvention());
    return ResponseEntity.status(HttpStatus.CREATED).body(conventionMapper.asDTO(result));
  }

    @PutMapping("transmission/{id}")
    public ResponseEntity<ConventionDto> transmettreConvention(@RequestBody ConventionDto conventionDto,
                                                          @PathVariable("id") Long id ,
        HttpServletRequest request) {
      log.info("ConventionControllers:transmettreConvention request started ");

      Convention convention = conventionMapper.asEntity(conventionDto);
      Convention result = conventionService.transmettreConvention(id,convention);
      log.info("ConventionControllers:transmettreConvention updated in database with Id:{} ", result.getIdConvention());
      return ResponseEntity.status(HttpStatus.OK).body(conventionMapper.asDTO(result));
    }

  @PutMapping("correction")
  public ResponseEntity<ConventionDto> corrigerConvention(@RequestBody ConventionDto conventionDto,
                                                     HttpServletRequest request) {
    Convention savedConvention = conventionService.corriger(conventionDto);
    log.info("ConventionControllers:convention corrigée");
     return ResponseEntity.status(HttpStatus.CREATED).body(conventionMapper.asDTO(savedConvention));
  }


    @GetMapping
    public ResponseEntity<List<ConventionDto>> getAllConventions(
        HttpServletRequest request) {
      log.info("ConventionControllers:getAllConventions request started ");

      List<Convention> conventions =
          conventionService.findAll();
       return ResponseEntity.status(HttpStatus.OK).body(conventionMapper.asDTOList(conventions));
      
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<ConventionDto> getConvention(
        @PathVariable Long id,
        HttpServletRequest request) {
      log.info("ConventionControllers:getConvention request started ");

      Convention convention = conventionService.getConvention(id).orElse(null);
      log.info("ConventionControllers:getConvention request params : {}", id);
      return ResponseEntity.status(HttpStatus.OK).body(conventionMapper.asDTO(convention));
    }

  @GetMapping(value = "/textConvention/{id}")
  public ResponseEntity<TextConventionDto> getTextConvention(
          @PathVariable Long id,
          HttpServletRequest request) {
    log.info("ConventionControllers:getConvention request started ");

    Convention convention = conventionService.getConvention(id).orElse(null);
    log.info("ConventionControllers:getConvention request params : {}", id);
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(convention.getTextConvention()));
  }

  @PostMapping("/{idConvention}/signer-convention-dg/{idUtilisateur}")
  public  ResponseEntity<Boolean> signerConventionDG(@RequestBody String codePin,@PathVariable Long idUtilisateur,@PathVariable Long idConvention)  {
   if (utilisateurService.signerConvention(idUtilisateur,codePin))
      conventionService.conventionSignerParDG(idConvention,idUtilisateur);
    return ResponseEntity.ok().body(utilisateurService.signerConvention(idUtilisateur,codePin));

  }

  /**
   * {@code POST  /signer-convention-pme} : signer convention par le pme
   *
   * @param  codePin of the user .
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */

  @PostMapping("/{idConvention}/signer-convention-pme/{idUtilisateur}")
  public  ResponseEntity<Boolean> signerConventionPME(@RequestBody String codePin,@PathVariable Long idUtilisateur,@PathVariable Long idConvention)  {
    if (utilisateurService.signerConvention(idUtilisateur,codePin))
      conventionService.conventionSignerParPME(idConvention,idUtilisateur);

    return ResponseEntity.ok().body(utilisateurService.signerConvention(idUtilisateur,codePin));

  }

    @PostMapping("/{id}/upload")
    @Operation(summary = "Upload file", description = "Upload a new file for Convention")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<ConventionDto> addDocument(@PathVariable Long id,
        @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

      Optional<Convention> convention = null;
      try {
          convention = conventionService.upload(id, file, type);
      } catch (IOException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if (convention.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
      log.info("Document added. Id:{} ", convention.get().getIdConvention());
      return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(convention.get()));
    }

  @GetMapping(value = "/{valeurCreance}/decote")
  public ResponseEntity<ParametrageDecote> getDecoteInterval(
          @PathVariable("valeurCreance") Double valeurCreance,
          HttpServletRequest request) {
    ParametrageDecote exactParametrageDecote = decoteService.findIntervalDecote(valeurCreance).orElse(null);

    return ResponseEntity.status(HttpStatus.OK).body(exactParametrageDecote);
  }

  @PutMapping(value ="/valeurCreance/{idConvention}")
  public ResponseEntity<ConventionDto> updateValeurDecote(@RequestBody double decote, @PathVariable Long idConvention) {
    log.info("ConventionControllers:updateValeurDecote request started ");
   Convention updatedConvention = conventionService.updateValeurDecote(idConvention, decote);
    log.info("ConventionControllers:updateValeurDecote request params  {}", updatedConvention.getIdConvention());

    return ResponseEntity.status(HttpStatus.OK).body(conventionMapper.asDTO(updatedConvention));
  }

}

