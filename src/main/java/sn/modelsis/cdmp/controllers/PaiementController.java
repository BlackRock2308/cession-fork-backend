package sn.modelsis.cdmp.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.DetailPaiement;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.DetailPaiementDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.services.PaiementService;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
        DemandeCession demandeCession = paiementService.saveDemande(paiementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(demandeCession));

    }


    @GetMapping
    public ResponseEntity<List<PaiementDto>> getAllPaiements(
            HttpServletRequest request) {
        List<Paiement> paiements =
                paiementService.findAll();
        List<PaiementDto> paiementDtos = new ArrayList<>();
        for (Paiement paiement :paiements ) {
            paiementDtos.add(DtoConverter.convertToDto(paiement));
        }
        log.info("All paiements .");
        return ResponseEntity.status(HttpStatus.OK).body(paiementDtos);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaiementDto> getPaiement(
            @PathVariable Long id,
            HttpServletRequest request) {
        Paiement paiement = paiementService.getPaiement(id).orElse(null);
        log.info("Paiement . demandeId:{}", paiement.getIdPaiement());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(paiement));
    }
    @GetMapping(value = "/cdmp-pme/{id}")
    public ResponseEntity<PaiementDto> getPaiementAndDetailPaimentCDMP_PME(
            @PathVariable Long id,
            HttpServletRequest request) {
        Paiement paiement = paiementService.getPaiement(id).orElse(null);
        if (paiement==null)
            throw new CustomException("Le paiement n'existe pas");
        Set<DetailPaiement> detailPaiements= paiement.getDetailPaiements();
        Set<DetailPaiement> sortDetailPaiements= new HashSet<>();
        for (DetailPaiement detailPaiement:detailPaiements ) {
            if (detailPaiement.getTypepaiement().name().equals("CDMP_PME"))
                sortDetailPaiements.add(detailPaiement);
        }
        paiement.setDetailPaiements(sortDetailPaiements);
        log.info("Paiement . demandeId:{}", paiement.getIdPaiement());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(paiement));
    }

    @GetMapping(value = "/sica-cdmp/{id}")
    public ResponseEntity<PaiementDto> getPaiementAndDetailPaimentSICA_CDMP(
            @PathVariable Long id,
            HttpServletRequest request) {
        Paiement paiement = paiementService.getPaiement(id).orElse(null);
        if (paiement==null)
            throw new CustomException("Le paiement n'existe pas");
        Set<DetailPaiement> detailPaiements= paiement.getDetailPaiements();
        Set<DetailPaiement> sortDetailPaiements= new HashSet<>();
        for (DetailPaiement detailPaiement:detailPaiements ) {
            if (detailPaiement.getTypepaiement().name().equals("SICA_CDMP"))
                sortDetailPaiements.add(detailPaiement);
        }
        paiement.setDetailPaiements(sortDetailPaiements);
        log.info("Paiement . demandeId:{}", paiement.getIdPaiement());
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
