package sn.modelsis.cdmp.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.util.DtoConverter;

public class DetailPaiementController {
    
   // @Autowired
//    private De
//    
//    @PostMapping("/{id}/upload")
//    @Operation(summary = "Upload file", description = "Upload a new file for Convention")
//    @ApiResponses({@ApiResponse(responseCode = "201", description = "Success"),
//        @ApiResponse(responseCode = "400", description = "Bad request")})
//    public ResponseEntity<ConventionDto> addDocument(@PathVariable Long id,
//        @RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) {
//
//      Optional<Convention> be = null;
//      try {
//        be = conventionService.upload(id, file, TypeDocument.valueOf(type));
//      } catch (IOException e) {
//        log.error(e.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//      }
//      if (be.isEmpty()) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//      }
//      log.info("Document added. Id:{} ", be.get().getIdConvention());
//      return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(be.get()));
//    }
}
