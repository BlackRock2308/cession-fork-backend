package sn.modelsis.cdmp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.Util.DtoConverter;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.services.BonEngagementService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bonEngagement")
public class BonEngagementControllers {
    private final Logger log = LoggerFactory.getLogger(BonEngagementControllers.class);

    @Autowired
    private BonEngagementService bonEngagementService;

    @PostMapping()
    public ResponseEntity<BonEngagementDto> addBonEngagement(@RequestBody BonEngagementDto bonEngagementDto, HttpServletRequest request) {
        BonEngagement bonEngagement = DtoConverter.convertToEntity(bonEngagementDto);
        BonEngagement result = bonEngagementService.save(bonEngagement);
        log.info("BonEngagement create. Id:{} ", result.getIdBonEngagement());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<BonEngagementDto> updateBonEngagement(@RequestBody BonEngagementDto bonEngagementDto, HttpServletRequest request) {
        BonEngagement bonEngagement = DtoConverter.convertToEntity(bonEngagementDto);
        BonEngagement result = bonEngagementService.save(bonEngagement);
        log.info("BonEngagement updated. Id:{}", result.getIdBonEngagement());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
    }

    @GetMapping
    public ResponseEntity<List<BonEngagementDto>> getAllBonEngagement(HttpServletRequest request) {
        List<BonEngagement> bonEngagementList = bonEngagementService.findAll();
        log.info("All BonEngagement .");
        return ResponseEntity.status(HttpStatus.OK)
                .body(bonEngagementList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BonEngagementDto> getBonEngagement(@PathVariable Long id,HttpServletRequest request) {
        BonEngagement bonEngagement = bonEngagementService.getBonEngagement(id).orElse(null);
        log.info("BonEngagement . Id:{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(bonEngagement));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BonEngagementDto> deleteBonEngagement(@PathVariable Long id, HttpServletRequest request) {
        bonEngagementService.delete(id);
        log.warn("BonEngagement deleted. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }}
