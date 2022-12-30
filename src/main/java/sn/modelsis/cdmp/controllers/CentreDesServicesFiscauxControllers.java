package sn.modelsis.cdmp.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.CentreDesServicesFiscaux;
import sn.modelsis.cdmp.entitiesDtos.CentreDesServicesFiscauxDto;
import sn.modelsis.cdmp.services.CentreDesServicesFiscauxService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SNDIAGNEF
 * REST Controller to handle Ministere
 */
@RestController
@RequestMapping("/api/centre_des_servicesFiscaux")
@RequiredArgsConstructor
public class CentreDesServicesFiscauxControllers {

    private final Logger log = LoggerFactory.getLogger(CentreDesServicesFiscauxControllers.class);


    private final CentreDesServicesFiscauxService centreDesServicesFiscauxService;

    @GetMapping
    public ResponseEntity<List<CentreDesServicesFiscauxDto>> getAllCentreDesServicesFiscaux(HttpServletRequest request) {
        List<CentreDesServicesFiscaux> ministeres = centreDesServicesFiscauxService.findAll();
        log.info("All CentreDesServicesFiscaux .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(ministeres.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

    }

    @GetMapping("/{code}")
    public ResponseEntity<CentreDesServicesFiscauxDto> getCentreDesServicesFiscauxByCode(@PathVariable String code) {
        log.debug("REST request to get CentreDesServicesFiscauxr : {}", code);
        CentreDesServicesFiscaux md = centreDesServicesFiscauxService.findByCode(code);
        return  ResponseEntity.ok().body(DtoConverter.convertToDto(md));
    }

    @PutMapping()
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict duplicated code")})
    public ResponseEntity<CentreDesServicesFiscauxDto> updateCentreDesServicesFiscaux(@RequestBody CentreDesServicesFiscauxDto centreDesServicesFiscauxrDto,
                                                                                       HttpServletRequest request) {
        CentreDesServicesFiscaux centreDesServicesFiscaux = DtoConverter.convertToEntity(centreDesServicesFiscauxrDto);
        centreDesServicesFiscaux = centreDesServicesFiscauxService.save(centreDesServicesFiscaux);
        if(centreDesServicesFiscaux == null){
            log.info("CentreDesServicesFiscauxrControllers:ce code existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(DtoConverter.convertToDto(centreDesServicesFiscaux));
        }
        log.info("CentreDesServicesFiscauxrControllers:CentreDesServicesFiscauxr modifié");
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(centreDesServicesFiscaux));
    }

    @PostMapping()
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict duplicated code")})
    public ResponseEntity<CentreDesServicesFiscauxDto> addCentreDesServicesFiscaux(@RequestBody CentreDesServicesFiscauxDto centreDesServicesFiscauxrDto,
                                                                                    HttpServletRequest request) {
        CentreDesServicesFiscaux centreDesServicesFiscaux = DtoConverter.convertToEntity(centreDesServicesFiscauxrDto);
        centreDesServicesFiscaux = centreDesServicesFiscauxService.save(centreDesServicesFiscaux);
        if(centreDesServicesFiscaux == null){
            log.info("CentreDesServicesFiscauxrControllers:ce code existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(DtoConverter.convertToDto(centreDesServicesFiscaux));
        }
        log.info("CentreDesServicesFiscauxrControllers:CentreDesServicesFiscaux créé");
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(centreDesServicesFiscaux));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CentreDesServicesFiscauxDto> deleteCentreDesServicesFiscaux(@PathVariable Long id,
                                                                   HttpServletRequest request) {
        centreDesServicesFiscauxService.delete(id);
        log.warn("CentreDesServicesFiscauxrControllers: CentreDesServicesFiscauxr supprimé. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

