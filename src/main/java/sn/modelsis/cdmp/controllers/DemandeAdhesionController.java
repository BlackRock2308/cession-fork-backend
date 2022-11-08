package sn.modelsis.cdmp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Slf4j
public class DemandeAdhesionController {

    private final DemandeAdhesionService demandeAdhesionService;
    @PostMapping
    public ResponseEntity<DemandeAdhesionDto> addDemandeAdhesion(@RequestBody DemandeAdhesionDto demandeadhesionDto,
                                                                 HttpServletRequest request) {
        log.info("DemandeAdhesionController:addDemandeAdhesion request started");
        DemandeAdhesion result = demandeAdhesionService.saveAdhesion(demandeadhesionDto);
        log.info("DemandeAdhesionController:addDemandeAdhesion request params  {}", result.getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @GetMapping
    public ResponseEntity<Page<DemandeAdhesionDto>> getAllDemandeAdhesion(Pageable pageable,
                                                                          HttpServletRequest request) {
        log.info("DemandeAdhesionController:getAllDemandeAdhesion request started");
        Page<DemandeAdhesionDto> demandeList = demandeAdhesionService.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(demandeList);
    }
    @GetMapping(value = "/{id}")
    public Optional<DemandeAdhesionDto> getDemandeAdhesion(@PathVariable Long id,
                                                           HttpServletRequest request) {
        log.info("DemandeAdhesionController:getDemandeAdhesion request started");
        log.debug("DemandeAdhesionController:getDemandeAdhesion request param {}", id);
        return demandeAdhesionService.findById(id);
    }

    @PatchMapping(value ="/{id}/rejectadhesion")
    public ResponseEntity<DemandeAdhesionDto> rejetAdhesion(@PathVariable("id") Long id,
                                                            HttpServletRequest request) {
        log.info("DemandeAdhesionController:rejetAdhesion request started");
        DemandeAdhesion demandeadhesion=demandeAdhesionService.rejetAdhesion(id);
        log.debug("DemandeAdhesionController:rejetAdhesion request params  {}", demandeadhesion.getIdDemande());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(demandeadhesion));
    }

    @PatchMapping(value = "/{id}/acceptadhesion")
    public ResponseEntity<DemandeAdhesionDto> validerAdhesion(@PathVariable("id") Long id,
                                                              HttpServletRequest request) {
        log.info("DemandeAdhesionController:validerAdhesion request started");
        DemandeAdhesion demandeadhesion=demandeAdhesionService.validerAdhesion(id);
        log.debug("DemandeAdhesionController:validerAdhesion request params  {}", demandeadhesion.getIdDemande());
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
        log.info("DemandeAdhesionController.addDocument : Document added with Demande Id:{} ", doc.get().getIdDemande());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(doc.get()));
    }
}
