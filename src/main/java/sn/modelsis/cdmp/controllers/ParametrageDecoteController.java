package sn.modelsis.cdmp.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.mappers.DecodeMapper;
import sn.modelsis.cdmp.services.ParametrageDecoteService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;

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
        ParametrageDecoteDTO result = decoteService.createNewDecote(parametrageDecoteDTO);
        log.info("ParametrageDecoteController:saveNewDecote request params : {}", parametrageDecoteDTO);
        //ParametrageDecote parametrageDecote = decodeMapper.asEntity(parametrageDecoteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
