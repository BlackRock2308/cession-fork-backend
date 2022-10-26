package sn.modelsis.cdmp.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.CreanceDto;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/creances")
public class CreanceController {

    private final Logger log = LoggerFactory.getLogger(CreanceController.class);

    private final DemandeCessionService demandeCessionService;

    @GetMapping
    public ResponseEntity<List<CreanceDto>> getAllDemandeCession(HttpServletRequest request) {
        List<DemandeCession> demandeList = demandeCessionService.findAll();
        log.info("Fetching list of creances : ....");
        return ResponseEntity.status(HttpStatus.OK)
                .body(demandeList.stream().map(DtoConverter::convertToCreanceDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CreanceDto> getCreance(
            @PathVariable Long id,
            HttpServletRequest request) {
        DemandeCession demandeCession = demandeCessionService.getDemandeCession(id).orElse(null);
        log.info("Getting Creance with Id = {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToCreanceDto(demandeCession));
    }


}
