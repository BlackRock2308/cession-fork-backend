package sn.modelsis.cdmp.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.NewDemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiqueDemandeCession;
import sn.modelsis.cdmp.exceptions.NotFoundException;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.UtilisateurService;
import sn.modelsis.cdmp.util.DtoConverter;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/demandecession")
@Slf4j
public class DemandeCessionController {

    private final DemandeCessionService demandeCessionService;

    private final UtilisateurService utilisateurService;

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
        log.info("DemandeCessionController:getDemandeCession request started");
        DemandeCessionDto demande = demandeCessionService.getDemandeCession(id).orElse(null);
        log.info("DemandeCessionController:getDemandeCession request params  {}", demande.getIdDemande());
        return ResponseEntity.status(HttpStatus.OK)
                .body(demande);
    }

    @GetMapping(value = "statistiqueDemandeCession/{anne}")
    public ResponseEntity<List<StatistiqueDemandeCession>> getStatistiqueDemandeCession(@PathVariable int anne,
                                                                             HttpServletRequest request) {
       return ResponseEntity.status(HttpStatus.OK)
                .body(demandeCessionService.getStatistiqueDemandeCession(anne));
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
        log.info("DemandeCessionController:acceptCession request started... ");
        DemandeCession acceptedDemande = demandeCessionService.validerRecevabilite(idDemande);
        log.info("DemandeCessionController:acceptCession request params  {}", acceptedDemande.getIdDemande());

        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(acceptedDemande));
    }

    /** ******* Endpoints pour la validation de la Demande de Cession ************ **/

    @PatchMapping(value ="/{idDemande}/validateAnalyse")
    public ResponseEntity<DemandeCessionDto> validateAnalyseNonRisque(@PathVariable("idDemande") Long idDemande) {
        log.info("DemandeCessionController:validateAnalyseNonRisque request started... ");
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


    @GetMapping(value = "pme/{id}")
    public ResponseEntity<List<DemandeCessionDto>> getAllPMEDemandeCession(@PathVariable Long id, HttpServletRequest request) {
        List<DemandeCession> demandeList = demandeCessionService.findAllPMEDemandes(id);
        log.info("DemandeCessionController:getAllPMEDemandeCession {} : ");

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
    public ResponseEntity<Page<DemandeCessionDto>> getAllDemandeCessionByStatut(Pageable pageable, @RequestParam(value = "statut", required = true, defaultValue = "") String[] statuts,
                                                                                HttpServletRequest request) {
        Page<DemandeCessionDto> demandeList = demandeCessionService.findAllByStatut(pageable,statuts);
        log.info("Fetching All Deamndes Cession ....");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList);
    }

    @GetMapping(value="byStatutAndPme")
    public ResponseEntity<Page<DemandeCessionDto>> getAllDemandeCessionByStatutAndPME(Pageable pageable, @RequestParam(value = "statut", required = true, defaultValue = "") String statut,@RequestParam(value = "pme", required = true, defaultValue = "") Long idPME,
                                                                                HttpServletRequest request) {
        Page<DemandeCessionDto> demandeList = demandeCessionService.findAllByStatutAndPME(pageable,statut,idPME);
        log.info("Fetching All Deamndes Cession by statut {} and PME....",statut,idPME);
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList);
    }

    /**
     * {@code POST  /signer-convention-dg} : signer convention par le dg
     *
     * @param  codePin of the user .
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @PostMapping("/{idDemande}/signer-convention-dg/{idUtilisateur}")
    public  ResponseEntity<Boolean> signerConventionDG(@RequestBody String codePin,@PathVariable Long idUtilisateur,@PathVariable Long idDemande)  {

        log.info("codePin:{}",codePin);
        if (!(demandeCessionService.getDemandeCession(idDemande).isPresent()))
            throw new NotFoundException("La demande de cession n'existe pas");

        DemandeCession demandeSignee;
        if (utilisateurService.signerConvention(idUtilisateur,codePin))
            demandeCessionService.signerConventionDG(idDemande);

        return ResponseEntity.ok().body(utilisateurService.signerConvention(idUtilisateur,codePin));

    }

    /**
     * {@code POST  /signer-convention-pme} : signer convention par le pme
     *
     * @param  codePin of the user .
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @PostMapping("/{idDemande}/signer-convention-pme/{idUtilisateur}")
    public  ResponseEntity<Boolean> signerConventionPME(@RequestBody String codePin,@PathVariable Long idUtilisateur,@PathVariable Long idDemande)  {

        log.info("codePin:{}",codePin);
        if (!(demandeCessionService.getDemandeCession(idDemande).isPresent()))
            throw new NotFoundException("La demande de cession n'existe pas");

        DemandeCession demandeSignee;
        if (utilisateurService.signerConvention(idUtilisateur,codePin))
            demandeCessionService.signerConventionPME(idDemande);

        return ResponseEntity.ok().body(utilisateurService.signerConvention(idUtilisateur,codePin));

    }

    /**
     * {@code PATCH  /updateStatutDemande} : mettre à jour le statut de la demande de cession
     *
     * @param  idDemande of the demand .
     * @param  statut of the demand .
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @PatchMapping(value ="/{idDemande}/statut")
    public ResponseEntity<DemandeCessionDto> updateStatutDemande(@PathVariable("idDemande") Long idDemande,@RequestParam(value = "statut", required = true) String statut) {
        log.info("DemandeCessionController:updateStatutDemande request started... ");
        DemandeCession updatedDemande = demandeCessionService.updateStatut(idDemande,statut);
        log.info("DemandeCessionController:updateStatutDemande request params  {}", updatedDemande.getIdDemande());

        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(updatedDemande));
    }


    /* ************* Search Demande Cession by ReferenceBR, numeroDemande, nomMarche, statutLibelle ************* */

    @GetMapping("search-by-multi-params")
    public ResponseEntity<List<DemandeCessionDto>> filterDemandeCessionByMultipleParams(
            @RequestParam("referenceBE") String referenceBE,
            @RequestParam("numeroDemande") String numeroDemande,
            @RequestParam("nomMarche") String nomMarche,
            @RequestParam("statutLibelle") String statutLibelle,
            @RequestParam("startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate

    ){
        log.info("DemandeCessionController:searchDemandeCessionByMultipleParams request started");


        List<DemandeCessionDto> cessionListByDate = demandeCessionService.findDemandeCessionByLocalDateTime(startDate, endDate);
        log.info("DemandeCessionService:filterExactDemandeCession cessionListByDate : {} ",cessionListByDate);


        List<DemandeCessionDto> cessionListByOthersCriteria = demandeCessionService.findDemandeCessionByMultipleParams(referenceBE,
                numeroDemande,
                nomMarche,
                statutLibelle);
        log.info("DemandeCessionService:filterExactDemandeCession cessionListByOthersCriteria : {} ",cessionListByOthersCriteria);

        List<DemandeCessionDto> similarities = new ArrayList<>();

        if(!cessionListByOthersCriteria.isEmpty()){
            similarities = cessionListByOthersCriteria
                    .stream()
                    .filter(element -> !cessionListByDate.contains(element))
                    .collect(Collectors.toList());
            log.info("Demande similaire based on criteres: {} ",similarities);

        }else{
            similarities = cessionListByDate;
            log.info("Demande similaire based Date: {} ",similarities);

        }
        log.info("DemandeCessionService:filterExactDemandeCession similarities : {} ",similarities);

        log.info("DemandeCessionController:searchDemandeCessionByMultipleParams ----> reulst : {}",similarities);
        return ResponseEntity.status(HttpStatus.OK)
                .body(similarities);
    }

    /* ************************ Search by Statut Libellé ************************ */
    @GetMapping("searchByStatut/{statutLibelle}")
    public ResponseEntity<List<DemandeCessionDto>> searchDemandeCessionByStatutLibelle(
            @PathVariable("statutLibelle") String statutLibelle){
        log.info("DemandeCessionController:searchDemandeCessionByStatutLibelle request started");

        List<DemandeCessionDto> demandeList = demandeCessionService
                .findDemandeCessionByStatutLibelle(statutLibelle);
        log.info("DemandeCessionController:searchDemandeCessionByStatutLibelle: statutLibelle : {}",statutLibelle);
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList);
    }

    /* ************************ Search by Date de Deamnde de Cession ************************ */
    @GetMapping("/searchByLocalDate/dateSearch")
    public ResponseEntity<List<DemandeCessionDto>> findDemandeCessionByDate(
            @RequestParam("startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,

            @RequestParam("endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){

        log.info("DemandeCessionController:findDemandeCessionByDate request started");

        log.info("DemandeCessionController:findDemandeCessionByDate: startDate : {} and endDate : {}",startDate, endDate);
        List<DemandeCessionDto> demandeList = demandeCessionService
                .findDemandeCessionByLocalDateTime(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList);
    }




    //    @GetMapping("search-by-multi-params")
//    public ResponseEntity<List<DemandeCessionDto>> searchDemandeCessionByMultipleParams(
//            @RequestParam("referenceBE") String referenceBE,
//            @RequestParam("numeroDemande") String numeroDemande,
//            @RequestParam("nomMarche") String nomMarche,
//            @RequestParam("statutLibelle") String statutLibelle
//
//            ){
//        log.info("DemandeCessionController:searchDemandeCessionByMultipleParams request started");
//
//        List<DemandeCessionDto> demandeList = demandeCessionService
//                .findDemandeCessionByMultipleParams(referenceBE, numeroDemande,nomMarche,statutLibelle);
//        log.info("DemandeCessionController:searchDemandeCessionByMultipleParams: referenceBE : {}",referenceBE);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(demandeList);
//    }




    //
//    @GetMapping("search/{numeroDemande}")
//    public ResponseEntity<List<DemandeCessionDto>> searchDemandeCessionByParameters(
//            @PathVariable("numeroDemande") String numeroDemande){
//        log.info("DemandeCessionController:searchDemandeCessionByParameters request started");
//
//        List<DemandeCessionDto> demandeList = demandeCessionService
//                .findDemandeCessionByMultipleCritere(numeroDemande);
//        log.info("DemandeCessionController:searchDemandeCessionByParameters request params : {}",numeroDemande);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(demandeList);
//    }

}
