package sn.modelsis.cdmp.controllers;


import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.ParametrageDecote;
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
    public ResponseEntity<ParametrageDecoteDTO> saveNewDecote(@RequestBody ParametrageDecoteDTO parametrageDecoteDTO,
                                                              HttpServletRequest request) {
        log.info("ParametrageDecoteController:saveNewDecote request started");
        ParametrageDecote decoteDTO = decodeMapper.asEntity(parametrageDecoteDTO);

        log.info("ParametrageDecoteController:saveNewDecote request params : {}", parametrageDecoteDTO);

        ParametrageDecote result = decoteService.createNewDecote(decoteDTO);
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


    @PutMapping(value = "/{id}")
    public ResponseEntity<ParametrageDecoteDTO> updateDecote(@RequestBody ParametrageDecoteDTO decoteDTO,
                                               @PathVariable("id") Long id ,
                                            HttpServletRequest request) {
        log.info("ParametrageDecoteController:updateDecote request started");
        ParametrageDecote parametrageDecote = decodeMapper.asEntity(decoteDTO);
        log.info("ParametrageDecoteController:updateDecote Started with request params id={}", id);
        ParametrageDecote result = decoteService.updateDecoteParameter(id,parametrageDecote);
        log.info("ParametrageDecoteController:updateDecote updated with id = {} ", result.getIdDecote());
        return ResponseEntity.status(HttpStatus.OK).body(decodeMapper.asDTO(result));
    }

}