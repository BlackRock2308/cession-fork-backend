package sn.modelsis.cdmp.controllers;

import java.io.IOException;
import java.math.BigDecimal;
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
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.mappers.ConventionMapper;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.services.ConventionService;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.ParametrageDecoteService;
import sn.modelsis.cdmp.services.StatutService;
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

    private final BonEngagementService bonEngagementService;


  @PostMapping()
  public ResponseEntity<ConventionDto> addConvention(@RequestBody ConventionDto conventionDto,
      HttpServletRequest request) {
    log.info("ConventionControllers:addConvention request started .......");
    Convention convention= new Convention();

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
    ParametrageDecote exactParametrageDecote = decoteService.findIntervalDecote(bigDecimal).orElse(null);

    log.info("Correct Decote param: {}",exactParametrageDecote);

    convention.setDemandeCession(demandeCession);
    convention.setDecote(exactParametrageDecote);  //decote

    assert exactParametrageDecote != null;
    convention.setValeurDecote(exactParametrageDecote.getDecoteValue());  //decote

    Convention result = conventionService.save(convention);
    Statut statut = statutService.findByCode("CONVENTION_GENEREE");
    demandeCession.setStatut(statut);
    demandeCession.setConventions(result.getDemandeCession().getConventions());

    demandeCessionService.save(demandeCession);
    log.info("ConventionControllers:addConvention saved in database with Id:{} ", result.getIdConvention());
    return ResponseEntity.status(HttpStatus.CREATED).body(conventionMapper.asDTO(result));
  }

    @PutMapping()
    public ResponseEntity<ConventionDto> updateConvention(@RequestBody ConventionDto conventionDto,
        HttpServletRequest request) {
      log.info("ConventionControllers:updateConvention request started ");

      Convention convention = DtoConverter.convertToEntity(conventionDto);
      Convention result = conventionService.save(convention);
      log.info("ConventionControllers:updateConvention updated in database with Id:{} ", result.getIdConvention());
      return ResponseEntity.status(HttpStatus.OK).body(conventionMapper.asDTO(result));
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
    
    @PostMapping("/{id}/upload")
    @Operation(summary = "Upload file", description = "Upload a new file for Convention")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<ConventionDto> addDocument(@PathVariable Long id,
        @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

      Optional<Convention> be = null;
      try {
        be = conventionService.upload(id, file, TypeDocument.valueOf(type));
      } catch (IOException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if (be.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
      log.info("Document added. Id:{} ", be.get().getIdConvention());
      return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(be.get()));
    }

//  @GetMapping(value = "/{valeurCreance}/decote")
//  public ResponseEntity<ParametrageDecote> getDecoteInterval(
//          @PathVariable("valeurCreance") Double valeurCreance,
//          HttpServletRequest request) {
//    ParametrageDecote exactParametrageDecote = decoteService.findIntervalDecote(valeurCreance).orElse(null);
//
//    return ResponseEntity.status(HttpStatus.OK).body(exactParametrageDecote);
//  }

  @PatchMapping(value ="/{idConvention}/{decote}")
  public ResponseEntity<ConventionDto> updateValeurDecote(@PathVariable("idConvention") Long idConvention,
                                                          @PathVariable("decote") double decote) {
    log.info("ConventionControllers:updateValeurDecote request started ");
   Convention updatedConvention = conventionService.updateValeurDecote(idConvention, decote);
    log.info("ConventionControllers:updateValeurDecote request params  {}", updatedConvention.getIdConvention());

    return ResponseEntity.status(HttpStatus.OK).body(conventionMapper.asDTO(updatedConvention));
  }

}

