package sn.modelsis.cdmp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.util.DtoConverter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/demandeadhesion")
public class DemandeAdhesionController {

    private final Logger log = LoggerFactory.getLogger(DemandeAdhesionController.class);


    @Autowired
    private DemandeAdhesionService demandeAdhesionService;

    @PostMapping
    public ResponseEntity<DemandeAdhesionDto> addDemandeAdhesion(@RequestBody DemandeAdhesionDto demandeadhesionDto,
                                                                 HttpServletRequest request) {

        DemandeAdhesion result = demandeAdhesionService.saveAdhesion(demandeadhesionDto);
        log.info("demande cession created. Id:{} ", result.getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @PatchMapping(value ="/{id}/rejectadhesion")
    public ResponseEntity<DemandeAdhesionDto> rejetAdhesion(@PathVariable Long id, HttpServletRequest request) {
        DemandeAdhesion demandeadhesion=demandeAdhesionService.rejetAdhesion(id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(demandeadhesion));
    }

    @PatchMapping(value = "/{id}/acceptadhesion")
    public ResponseEntity<DemandeAdhesionDto> validerAdhesion(@PathVariable Long id, HttpServletRequest request) {
        DemandeAdhesion demandeadhesion=demandeAdhesionService.validerAdhesion(id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(demandeadhesion));
    }

    @GetMapping
    public ResponseEntity<List<DemandeAdhesionDto>> getAllDemandeAdhesion(HttpServletRequest request) {
        List<DemandeAdhesion> demandeList = demandeAdhesionService.findAll();
        log.info("All Requests .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
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
