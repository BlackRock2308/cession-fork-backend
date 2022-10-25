package sn.modelsis.cdmp.controllers;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import sn.modelsis.cdmp.services.DetailPaiementService;
import sn.modelsis.cdmp.util.DtoConverter;

@RestController
@RequestMapping("/api/detailsPaiement")
public class DetailPaiementController {

    private final Logger log = LoggerFactory.getLogger(DetailPaiementController.class);

    @Autowired
    DetailPaiementService detailPaiementService;

    @PostMapping()
    public ResponseEntity<DetailPaiement> addDetailPaiement(@RequestBody DetailPaiement detailPaiement,
                                                       HttpServletRequest request) {
        DetailPaiement saveDetailPaiement = detailPaiementService.save(detailPaiement);
        detailPaiementService.save(saveDetailPaiement);
        log.info("Paiement created : ", saveDetailPaiement.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(saveDetailPaiement);
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
}
