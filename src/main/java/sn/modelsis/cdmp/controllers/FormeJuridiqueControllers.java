package sn.modelsis.cdmp.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.FormeJuridique;
import sn.modelsis.cdmp.entitiesDtos.FormeJuridiqueDto;
import sn.modelsis.cdmp.services.FormeJuridiqueService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SNDIAGNEF
 * REST Controller to handle Ministere
 */
@RestController
@RequestMapping("/api/formesjuridiques")
@RequiredArgsConstructor
public class FormeJuridiqueControllers {

    private final Logger log = LoggerFactory.getLogger(FormeJuridiqueControllers.class);


    private final FormeJuridiqueService formeJuridiqueService;

    @GetMapping
    public ResponseEntity<List<FormeJuridiqueDto>> getAllFormeJuridiques(HttpServletRequest request) {
        List<FormeJuridique> ministeres = formeJuridiqueService.findAll();
        log.info("All formeJuridiques .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(ministeres.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

    }

    @GetMapping("/{code}")
    public ResponseEntity<FormeJuridiqueDto> getFormeJuridiqueByCode(@PathVariable String code) {
        log.debug("REST request to get formeJuridique : {}", code);
        FormeJuridique fj = formeJuridiqueService.findByCode(code);
        return  ResponseEntity.ok().body(DtoConverter.convertToDto(fj));
    }

    @PutMapping()
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict duplicated code")})
    public ResponseEntity<FormeJuridiqueDto> updateFormeJuridique(@RequestBody FormeJuridiqueDto formeJuridiqueDto,
                                                                    HttpServletRequest request) {
        FormeJuridique formeJuridique = DtoConverter.convertToEntity(formeJuridiqueDto);
        formeJuridique = formeJuridiqueService.save(formeJuridique);
        if(formeJuridique == null){
            log.info("FormeJuridiqueControllers:ce code existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(DtoConverter.convertToDto(formeJuridique));
        }
        log.info("FormeJuridiqueControllers:formeJuridique modifiée");
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(formeJuridique));
    }

    @PostMapping()
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict duplicated code")})
    public ResponseEntity<FormeJuridiqueDto> addFormeJuridique(@RequestBody FormeJuridiqueDto formeJuridiqueDto,
                                                                 HttpServletRequest request) {
        FormeJuridique formeJuridique = DtoConverter.convertToEntity(formeJuridiqueDto);
        formeJuridique = formeJuridiqueService.save(formeJuridique);
        if(formeJuridique == null){
            log.info("FormeJuridiqueControllers:ce code existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(DtoConverter.convertToDto(formeJuridique));
        }
        log.info("FormeJuridiquerControllers:FormeJuridique créée");
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(formeJuridique));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FormeJuridiqueDto> deleteFormeJuridique(@PathVariable Long id,
                                                          HttpServletRequest request) {
        formeJuridiqueService.delete(id);
        log.warn("FormeJuridiquerControllers: FormeJuridique supprimée. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

