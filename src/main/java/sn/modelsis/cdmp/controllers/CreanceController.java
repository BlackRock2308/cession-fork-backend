package sn.modelsis.cdmp.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entitiesDtos.CreanceDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.exceptions.CustomException;
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

        Page<DemandeCessionDto> demandeList;

        log.info("CreanceController.findAllCreance request started ...");
        try{
            demandeList = demandeCessionService
                    .findAll(pageable);
        }catch (Exception e){
            log.info("Creance a payment before getting the list of creances");
            throw new CustomException("Can't get all creance curently. You need to create an instance of paiement first");
        }

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
            @RequestParam("montantCreance") double montantCreance){

        log.info("CreanceController:findCreanceByMultipleParams request started");

        List<CreanceDto> creanceList = demandeCessionService
                .findCreanceByMultipleParams(nomMarche,raisonSocial,montantCreance);
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


    /* ************* Search Creance by ReferenceBR, numeroDemande, nomMarche, statutLibelle ************* */
//
//    @GetMapping("search-by-multi-params")
//    public ResponseEntity<List<CreanceDto>> filterCreanceByMultipleParams(
//            @RequestParam("raisonSocial") String raisonSocial,
//            @RequestParam("nomMarche") String nomMarche,
//            @RequestParam("montantCreance") double montantCreance
//
//
//    ){
//        log.info("DemandeCessionController:filterCreanceByMultipleParams request started");
//        List<CreanceDto> creanceListRaisonList = demandeCessionService
//                .findCreanceByRaisonSocial(raisonSocial);
//
//        List<CreanceDto> creanceListNomMarche = demandeCessionService
//                .findCreanceByNomMarche(nomMarche);
//
//        List<CreanceDto> creanceListMontantCreance = demandeCessionService
//                .findCreanceByMontantCreance(montantCreance);
//
//
//        List<CreanceDto> similarities = new ArrayList<>();
//
//        if(!creanceListRaisonList.isEmpty()){
//            similarities = creanceListRaisonList
//                    .stream()
//                    .filter(element -> !creanceListNomMarche.contains(element))
//                    .collect(Collectors.toList());
//            log.info("Demande similaire based on criteres: {} ",similarities);
//
//        }else{
//            similarities = creanceListMontantCreance;
//            log.info("Demande similaire based Date: {} ",similarities);
//
//        }
//        log.info("DemandeCessionService:filterExactDemandeCession similarities : {} ",similarities);
//
//        log.info("DemandeCessionController:searchDemandeCessionByMultipleParams ----> reulst : {}",similarities);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(similarities);
//    }



}
