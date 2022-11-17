package sn.modelsis.cdmp.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import sn.modelsis.cdmp.entities.Documents;
import sn.modelsis.cdmp.entitiesDtos.DocumentDto;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.util.DtoConverter;

/**
 * REST Controller to handle {@link <Document>}
 */
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentControllers {

  private final Logger log = LoggerFactory.getLogger(DocumentControllers.class);

  private final DocumentService documentService;

  @PostMapping()
  @Operation(summary = "Create  document", description = "Create a new  document")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "Created"),
      @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<DocumentDto> addDocument(@RequestBody DocumentDto documentDto,
      HttpServletRequest request) {
    Documents document = DtoConverter.convertToEntity(documentDto);
    Documents result = documentService.save(document);
    log.info("Document create. Id:{} ", result.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

  @PutMapping()
  @Operation(summary = "Update  document", description = "Update an existing  document")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "404", description = "Not found")})
  public ResponseEntity<DocumentDto> updateDocument(@RequestBody DocumentDto documentDto,
      HttpServletRequest request) {
    Documents document = DtoConverter.convertToEntity(documentDto);
    Documents result = documentService.save(document);
    log.info("Document updated. Id:{}", result.getId());
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
  }

  @GetMapping
  @Operation(summary = "Find all  documents", description = "Retrieve a collection of  documents")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "500", description = "Internal server error")})
  public ResponseEntity<List<DocumentDto>> getDocuments(
      HttpServletRequest request) {
    List<Documents> documents = documentService.findAll();
    log.info("All documents .");
    return ResponseEntity.status(HttpStatus.OK)
            .body(documents
            .stream()
            .map(DtoConverter::convertToDto)
             .collect(Collectors.toList()));
  }

  @GetMapping(value = "/{id}/file")
  @Operation(summary = "Find  document by id", description = "Retrieve a  document by id")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "Not found")})
  public ResponseEntity<Resource> getDocument(@PathVariable Long id, HttpServletRequest request) {
    Resource resource = documentService.loadFile(id);
    String contentType = null;
    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {
      log.info("Could not determine file type.");
    }
    if (contentType == null) {
      contentType = "application/octet-stream";
    }
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }

  @GetMapping(value = "/file")
  @Operation(summary = "Find  document by path", description = "Retrieve a  document by path")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "Not found")})
  public ResponseEntity<Resource> getDocument(
      @RequestParam(value = "path", required = true, defaultValue = "") String path,
      HttpServletRequest request) {
    Resource resource = documentService.loadFile(path);
    String contentType = null;
    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {
      log.info("Could not determine file type.");
    }
    if (contentType == null) {
      contentType = "application/octet-stream";
    }
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }

  @DeleteMapping(value = "/{id}")
  @Operation(summary = "Delete  document", description = "Delete an existing  document")
  @ApiResponses({@ApiResponse(responseCode = "204", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "404", description = "Not found")})
  public ResponseEntity<DocumentDto> deleteDocument(@PathVariable Long id,
      HttpServletRequest request) {
    documentService.delete(id);
    log.warn("Document deleted. Id:{}", id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

