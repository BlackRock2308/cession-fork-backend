package sn.modelsis.cdmp.controllers;

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
@RequestMapping("/api/demandecession")
public class DemandeCessionController {

    private final Logger log = LoggerFactory.getLogger(DemandeCessionController.class);


    @Autowired
    private DemandeCessionService demandeCessionService;


    @PostMapping
    public ResponseEntity<DemandeCessionDto> addDemandeCession(@RequestBody DemandeCessionDto demandecessionDto,
                                                        HttpServletRequest request) {
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);

        DemandeCession result = demandeCessionService.saveCession(demandecession);
        log.info("demande cession created. Id:{} ", result.getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @PatchMapping(value ="/{id}/reject")
    public ResponseEntity<DemandeCessionDto> rejetAdhesion(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        DemandeCessionDto demandecessionDto1=demandeCessionService.rejetCession(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
    }

    @PatchMapping(value = "/{id}/accept")
    public ResponseEntity<DemandeCessionDto> validerAdhesion(@RequestBody DemandeCessionDto demandecessionDto, HttpServletRequest request) {
        DemandeCessionDto demandecessionDto1=demandeCessionService.validerCession(demandecessionDto);
        return ResponseEntity.status(HttpStatus.OK).body(demandecessionDto1);
    }

    @GetMapping
    public ResponseEntity<List<DemandeCessionDto>> getAllDemandeCession(HttpServletRequest request) {
        List<DemandeCession> demandeList = demandeCessionService.findAll();
        log.info("All Requests .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

}
