package sn.modelsis.cdmp.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/demandecession")
public class DemandeCessionController {

    private final Logger log = LoggerFactory.getLogger(DemandeCessionController.class);
    private final DemandeCessionService demandeCessionService;

    @PostMapping
    public ResponseEntity<DemandeCessionDto> addDemandeCession(@RequestBody @Valid DemandeCessionDto demandecessionDto,
                                                               HttpServletRequest request) {
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);

        DemandeCession result = demandeCessionService.saveCession(demandecession);
        log.info("DemandeCessionController:addDemandeCession save in database {}", result.getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @GetMapping
    public ResponseEntity<Page<DemandeCessionDto>> getAllDemandeCession(Pageable pageable,
                                                                        HttpServletRequest request) {
        Page<DemandeCessionDto> demandeList = demandeCessionService.findAll(pageable);
        log.info("DemandeCessionController:getAllDemandeCession request started");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<DemandeCessionDto> getDemandeCession(@PathVariable Long id,
                                                               HttpServletRequest request) {
        DemandeCessionDto demande = demandeCessionService.getDemandeCession(id).orElse(null);
        log.info("DemandeCessionController:getDemandeCession request params  {}", demande.getIdDemande());
        return ResponseEntity.status(HttpStatus.OK)
                .body(demande);
    }


    /******* Recevabilité : Endpoint pour rejeter une demande de cession -- accepter une demande de cession ******* ***/

    @PatchMapping(value ="/{idDemande}/rejeterRecevabilite")
    public ResponseEntity<DemandeCessionDto> rejectCession(@PathVariable("idDemande") Long idDemande) {
        DemandeCession rejectedDemande = demandeCessionService.rejeterRecevabilite(idDemande);
        log.info("DemandeCessionController:rejectCession request params  {}", rejectedDemande.getIdDemande());
       return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(rejectedDemande));
    }

    @PatchMapping(value ="/{idDemande}/validerRecevabilite")
    public ResponseEntity<DemandeCessionDto> acceptCession(@PathVariable("idDemande") Long idDemande) {
        DemandeCession acceptedDemande = demandeCessionService.validerRecevabilite(idDemande);
        log.info("DemandeCessionController:acceptCession request params  {}", acceptedDemande.getIdDemande());

        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(acceptedDemande));
    }

    /** ******* Endpoints pour la validation de la Demande de Cession ************ **/

    @PatchMapping(value ="/{idDemande}/validateAnalyse")
    public ResponseEntity<DemandeCessionDto> validateAnalyseNonRisque(@PathVariable("idDemande") Long idDemande) {
        DemandeCession acceptedDemande = demandeCessionService.analyseDemandeCessionNonRisque(idDemande);
        log.info("DemandeCessionController:acceptCession request params  {}", acceptedDemande.getIdDemande());

        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(acceptedDemande));
    }

    @PatchMapping(value ="/{idDemande}/rejectedAnalyse")
    public ResponseEntity<DemandeCessionDto> rejectAnalyseRisque(@PathVariable("idDemande") Long idDemande) {
        DemandeCession acceptedDemande = demandeCessionService.analyseDemandeCessionRisque(idDemande);
        log.info("DemandeCessionController:acceptCession request params  {}", acceptedDemande.getIdDemande());

        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(acceptedDemande));
    }

    @PatchMapping(value ="/{idDemande}/complementAnalyse")
    public ResponseEntity<DemandeCessionDto> completeDocAnalyse(@PathVariable("idDemande") Long idDemande) {
        DemandeCession acceptedDemande = demandeCessionService.analyseDemandeCessionComplement(idDemande);
        log.info("DemandeCessionController:acceptCession request params  {}", acceptedDemande.getIdDemande());

        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(acceptedDemande));
    }

    /** *******Endpoints pour la recevabilité de la Demande de Cession******* **/

//    @PatchMapping(value = "/{id}/validerRecevabilite")
//    public ResponseEntity<DemandeCessionDto> validerRecevabilite(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
//        DemandeCessionDto demandecessionDto1=demandeCessionService.validerRecevabilite(demandecessionDto);
//        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
//    }
//
//    @PatchMapping(value = "/{id}/rejeterRecevabilite")
//    public ResponseEntity<DemandeCessionDto> rejeterRecevabilite(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
//        DemandeCessionDto demandecessionDto1=demandeCessionService.rejeterRecevabilite(demandecessionDto);
//        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
//    }

    /** --------********************************************-------**/


    @GetMapping(value = "pme/{id}")
    public ResponseEntity<List<DemandeCessionDto>> getAllPMEDemandeCession(@PathVariable Long id, HttpServletRequest request) {
        List<DemandeCession> demandeList = demandeCessionService.findAllPMEDemandes(id);
        log.info("All Requests .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

    /* **************** Filtering accepting and rejected Demande **************** */

    @GetMapping("/rejected")
    public ResponseEntity<List<DemandeCessionDto>> getAllRejectedDemande(HttpServletRequest request) {
        log.info("DemandeCessionController:getAllRejectedDemande request started");
        List<DemandeCession> demandeList = demandeCessionService.findAllDemandeRejetee();
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/recevable")
    public ResponseEntity<List<DemandeCessionDto>> getAllAcceptedDemande(HttpServletRequest request) {
        log.info("DemandeCessionController:getAllAcceptedDemande request started");
        List<DemandeCession> demandeList = demandeCessionService.findAllDemandeAcceptee();
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/complement-requis")
    public ResponseEntity<List<DemandeCessionDto>> getAllComplementRequisDemande(HttpServletRequest request) {
        log.info("DemandeCessionController:getAllRejectedDemande request started");
        List<DemandeCession> demandeList = demandeCessionService.findAllDemandeComplementRequis();
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }


    @GetMapping(value="bystatut")
    public ResponseEntity<Page<DemandeCessionDto>> getAllDemandeCessionByStatut(Pageable pageable, @RequestParam(value = "statut", required = true, defaultValue = "") String statut,
                                                                                HttpServletRequest request) {
        Page<DemandeCessionDto> demandeList = demandeCessionService.findAllByStatut(pageable,statut);
        log.info("Fetching All Deamndes Cession ....");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList);
    }


}
