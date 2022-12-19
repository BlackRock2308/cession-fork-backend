package sn.modelsis.cdmp.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entitiesDtos.CreanceDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.mappers.CreanceMapper;
import sn.modelsis.cdmp.mappers.CreanceWithNoPaymentMapper;
import sn.modelsis.cdmp.services.DemandeCessionService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/creances")
@Slf4j
public class CreanceController {

    private final DemandeCessionService demandeCessionService;
    private final CreanceMapper creanceMapper;

    private final CreanceWithNoPaymentMapper noPaymentMapper;

    @GetMapping()
    public ResponseEntity<Page<CreanceDto>> findNoPaidCreance(Pageable pageable,
                                                           HttpServletRequest request) {

        Page<DemandeCessionDto> demandeList;


        log.info("CreanceController.findAllCreance request started ...");
        try{
            demandeList = demandeCessionService
                    .findAllCreanceWithTheRightStatut(pageable);
            log.info("la liste : {}",demandeList);

        }catch (Exception e){
            log.info("Create a payment before getting the list of creances");
            throw new CustomException("Can't get all creance curently. You need to create an instance of paiement first");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.map(noPaymentMapper::mapToDto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<CreanceDto>> getCreance(@PathVariable Long id,
                                                    HttpServletRequest request) {
        log.info("CreanceController.getCreance request started ...");
        Optional<CreanceDto> creanceDto = demandeCessionService
                    .getDemandeCession(id)
                    .map(noPaymentMapper::mapToDto);
        log.debug("CreanceController.getCreance request params : {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(creanceDto);
    }


    /************** Filtering creance by multiple parameters **************/

    @GetMapping("search-creance-by-params")
    public ResponseEntity<List<CreanceDto>> findCreanceByMultipleParams(
            @RequestParam(value = "raisonSocial") String raisonSocial,
            @RequestParam(value = "montantCreance") String montantCreance,
            @RequestParam(value = "nomMarche") String nomMarche,
            @RequestParam(value = "statutLibelle") String statutLibelle,
            @RequestParam(value = "decote") String decote,
            @RequestParam(value = "startDateD", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateD,
            @RequestParam(value = "endDateD", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateD,
            @RequestParam(value = "startDateM", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateM,
            @RequestParam(value = "endDateM", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateM){

        log.info("CreanceController:findCreanceByMultipleParams request started");
        double newMontantCreance = 0;
        double newDecote = 0;

        if(montantCreance==null || montantCreance.isEmpty()){
            montantCreance = String.valueOf(0.0);
            newMontantCreance = Double.parseDouble(montantCreance);
        }else {
            newMontantCreance = Double.parseDouble(montantCreance);
        }

        if(decote==null || decote.isEmpty()){
            decote = String.valueOf(0.0);
            newDecote = Double.parseDouble(decote);
        }else {
            newDecote = Double.parseDouble(decote);
        }
        List<CreanceDto> creanceList = demandeCessionService
                .findCreanceByMultipleParams(nomMarche,raisonSocial,newMontantCreance,statutLibelle,newDecote,startDateD,endDateD,startDateM,endDateM);
        log.info("CreanceController:findCreanceByMultipleParams: creanceList : {}",creanceList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(creanceList);
    }

    /* ************************ Search by RaisonSocial ************************ */
    @GetMapping("searchByRaisonSocial")
    public ResponseEntity<List<CreanceDto>> searchCreanceByRaisonSocial(
            @RequestParam("raisonSocial") String raisonSocial){
        log.info("CreanceController:searchCreancebyRaisonSocial request started");

        List<CreanceDto> creanceList = demandeCessionService
                .findCreanceByRaisonSocial(raisonSocial);
        log.info("CreanceController:searchCreancebyRaisonSocial: raisonSocial : {}",raisonSocial);
        return ResponseEntity.status(HttpStatus.OK)
                .body(creanceList);
    }


    /* ************************ Search by nom Marche ************************ */
    @GetMapping("searchByNomMarche")
    public ResponseEntity<List<CreanceDto>> searchCreanceByNomMarche(
            @RequestParam("nomMarche") String nomMarche){
        log.info("CreanceController:searchCreanceByNomMarche request started");

        List<CreanceDto> creanceList = demandeCessionService
                .findCreanceByNomMarche(nomMarche);
        log.info("CreanceController:searchCreanceByNomMarche: nomMarche : {}",nomMarche);
        return ResponseEntity.status(HttpStatus.OK)
                .body(creanceList);
    }



    /* ************************ Search by montant Creance ************************ */
    @GetMapping("searchByMontantCreance")
    public ResponseEntity<List<CreanceDto>> searchCreanceByMontantCreance(
            @RequestParam("montantCreance") double montantCreance){
        log.info("CreanceController:searchCreanceByMontantCreance request started");

        List<CreanceDto> creanceList = demandeCessionService
                .findCreanceByMontantCreance(montantCreance);
        log.info("CreanceController:searchCreanceByMontantCreance: montant Creance : {}",montantCreance);
        return ResponseEntity.status(HttpStatus.OK)
                .body(creanceList);
    }

}
