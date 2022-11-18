package sn.modelsis.cdmp.controllers;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sn.modelsis.cdmp.entities.DetailPaiement;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DetailPaiementDto;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.services.DetailPaiementService;
import sn.modelsis.cdmp.services.PaiementService;
import sn.modelsis.cdmp.util.DtoConverter;

@RestController
@RequestMapping("/api/detailsPaiements")
public class DetailPaiementController {

    private final Logger log = LoggerFactory.getLogger(DetailPaiementController.class);


   final private DetailPaiementService detailPaiementService;
   final private PaiementService paiementService;

    public DetailPaiementController(DetailPaiementService detailPaiementService, PaiementService paiementService) {
        this.detailPaiementService = detailPaiementService;
        this.paiementService = paiementService;
    }

    @PostMapping(value = "cdmp-pme")
    public ResponseEntity<DetailPaiementDto> addDetailPaiementPME(@RequestBody DetailPaiementDto detailPaiementDto,
                                                            HttpServletRequest request) {
        log.info("paiement:{} ",detailPaiementDto.getPaiementDto().getDetailPaiements());
        DetailPaiement detailPaiement=DtoConverter.convertToEntity(detailPaiementDto);
        log.info("paiement0:{} ",detailPaiement.getPaiement().getIdPaiement());
        DetailPaiement result = detailPaiementService.paiementPME(detailPaiement);
        log.info("DetailPaiement create. Id:{} ");
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @PostMapping(value = "sica-cdmp")
    public ResponseEntity<DetailPaiementDto> addDetailPaiementCDMP(@RequestBody DetailPaiementDto detailPaiementDto,
                                                            HttpServletRequest request) {
        log.info("paiement:{} ",detailPaiementDto.getPaiementDto().getDetailPaiements());
        LocalDateTime date = LocalDateTime.now();
        DetailPaiement detailPaiement=DtoConverter.convertToEntity(detailPaiementDto);
        log.info("paiement0:{} ",detailPaiement.getPaiement().getIdPaiement());
        detailPaiement.setDatePaiement(date);
        DetailPaiement result = detailPaiementService.paiementCDMP(detailPaiement);
        log.info("DetailPaiement create. Id:{} ");
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }
/*
    @PostMapping(value = "cdmp")
    public ResponseEntity<DetailPaiementDto> addDetailPaiementCDMP(@RequestBody DetailPaiement detailPaiement,
                                                               HttpServletRequest request) {
        DetailPaiement result = detailPaiementService.paiementCDMP(detailPaiement);
        log.info("DetailPaiement create. Id:{} ", result.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }


 */
    /*@PutMapping()
    public ResponseEntity<DetailPaiementDto> updateDetailPaiement(@RequestBody DetailPaiementDto detailPaiementDto,
                                                          HttpServletRequest request) {
        DetailPaiement detailPaiement = DtoConverter.convertToEntity(detailPaiementDto);
        DetailPaiement result = detailPaiementService.save(detailPaiement);
        log.info("DetailPaiement updated. Id:{}", result.getId());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
    }

     */

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
    
    @PostMapping("/{id}/upload")
    @Operation(summary = "Upload file", description = "Upload a new file for BE")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<DetailPaiementDto> addDocument(@PathVariable Long id,
        @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

      Optional<DetailPaiement> be = null;
      try {
        be = detailPaiementService.upload(id, file, TypeDocument.valueOf(type));
      } catch (IOException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if (be.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
      log.info("Document added. Id:{} ", be.get().getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(be.get()));
    }

    @GetMapping(value = "/sica-cdmp/{id}")
    public ResponseEntity<Set<DetailPaiementDto>> getDetailPaiementSICA_CDMP(
            @PathVariable Long id,
            HttpServletRequest request) {
        Set<DetailPaiementDto> sortedDetailPaiements =  detailPaiementService.getDetailPaiementByTypePaiement(id,"SICA_CDMP");
        log.info("Paiement . demandeId:{}");
        return ResponseEntity.status(HttpStatus.OK).body(sortedDetailPaiements);
    }

    @GetMapping(value = "/cdmp-pme/{id}")
    public ResponseEntity<Set<DetailPaiementDto>> getDetailPaiementCDMP_PME(
            @PathVariable Long id) {
        Set<DetailPaiementDto> sortedDetailPaiements =  detailPaiementService.getDetailPaiementByTypePaiement(id,"CDMP_PME");
        log.info("Paiement . demandeId:{}");
        return ResponseEntity.status(HttpStatus.OK).body(sortedDetailPaiements);
    }
}
