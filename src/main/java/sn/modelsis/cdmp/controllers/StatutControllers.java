package sn.modelsis.cdmp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entitiesDtos.StatutDto;
import sn.modelsis.cdmp.services.StatutService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statuts")
public class StatutControllers {

    private final Logger log = LoggerFactory.getLogger(StatutControllers.class);
    @Autowired
    private StatutService statutService;

    @PostMapping()
    public ResponseEntity<StatutDto> addStatut(@RequestBody StatutDto statutDto, HttpServletRequest request) {
        Statut statut = DtoConverter.convertToEntity(statutDto);
        Statut result = statutService.save(statut);
        log.info("statut create. Id:{} ", result.getIdStatut());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StatutDto> updateStatut(@RequestBody StatutDto statutDto, HttpServletRequest request) {
        Statut statut = DtoConverter.convertToEntity(statutDto);
        Statut result = statutService.save(statut);
        log.info("Statut updated. Id:{}", result.getIdStatut());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
    }

    @GetMapping
    public ResponseEntity<List<StatutDto>> getAllStatut(HttpServletRequest request) {
        List<Statut> statutList = statutService.findAll();
        log.info("All statut .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(statutList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StatutDto> getStatut(@PathVariable Long id,HttpServletRequest request) {
        Statut statut = statutService.getStatut(id).orElse(null);
        log.info("Statut . Id:{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(statut));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatutDto> deleteStatut(@PathVariable Long id, HttpServletRequest request) {
        statutService.delete(id);
        log.warn("Statut deleted. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
