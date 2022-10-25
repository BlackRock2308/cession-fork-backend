package sn.modelsis.cdmp.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.services.PaiementService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    private final Logger log = LoggerFactory.getLogger(PaiementController.class);

    @Autowired
    private PaiementService paiementService;
    @Autowired
    private DemandeCessionService demandeCessionService;

    @PostMapping()
    public ResponseEntity<Paiement> addPaiement(@RequestBody Paiement paiement, HttpServletRequest request){
        //Paiement paiement = DtoConverter.convertToEntity(paiementDto);
//        Paiement result = paiementService.save(paiement);
//        paiementService.save(result);
        Paiement savePaiement = paiementService.save(paiement);
        paiementService.save(savePaiement);
        log.info("Paiement created : ", savePaiement.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(savePaiement);



    }

    @PutMapping()
    public ResponseEntity<PaiementDto> updatePaiement(@RequestBody PaiementDto paiementDto,
                                                          HttpServletRequest request) {
        Paiement paiement = DtoConverter.convertToEntity(paiementDto);
        Paiement result = paiementService.save(paiement);
        log.info("Paiement updated. Id:{}", result.getIdPaiement());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
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
    public ResponseEntity<Paiement> getPaiement(
            @PathVariable Long id,
            HttpServletRequest request) {
        Paiement paiement = paiementService.getPaiement(id).orElse(null);
        log.info("Paiement . Id:{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(paiement);
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
