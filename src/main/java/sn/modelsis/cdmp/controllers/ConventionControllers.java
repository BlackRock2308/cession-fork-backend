package sn.modelsis.cdmp.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.mappers.ConventionMapper;
import sn.modelsis.cdmp.services.*;
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
    final private StatutService statutService;
    final private DemandeCessionService demandeCessionService ;

    private final ParametrageDecoteService decoteService;

    private final ConventionMapper conventionMapper;

    private final UtilisateurService utilisateurService;

    private final BonEngagementService bonEngagementService;


  @PostMapping()
  public ResponseEntity<ConventionDto> addConvention(@RequestBody ConventionDto conventionDto,
      HttpServletRequest request) {
    log.info("ConventionControllers:addConvention request started .......");
    Convention convention= new Convention();

   // Utilisateur utilisateur = utilisateurService.findById(conventionDto.getUtilisatuerId());
    DemandeCession demandeCession =
            demandeCessionService.findByIdDemande(conventionDto.getIdDemande()).orElse(null);

    assert demandeCession != null;
    Optional<BonEngagement> bonEngagement =
            bonEngagementService.getBonEngagement(demandeCession.getBonEngagement().getIdBonEngagement());
    double valeurCreance =
            bonEngagement.get().getMontantCreance();
    BigDecimal bigDecimal = new BigDecimal(valeurCreance);

    log.info("Valeur du montant de la creance : {}",bigDecimal);

    //this method allows to find the right decote interval depending on montantCreance
    ParametrageDecote exactParametrageDecote = decoteService.findIntervalDecote(valeurCreance).orElse(null);

    log.info("Correct Decote param: {}",exactParametrageDecote);

    convention.setDateConvention(LocalDateTime.now());
    convention.setDemandeCession(demandeCession);
    convention.setDecote(exactParametrageDecote);  //decote

    assert exactParametrageDecote != null;
    convention.setValeurDecote(exactParametrageDecote.getDecoteValue());  //decote

    if(convention.getValeurDecoteByDG() == 0){
      convention.setValeurDecoteByDG(exactParametrageDecote.getDecoteValue()); //valeurDecoteDG take the value of the params decote
    }

    log.info("Valeur Decote DG: {}",convention.getValeurDecoteByDG());

    Convention result = conventionService.save(convention);
    Statut statut = statutService.findByCode("CONVENTION_GENEREE");
    demandeCession.setStatut(statut);
    demandeCession.setConventions(result.getDemandeCession().getConventions());

    demandeCessionService.save(demandeCession);
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

  @PutMapping("correction/{id}")
  public ResponseEntity<ConventionDto> corrigerConvention(@RequestBody ConventionDto conventionDto,
                                                          @PathVariable("id") Long id ,
                                                     HttpServletRequest request) {
    log.info("ConventionControllers:corrigerConvention request started .......");

    Convention convention= conventionMapper.asEntity(conventionDto);

    conventionService.delete(id);

    DemandeCession demandeCession =
            demandeCessionService.findByIdDemande(conventionDto.getIdDemande()).orElse(null);

    assert demandeCession != null;
    Optional<BonEngagement> bonEngagement =
            bonEngagementService.getBonEngagement(demandeCession.getBonEngagement().getIdBonEngagement());
    if (bonEngagement.isPresent()){
      double valeurCreance = bonEngagement
              .get()
              .getMontantCreance();
      BigDecimal bigDecimal = new BigDecimal(valeurCreance);

      log.info("Valeur du montant de la creance : {}",bigDecimal);

      //this method allows to find the right decote interval depending on montantCreance
      ParametrageDecote exactParametrageDecote = decoteService
              .findIntervalDecote(valeurCreance)
              .orElse(null);

      log.info("Correct Decote param: {}",exactParametrageDecote);

      convention.setDemandeCession(demandeCession);
      convention.setDecote(exactParametrageDecote);  //decote

      assert exactParametrageDecote != null;
      convention.setValeurDecote(exactParametrageDecote.getDecoteValue());  //decote

    }

    Convention savedConvention = conventionService.save(convention);
    Statut statut = statutService.findByCode("CONVENTION_GENEREE");
    demandeCession.setStatut(statut);
    demandeCession.setConventions(convention.getDemandeCession().getConventions());

    demandeCessionService.save(demandeCession);
    log.info("ConventionControllers:corrigerConvention saved in database with Id:{} ", savedConvention.getIdConvention());
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ConventionDto> deleteConvention(
        @PathVariable Long id,
        HttpServletRequest request) {
      log.info("ConventionControllers:deleteConvention request started ");
      conventionService.delete(id);
      log.info("ConventionControllers:deleteConvention request params : {} ", id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PostMapping("/{id}/upload")
    @Operation(summary = "Upload file", description = "Upload a new file for Convention")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<ConventionDto> addDocument(@PathVariable Long id,
        @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

      Optional<Convention> convention = null;
      try {
          convention = conventionService.upload(id, file, TypeDocument.valueOf(type));
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

  @PatchMapping(value ="/{idConvention}/{decote}")
  public ResponseEntity<ConventionDto> updateValeurDecote(@PathVariable("idConvention") Long idConvention,
                                                          @PathVariable("decote") double decote) {
    log.info("ConventionControllers:updateValeurDecote request started ");
   Convention updatedConvention = conventionService.updateValeurDecote(idConvention, decote);
    log.info("ConventionControllers:updateValeurDecote request params  {}", updatedConvention.getIdConvention());

    return ResponseEntity.status(HttpStatus.OK).body(conventionMapper.asDTO(updatedConvention));
  }

}

