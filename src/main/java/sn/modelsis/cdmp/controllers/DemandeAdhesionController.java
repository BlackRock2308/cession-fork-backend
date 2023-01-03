package sn.modelsis.cdmp.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.util.DtoConverter;

@RestController
@RequestMapping("/api/demandeadhesion")
@RequiredArgsConstructor
@Slf4j
public class DemandeAdhesionController {

    private final DemandeAdhesionService demandeAdhesionService;

    @PostMapping
    public ResponseEntity<DemandeAdhesionDto> addDemandeAdhesion(@RequestBody DemandeAdhesionDto demandeadhesionDto,
                                                                 HttpServletRequest request) {

        DemandeAdhesion result = demandeAdhesionService.saveAdhesion(demandeadhesionDto);
        log.info("demande cession created. Id:{} ", result.getIdDemande());
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
                                              @RequestParam(name = "file") MultipartFile file,
                                              @RequestParam(name = "type") String type) {

        Optional<DemandeAdhesion> doc = null;
        try {
            doc = demandeAdhesionService.upload(id, file, type);
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
