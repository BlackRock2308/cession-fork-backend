package sn.modelsis.cdmp.controllers;


import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.FormeJuridiqueDto;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;
import sn.modelsis.cdmp.mappers.DecodeMapper;
import sn.modelsis.cdmp.services.ParametrageDecoteService;

@RestController
@RequestMapping("api/decote")
@RequiredArgsConstructor
@Slf4j
public class ParametrageDecoteController {

    private final ParametrageDecoteService decoteService;

    private final DecodeMapper decodeMapper;

    @PostMapping()
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict duplicated code")})
    public ResponseEntity<ParametrageDecoteDTO> saveNewDecote(@RequestBody ParametrageDecoteDTO parametrageDecoteDTO,
                                                              HttpServletRequest request) {
        log.info("ParametrageDecoteController:saveNewDecote request started");
        ParametrageDecote decoteDTO = decodeMapper.asEntity(parametrageDecoteDTO);

       ParametrageDecote result = decoteService.createNewDecote(decoteDTO);
        if(result == null){
            log.info("ParametrageDecoteController: conflit sur les bornes");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(decodeMapper.asDTO(result));
        }
        log.info("ParametrageDecoteController:saveNewDecote request params : {}", parametrageDecoteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(decodeMapper.asDTO(result));
    }

    @GetMapping
    public ResponseEntity<List<ParametrageDecoteDTO>> findAllDecote(
                                                              HttpServletRequest request) {
        log.info("ParametrageDecoteController.findAllDecote request started ...");
        List<ParametrageDecote> decoteList = decoteService.getAllDecode();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(decoteList.stream().map(decodeMapper::asDTO).collect(Collectors.toList()));
    }


    @PutMapping
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict duplicated code")})
    public ResponseEntity<ParametrageDecoteDTO> updateDecote(@RequestBody ParametrageDecoteDTO decoteDTO,
                                            HttpServletRequest request) {
        log.info("ParametrageDecoteController:updateDecote request started");
        ParametrageDecote parametrageDecote = decodeMapper.asEntity(decoteDTO);
        log.info("ParametrageDecoteController:updateDecote Started with request params id={}", decoteDTO.getIdDecote());
        ParametrageDecote result = decoteService.updateDecoteParameter(parametrageDecote);
        if(result == null){
            log.info("ParametrageDecoteController: conflit sur les bornes");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(decodeMapper.asDTO(result));
        }
        log.info("ParametrageDecoteController:updateDecote updated with id = {} ", result.getIdDecote());
        return ResponseEntity.status(HttpStatus.OK).body(decodeMapper.asDTO(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FormeJuridiqueDto> deleteDecote(@PathVariable Long id,
                                                                  HttpServletRequest request) {
        decoteService.deleteDecoteParameter(id);
        log.warn("ParametrageDecoteController: décote supprimée. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}