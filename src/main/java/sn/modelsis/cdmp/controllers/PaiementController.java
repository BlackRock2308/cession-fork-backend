package sn.modelsis.cdmp.controllers;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.DetailPaiement;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiquePaiementCDMPDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiquePaiementPMEDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.PaiementService;
import sn.modelsis.cdmp.util.DtoConverter;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@Slf4j
public class PaiementController {


    final  private PaiementService paiementService;
    final  private DemandeCessionService demandeCessionService;

    @PostMapping
    public ResponseEntity<PaiementDto> addPaiement(@RequestBody PaiementDto paiementDto){
        Paiement paiement = paiementService.addPaiementToDemandeCession(paiementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(paiement));

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

    @GetMapping("/bypme/{id}")
    public ResponseEntity<List<PaiementDto>> getPaiementsByPME(@PathVariable("id") Long id,
            HttpServletRequest request) {
        List<Paiement> paiements =
                paiementService.findAllByPME(id);
        List<PaiementDto> paiementDtos = new ArrayList<>();
        for (Paiement paiement :paiements ) {
            paiementDtos.add(DtoConverter.convertToDto(paiement));
        }
        log.info("All paiements .");
        return ResponseEntity.status(HttpStatus.OK).body(paiementDtos);

    }

    @GetMapping("/pme/{id}")
    public ResponseEntity<List<PaiementDto>> getAllPaiementsByPME(@PathVariable("id") Long id) {
        if (id==null)
            throw new CustomException("Is should nit be null") ;
        List<PaiementDto> paiementDtos = new ArrayList<>();
        List<DemandeCession> demandeCessions = demandeCessionService.findAllPMEDemandes(1L);
        if(demandeCessions==null)
            throw new CustomException("this pme don't have a payment ");
        for (DemandeCession demandeCession :demandeCessions ) {
            paiementDtos.add(DtoConverter.convertToDto(demandeCession.getPaiement()));
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

    @GetMapping(value = "/getStatistiquePaiementByPME/{anne}/{idPME}")
    public ResponseEntity<StatistiquePaiementPMEDto> getStatistiquePaiementByPME(@PathVariable int anne, @PathVariable Long idPME,
                                                                                  HttpServletRequest request)  {
        return ResponseEntity.status(HttpStatus.OK)
                .body(paiementService.getStatistiquePaiementByPME(anne, idPME));
    }

    @GetMapping(value = "/getStatistiqueAllPaiementPME/{anne}")
    public ResponseEntity<StatistiquePaiementPMEDto> getStatistiqueAllPaiementPME(@PathVariable int anne,
                                                                                   HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(paiementService.getStatistiqueAllPaiementPME(anne));
    }

    @GetMapping(value = "/getStatistiquePaiementCDMP/{anne}")
    public ResponseEntity<StatistiquePaiementCDMPDto> getStatistiquePaiementCDMP(@PathVariable int anne,
                                                                                  HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(paiementService.getStatistiquePaiementCDMP(anne));
    }

}
