package sn.modelsis.cdmp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.util.DtoConverter;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/demandeadhesion")
public class DemandeAdhesionController {  private final Logger log = LoggerFactory.getLogger(DemandeAdhesionController.class);


    @Autowired
    private DemandeAdhesionService demandeAdhesionService;


    @PostMapping
    public ResponseEntity<DemandeAdhesionDto> addDemandeAdhesion(@RequestBody DemandeAdhesionDto demandeadhesionDto,
                                                               HttpServletRequest request) {
        DemandeAdhesion demandeadhesion = DtoConverter.convertToEntity(demandeadhesionDto);

        DemandeAdhesion result = demandeAdhesionService.saveAdhesion(demandeadhesion);
        log.info("demande cession created. Id:{} ", result.getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

//    @PatchMapping(value ="/{id}/rejectadhesion")
//    public ResponseEntity<DemandeAdhesionDto> rejetAdhesion(@RequestBody DemandeAdhesionDto demandeadhesionDto, HttpServletRequest request) {
//        DemandeAdhesionDto demandeadhesionDto1=demandeAdhesionService.rejetAdhesion(demandeadhesionDto);
//        return ResponseEntity.status(HttpStatus.OK).body(demandeadhesionDto1);
//    }
//
//    @PatchMapping(value = "/{id}/acceptadhesion")
//    public ResponseEntity<DemandeAdhesionDto> validerAdhesion(@RequestBody DemandeAdhesionDto demandeadhesionDto, HttpServletRequest request) {
//        DemandeAdhesionDto demandeadhesionDto1=demandeAdhesionService.validerAdhesion(demandeadhesionDto);
//        return ResponseEntity.status(HttpStatus.OK).body(demandeadhesionDto1);
//    }

    @GetMapping
    public ResponseEntity<List<DemandeAdhesionDto>> getAllDemandeAdhesion(HttpServletRequest request) {
        List<DemandeAdhesion> demandeList = demandeAdhesionService.findAll();
        log.info("All Requests .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }


}
