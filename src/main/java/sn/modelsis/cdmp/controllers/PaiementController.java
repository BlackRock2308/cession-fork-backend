package sn.modelsis.cdmp.controllers;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.services.PaiementService;
import sn.modelsis.cdmp.util.DtoConverter;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    private final Logger log = LoggerFactory.getLogger(PaiementController.class);


    final  private PaiementService paiementService;

    public PaiementController(PaiementService paiementService ) {
        this.paiementService = paiementService;
    }

    @PostMapping
    public ResponseEntity<PaiementDto> addPaiement(@RequestBody PaiementDto paiementDto){

        /*if(paiementDto.getDemandecessionid()==null ||  paiementDto.getIdPaiement()!=null)
            throw new  CustomException("L'id de la demande de cession ne doit pas etre null ");

        if( paiementDto.getIdPaiement()!=null)
            throw new  CustomException("Le paiement exit déja ");*/

        Paiement paiementCreated = paiementService.save(paiementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(paiementCreated));

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
