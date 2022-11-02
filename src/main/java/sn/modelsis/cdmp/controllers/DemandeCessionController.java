package sn.modelsis.cdmp.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/demandecession")
public class DemandeCessionController {

    private final Logger log = LoggerFactory.getLogger(DemandeCessionController.class);
    private final DemandeCessionService demandeCessionService;

    @PostMapping
    public ResponseEntity<DemandeCessionDto> addDemandeCession(@RequestBody DemandeCessionDto demandecessionDto,
                                                               HttpServletRequest request) {
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);

        DemandeCession result = demandeCessionService.saveCession(demandecession);
        log.info("demande cession created. Id:{} ", result.getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @PatchMapping(value ="/{id}/rejectcession")
    public ResponseEntity<DemandeCessionDto> rejeterCession(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        // DemandeCessionDto demandecessionDto1=demandeCessionService.rejeterCession(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping(value = "/{id}/acceptcession")
    public ResponseEntity<DemandeCessionDto> validerCession(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        DemandeCessionDto demandecessionDto1=demandeCessionService.validerCession(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
    }

    @PatchMapping(value = "/{id}/validanalyse")
    public ResponseEntity<DemandeCessionDto> validerAnalyse(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        DemandeCessionDto demandecessionDto1=demandeCessionService.validerAnalyse(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
    }

    @PatchMapping(value = "/{id}/rejectanalyse")
    public ResponseEntity<DemandeCessionDto> rejeterAnalyse(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        DemandeCessionDto demandecessionDto1=demandeCessionService.rejeterAnalyse(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
    }

    @PatchMapping(value = "/{id}/demandercomplements")
    public ResponseEntity<DemandeCessionDto> demanderComplements(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        DemandeCessionDto demandecessionDto1=demandeCessionService.demanderComplements(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
    }

    @PatchMapping(value = "/{id}/validerRecevabilite")
    public ResponseEntity<DemandeCessionDto> validerRecevabilite(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        DemandeCessionDto demandecessionDto1=demandeCessionService.validerRecevabilite(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
    }

    @PatchMapping(value = "/{id}/rejeterRecevabilite")
    public ResponseEntity<DemandeCessionDto> rejeterRecevabilite(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        DemandeCessionDto demandecessionDto1=demandeCessionService.rejeterRecevabilite(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
    }
    @GetMapping
    public ResponseEntity<List<DemandeCessionDto>> getAllDemandeCession(HttpServletRequest request) {
        List<DemandeCession> demandeList = demandeCessionService.findAll();
        log.info("All Requests .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "pme/{id}")
    public ResponseEntity<List<DemandeCessionDto>> getAllPMEDemandeCession(@PathVariable Long id, HttpServletRequest request) {
        List<DemandeCession> demandeList = demandeCessionService.findAllPMEDemandes(id);
        log.info("All Requests .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<DemandeCessionDto> getAllDemandeCession(@PathVariable Long id, HttpServletRequest request) {
        DemandeCession demande = demandeCessionService.findById(id).orElse(null);
        log.info("Demande . Id:{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(demande));
    }

}