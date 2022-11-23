package sn.modelsis.cdmp.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entitiesDtos.CreanceDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.mappers.CreanceMapper;
import sn.modelsis.cdmp.services.DemandeCessionService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/creances")
@Slf4j
public class CreanceController {

    private final DemandeCessionService demandeCessionService;
    private final CreanceMapper creanceMapper;

    @GetMapping
    public ResponseEntity<Page<CreanceDto>> findAllCreance(Pageable pageable,
                                                                 HttpServletRequest request) {
        log.info("CreanceController.CreanceController request started ...");
        Page<DemandeCessionDto> demandeList = demandeCessionService
                .findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.map(creanceMapper::mapToDto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<CreanceDto>> getCreance(@PathVariable Long id,
                                                    HttpServletRequest request) {
        log.info("CreanceController.getCreance request started ...");
        Optional<CreanceDto> creanceDto = demandeCessionService
                    .getDemandeCession(id)
                    .map(creanceMapper::mapToDto);
        log.debug("CreanceController.getCreance request params : {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(creanceDto);
    }


    /************** Filtering creance by multiple parameters **************/

    @GetMapping("search-creance-by-params")
    public ResponseEntity<List<CreanceDto>> findCreanceByMultipleParams(
            @RequestParam("nomMarche") String nomMarche,
            @RequestParam("raisonSocial") String raisonSocial,
            @RequestParam("statutLibelle") String statutLibelle){

        log.info("CreanceController:findCreanceByMultipleParams request started");

        List<CreanceDto> creanceList = demandeCessionService
                .findCreanceByMultipleParams(nomMarche,raisonSocial,statutLibelle);
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
