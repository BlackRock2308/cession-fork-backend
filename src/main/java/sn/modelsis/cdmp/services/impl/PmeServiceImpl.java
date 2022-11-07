package sn.modelsis.cdmp.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.ItemExistsException;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.ExceptionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PmeServiceImpl implements PmeService {
  private final PmeRepository pmeRepository;
  private final DocumentService documentService;

  private final StatutRepository statutRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Pme savePme(Pme pme) {

    Pme newPme;

    try {
      log.info("PmeService:savePme request started");
      Optional<Pme> optional = pmeRepository.findByNinea(pme.getNinea());
      ExceptionUtils.absentOrThrow(optional, ItemExistsException.NINEA_PME_EXIST, pme.getNinea());

      optional = pmeRepository.findByRccm(pme.getRccm());
      ExceptionUtils.absentOrThrow(optional, ItemExistsException.RCCM_EXIST, pme.getRccm());

       //optional = pmeRepository.findByMail(pme.getEmail());
       //ExceptionUtils.absentOrThrow(optional, ItemExistsException.MAIL_EXISTS, pme.getEmail());

      optional = pmeRepository.findByPhone(pme.getTelephonePME());
      ExceptionUtils.absentOrThrow(optional, ItemExistsException.PHONE_EXISTS, pme.getTelephonePME());

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
    return pmeRepository
            .findAll()
            .stream()
            .collect(Collectors.toList());
  }

  @Override
  public Pme findPmeByEmail(String email) {
    return pmeRepository.findByMail(email);
  }

  @Override
  public Optional<Pme> getPme(Long id) {
    Optional<Pme> optional;
    try {
      log.info("PmeService:getPme started request");
      optional = pmeRepository.findById(id);

    } catch(Exception ex){
      throw new CustomException("Error, can't find PME with id ");
    }
    return optional;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deletePme(Long id) {
    try {
      log.info("PmeService:deletePme started request");
      Optional<Pme> optional = pmeRepository.findById(id);
      pmeRepository.deleteById(optional.get().getIdPME());
    } catch(Exception ex){
      throw new CustomException("Error, can't find PME with id ");
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Pme updatePme(Long id, Pme pme) {
    Optional <Pme> existingPme;
    try {
      log.info("PmeService:updatePme started request");
      existingPme = pmeRepository.findById(id);

      existingPme.get().setAdressePME(pme.getAdressePME());
      existingPme.get().setActivitePrincipale(pme.getActivitePrincipale());
      existingPme.get().setFormeJuridique(pme.getFormeJuridique());
      existingPme.get().setEffectifPermanent(pme.getEffectifPermanent());
      existingPme.get().setEmail(pme.getEmail());
      existingPme.get().setTelephonePME(pme.getTelephonePME());
      existingPme.get().setLocalite(pme.getLocalite());

      pmeRepository.saveAndFlush(existingPme.get());
      log.info("PmeService:updatePme update Pme in the database with id = {}",existingPme.get().getIdPME());
    } catch(Exception ex){
      throw new CustomException("Error, can't find and update PME with id ");
    }
    return existingPme.get();
  }

  
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Optional<Pme> upload(Long id, MultipartFile file, TypeDocument type)
      throws IOException {
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
      log.info("PmeService:getPme started request");
      optional = pmeRepository.findPmeByUtilisateurIdUtilisateur(id);

    } catch(Exception ex){
      throw new CustomException("Error, can't find PME with id user ");
    }
    return optional;
  }
}
