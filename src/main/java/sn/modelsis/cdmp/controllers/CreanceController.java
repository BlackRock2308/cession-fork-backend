package sn.modelsis.cdmp.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entitiesDtos.CreanceDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.mappers.CreanceMapper;
import sn.modelsis.cdmp.services.DemandeCessionService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/creances")
@Slf4j
public class CreanceController {

    private final DemandeCessionService demandeCessionService;
    private final CreanceMapper creanceMapper;

    @GetMapping
    public ResponseEntity<Page<CreanceDto>> findAllCreance(Pageable pageable,
                                                                 HttpServletRequest request) {
        log.info("CreanceController.CreanceController request started ...");
        Page<DemandeCessionDto> demandeList = demandeCessionService
                .findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.map(creanceMapper::mapToDto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<CreanceDto>> getCreance(@PathVariable Long id,
                                                    HttpServletRequest request) {
        log.info("CreanceController.getCreance request started ...");
        Optional<CreanceDto> creanceDto = demandeCessionService
                    .getDemandeCession(id)
                    .map(creanceMapper::mapToDto);
        log.debug("CreanceController.getCreance request params : {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(creanceDto);
    }
}
