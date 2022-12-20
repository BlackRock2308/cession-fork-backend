package sn.modelsis.cdmp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.modelsis.cdmp.entities.MinistereDepensier;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.MinistereDepensierDto;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.services.MinistereDepensierService;
import sn.modelsis.cdmp.util.DtoConverter;

/**
 * @author SNDIAGNEF
 * REST Controller to handle Ministere
 */
@RestController
@RequestMapping("/api/ministeres")
public class MinistereDepensierControllers {

  private final Logger log = LoggerFactory.getLogger(MinistereDepensierControllers.class);

  @Autowired
  private MinistereDepensierService ministereService;

  @GetMapping
  public ResponseEntity<List<MinistereDepensierDto>> getAllMinistereDepensiers(HttpServletRequest request) {
    List<MinistereDepensier> ministeres = ministereService.findAll();
    log.info("All MinistereDepensiers .");
    return ResponseEntity.status(HttpStatus.OK)
        .body(ministeres.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

  }
  
  @GetMapping("/{code}")
  public ResponseEntity<MinistereDepensierDto> getMinisterByCode(@PathVariable String code) {
      log.debug("REST request to get MinistereDepensier : {}", code);
      MinistereDepensier md = ministereService.findByCode(code);
      return  ResponseEntity.ok().body(DtoConverter.convertToDto(md));
  }

}

