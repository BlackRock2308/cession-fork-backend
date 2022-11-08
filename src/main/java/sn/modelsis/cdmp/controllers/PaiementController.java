package sn.modelsis.cdmp.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.services.PaiementService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    private final Logger log = LoggerFactory.getLogger(PaiementController.class);


    final  private PaiementService paiementService;

    final private DemandeCessionService demandeCessionService;

    public PaiementController(PaiementService paiementService, DemandeCessionService demandeCessionService) {
        this.paiementService = paiementService;
        this.demandeCessionService = demandeCessionService;
    }

    @PostMapping
    public ResponseEntity<DemandeCessionDto> addPaiement(@RequestBody PaiementDto paiementDto){

        /*if(paiementDto.getDemandecessionid()==null ||  paiementDto.getIdPaiement()!=null)
            throw new  CustomException("L'id de la demande de cession ne doit pas etre null ");

        if( paiementDto.getIdPaiement()!=null)
            throw new  CustomException("Le paiement exit déja ");*/

        DemandeCession demandeCession = paiementService.save(paiementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(demandeCession));

    }


    @GetMapping
    public ResponseEntity<List<Paiement>> getAllPaiements(
            HttpServletRequest request) {
        List<Paiement> paiements =
                paiementService.findAll();
        log.info("All paiements .");
        return ResponseEntity.status(HttpStatus.OK).body(paiements);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaiementDto> getPaiement(
            @PathVariable Long id,
            HttpServletRequest request) {
        Paiement paiement = paiementService.getPaiement(id).orElse(null);
        log.info("Paiement . demandeId:{}", paiement.getDemandeCession().getIdDemande());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(paiement));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PaiementDto> deletePaiement(
            @PathVariable Long id,
            HttpServletRequest request) {
        paiementService.delete(id);
        log.warn("Paiement deleted. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
