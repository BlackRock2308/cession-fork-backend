package sn.modelsis.cdmp.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.util.DtoConverter;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/bonEngagement")
@Slf4j
public class BonEngagementControllers {
    private final BonEngagementService bonEngagementService;

    @PostMapping()
    public ResponseEntity<BonEngagementDto> addBonEngagement(@RequestBody BonEngagementDto bonEngagementDto,
            HttpServletRequest request) {
        log.info("BonEngagementControllers.addBonEngagement request started ...");
        BonEngagement bonEngagement = DtoConverter.convertToEntity(bonEngagementDto);
        BonEngagement result = bonEngagementService.save(bonEngagement);
        log.info("BonEngagement create. Id:{} ", result.getIdBonEngagement());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BonEngagementDto> updateBonEngagement(@RequestBody BonEngagementDto bonEngagementDto,
            HttpServletRequest request) {
        log.info("BonEngagementControllers.updateBonEngagement request started ...");
        BonEngagement bonEngagement = DtoConverter.convertToEntity(bonEngagementDto);
        BonEngagement result = bonEngagementService.save(bonEngagement);
        log.info("BonEngagement updated. Id:{}", result.getIdBonEngagement());
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
    }

    @GetMapping
    public ResponseEntity<List<BonEngagementDto>> getAllBonEngagement(HttpServletRequest request) {
        log.info("BonEngagementControllers.getAllBonEngagement request started ...");

        List<BonEngagement> bonEngagementList = bonEngagementService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(bonEngagementList.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BonEngagementDto> getBonEngagement(@PathVariable Long id,
                                                             HttpServletRequest request) {
        log.info("BonEngagementControllers.getBonEngagement request started ...");
        BonEngagement bonEngagement = bonEngagementService.getBonEngagement(id).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(bonEngagement));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BonEngagementDto> deleteBonEngagement(@PathVariable Long id,
                                                                HttpServletRequest request) {
        log.info("BonEngagementControllers.deleteBonEngagement request started ...");
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
