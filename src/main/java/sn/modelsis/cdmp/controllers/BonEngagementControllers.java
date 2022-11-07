package sn.modelsis.cdmp.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/bonEngagement")
public class BonEngagementControllers {
    private final Logger log = LoggerFactory.getLogger(BonEngagementControllers.class);
    private final BonEngagementService bonEngagementService;

    @PostMapping()
    public ResponseEntity<BonEngagementDto> addBonEngagement(@RequestBody BonEngagementDto bonEngagementDto,
            HttpServletRequest request) {
        BonEngagement bonEngagement = DtoConverter.convertToEntity(bonEngagementDto);
        BonEngagement result = bonEngagementService.save(bonEngagement);
        log.info("BonEngagement create. Id:{} ", result.getIdBonEngagement());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BonEngagementDto> updateBonEngagement(@RequestBody BonEngagementDto bonEngagementDto,
            HttpServletRequest request) {
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
    public ResponseEntity<BonEngagementDto> getBonEngagement(@PathVariable Long id, HttpServletRequest request) {
        BonEngagement bonEngagement = bonEngagementService.getBonEngagement(id).orElse(null);
        log.info("BonEngagement . Id:{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(bonEngagement));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BonEngagementDto> deleteBonEngagement(@PathVariable Long id, HttpServletRequest request) {
        bonEngagementService.delete(id);
        log.warn("BonEngagement deleted. Id:{}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PostMapping("/{id}/upload")
    @Operation(summary = "Upload file", description = "Upload a new file for BE")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<BonEngagementDto> addDocument(@PathVariable Long id,
        @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {

      Optional<BonEngagement> be = null;
      try {
        be = bonEngagementService.upload(id, file, TypeDocument.valueOf(type));
      } catch (IOException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if (be.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
      log.info("Document added. Id:{} ", be.get().getIdBonEngagement());
      return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(be.get()));
    }
}
