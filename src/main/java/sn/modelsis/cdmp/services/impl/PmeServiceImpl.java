package sn.modelsis.cdmp.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.services.PmeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PmeServiceImpl implements PmeService {
  private final PmeRepository pmeRepository;
  private final StatutRepository statutRepository;
  private final DemandeCessionRepository demandecessionRepository;
  private final UtilisateurRepository utilisateurRepository;
  private final DocumentService documentService;


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Pme savePme(Pme pme) {

    Pme newPme;

    try {
      log.info("PmeService:savePme , saving.....");
      newPme = pmeRepository.saveAndFlush(pme);
      log.debug("PmeService:savePme received from database : {}",newPme);

    } catch (Exception ex){
      log.error("Exception occured while persisting new Pme to database : {}",ex.getMessage());
      throw new CustomException("Exception occured while creating a new Pme ");
    }
    return  newPme;
  }


  @Override
  public List<Pme> findAllPme() {
    log.info("PmeService:findAllPme fetching ......");
    return new ArrayList<>(pmeRepository
            .findAll());
  }

  @Override
  public Pme findPmeByEmail(String email) {
    return pmeRepository.findByMail(email);
  }

  @Override
  public Optional<Pme> getPme(Long id) {
    Optional<Pme> optional;
    try {
      log.info("PmeService:getPme fetching Pme with id : {}......", id);
      optional = pmeRepository.findById(id);
      log.debug("PmeService:getPme request params : {}",id);
    } catch(Exception ex){
      log.error("Exception occured while getting PME: {}",id);
      throw new CustomException("Error, can't find PME with id ");
    }
    return optional;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deletePme(Long id) {
    try {
      log.info("PmeService:deletePme with id : {}", id);
      Optional<Pme> optional = pmeRepository.findById(id);
      log.debug("PmeService:deletePme request params : {}",id);
      pmeRepository.deleteById(optional.get().getIdPME());
    } catch(Exception ex){
      log.error("Exception occured while deleting PME: {}",id);
      throw new CustomException("Error, can't find PME with id ");
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Pme updatePme(Long id, Pme pme) {

    try {
      log.info("PmeService:updatePme ........");
      log.info("PmeService:updatePme update Pme in the database with id = {}");
      if (pme.getUtilisateurid()!=null){
        pme.setUtilisateur(utilisateurRepository.findById(pme.getUtilisateurid()).orElse(null));
      }else
        log.info("Le pme n'a pas d'utilisateur ????");
      return pmeRepository.saveAndFlush(pme);
    } catch(Exception ex){
      log.error("Exception occured while updating PME with id : {}",id );
      throw new CustomException("Error occured while updating this PME ");
    }
  }

  
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Optional<Pme> upload(Long id, MultipartFile file, TypeDocument type)
      throws IOException {
    log.info("PmeService:upload ");
    Optional<Pme> pme = pmeRepository.findById(id);
    if (pme.isPresent()) {

      PMEDocuments doc = (PMEDocuments) documentService.upload(file, id,
              PMEDocuments.PROVENANCE, type);
      pme.get().getDocuments().add(doc);

      return Optional.of(pmeRepository.save(pme.get()));

    }
    return pme;
  }

  @Override
  public Optional<Pme> getPmeByUtilisateur(Long id) {
    Optional<Pme> optional;
    try {
      log.info("PmeService:getPmeByUtilisateur ...... ");
      optional = pmeRepository.findPmeByUtilisateurIdUtilisateur(id);

    } catch(Exception ex){
      throw new CustomException("Error, can't find PME with id user ");
    }
    return optional;
  }

  /* Methode permettant á la PME de changer le statut de sa demande de Cession en complété*/

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public DemandeCession complementerDemandeCession(Long idDemande ){
    log.info("DemandeCessionService:complementerDemandeCession request started...");
    Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
    log.debug("DemandeCessionService:complementerDemandeCession request params {}", idDemande);
    Statut updatedStatut = statutRepository.findByLibelle("COMPLETEE");
    optional.get().setStatut(updatedStatut);

    DemandeCession demandeCessionDto = demandecessionRepository.save(optional.get());
    log.info("DemandeCessionService:complementerDemandeCession received from Database {}", demandeCessionDto.getIdDemande());
    return demandeCessionDto;

  }

}
