package sn.modelsis.cdmp.controllers;


import sn.modelsis.cdmp.util.DtoConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.DetailPaiement;
import sn.modelsis.cdmp.entitiesDtos.DetailPaiementDto;
import sn.modelsis.cdmp.services.DetailPaiementService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detailsPaiement")
public class DetailPaiementController {

    private final Logger log = LoggerFactory.getLogger(DetailPaiementController.class);

    @Autowired
    DetailPaiementService detailPaiementService;

    @PostMapping()
    public ResponseEntity<DetailPaiementDto> addDetailPaiement(@RequestBody DetailPaiementDto detailPaiementDto,
                                                       HttpServletRequest request) {
        DetailPaiement detailPaiement=DtoConverter.convertToEntity(detailPaiementDto);
        DetailPaiement result = detailPaiementService.save(detailPaiement);
        log.info("DetailPaiement create. Id:{} ", result.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @PutMapping()
    public ResponseEntity<DetailPaiementDto> updateDetailPaiement(@RequestBody DetailPaiementDto detailPaiementDto,
                                                          HttpServletRequest request) {
        DetailPaiement detailPaiement = DtoConverter.convertToEntity(detailPaiementDto);
        DetailPaiement result = detailPaiementService.save(detailPaiement);
        log.info("DetailPaiement updated. Id:{}", result.getId());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
    }

    @GetMapping
    public ResponseEntity<List<DetailPaiementDto>> getAllDetailPaiements(
            HttpServletRequest request) {
        List<DetailPaiement> detailPaiements =
                detailPaiementService.findAll();
        log.info("All detailPaiements .");
        return ResponseEntity.status(HttpStatus.OK).body(detailPaiements.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DetailPaiementDto> getDetailPaiement(
            @PathVariable Long id,
            HttpServletRequest request) {
        DetailPaiement detailPaiement = detailPaiementService.getDetailPaiement(id).orElse(null);
        log.info("DetailPaiement . Id:{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(detailPaiement));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DetailPaiementDto> deleteDetailPaiement(
            @PathVariable Long id,
            HttpServletRequest request) {
        detailPaiementService.delete(id);
        log.warn("DetailPaiement deleted. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
