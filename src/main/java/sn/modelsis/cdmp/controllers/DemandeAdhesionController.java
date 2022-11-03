package sn.modelsis.cdmp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.util.DtoConverter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/demandeadhesion")
@RequiredArgsConstructor
public class DemandeAdhesionController {

    private final Logger log = LoggerFactory.getLogger(DemandeAdhesionController.class);
    private final DemandeAdhesionService demandeAdhesionService;

    @PostMapping
    public ResponseEntity<DemandeAdhesionDto> addDemandeAdhesion(@RequestBody DemandeAdhesionDto demandeadhesionDto,
                                                                 HttpServletRequest request) {

        DemandeAdhesion result = demandeAdhesionService.saveAdhesion(demandeadhesionDto);
        log.info("demande cession created. Id:{} ", result.getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @GetMapping
    public ResponseEntity<List<DemandeAdhesionDto>> getAllDemandeAdhesion(HttpServletRequest request) {
        List<DemandeAdhesionDto> demandeList = demandeAdhesionService.findAll();
        log.info("Fetching All Demande Adhesion ....");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(demandeList);
    }
/** On pourra effectuer cette operation dans le controller de la Demande**/
//    @GetMapping(value = "/{id}")
//    public Optional<DemandeAdhesion> getDemandeAdhesion(@PathVariable Long id, HttpServletRequest request) {
//        Optional<DemandeAdhesion> demande = demandeAdhesionService.findById(id);
//        return demandeAdhesionService.findById(demande.get().getIdDemande());
//    }

    @PatchMapping(value ="/{id}/rejectadhesion")
    public ResponseEntity<DemandeAdhesionDto> rejetAdhesion(@PathVariable Long id,
                                                            HttpServletRequest request) {
        DemandeAdhesion demandeadhesion=demandeAdhesionService.rejetAdhesion(id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(demandeadhesion));
    }

    @PatchMapping(value = "/{id}/acceptadhesion")
    public ResponseEntity<DemandeAdhesionDto> validerAdhesion(@PathVariable Long id,
                                                              HttpServletRequest request) {
        DemandeAdhesion demandeadhesion=demandeAdhesionService.validerAdhesion(id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(demandeadhesion));
    }



    @PostMapping("/{id}/upload")
    @Operation(summary = "Upload file", description = "Upload a new file for Pme adhesion demand")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<DemandeAdhesionDto> addDocument(@PathVariable Long id,
                                              @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

        Optional<DemandeAdhesion> doc = null;
        try {
            doc = demandeAdhesionService.upload(id, file, TypeDocument.valueOf(type));
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (doc.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        log.info("Document added. Id:{} ", doc.get().getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(doc.get()));
    }


}
