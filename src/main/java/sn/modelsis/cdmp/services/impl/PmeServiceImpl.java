package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.DemandeAdhesionRepository;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.Util;

@Service
@RequiredArgsConstructor
@Slf4j
public class PmeServiceImpl implements PmeService {
  private final PmeRepository pmeRepository;
  private final StatutRepository statutRepository;
  private final DemandeCessionRepository demandecessionRepository;
  private final UtilisateurRepository utilisateurRepository;
  private final DocumentService documentService;
  @Autowired
  private DemandeAdhesionRepository demandeAdhesionRepository;

  private final Util util;


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Pme savePme(Pme pme) {

   Pme newPme;
    Pme oldpme;

    try {
        oldpme = pmeRepository.findByMail(pme.getEmail());
        if(oldpme == null) {
            newPme = pmeRepository.saveAndFlush(pme);
            log.debug("PmeService:savePme received from database : {}",newPme);
        }else {
            List<DemandeAdhesion> demandes = demandeAdhesionRepository.findAllByPmeIdPME(oldpme.getIdPME());
            demandes.forEach(d -> {
                String code = d.getStatut().getCode();
                if(code == "ADHESION_SOUMISE") {
                    throw new CustomException("Vous avez déjà une demande en cours d'exécution ");
                }
                else if(code == "ADHESION_ACCEPTEE") {
                    throw new CustomException("Vous avez déjà un compte ");
                }
                else if(code == "ADHESION_REJETEE") {
                    pmeRepository.delete(oldpme);
                    pmeRepository.save(pme);
                }
            });
            newPme = pmeRepository.findByMail(pme.getEmail());
        }

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
      Pme returnPme=new Pme();
      Map<String,Object> fields=this.util.mergeObjects(pme,pmeRepository.findById(id).orElse(null));
      returnPme.setPME((Long) fields.get("idPME"), (String) fields.get("prenomRepresentant"),
              (String) fields.get("nomRepresentant"), (String) fields.get("rccm"), (String) fields.get("adressePME"),
              (String) fields.get("telephonePME"), (LocalDateTime) fields.get("dateImmatriculation"),
              (CentreDesServicesFiscaux) fields.get("centreFiscal"), (String) fields.get("ninea"), (String) fields.get("raisonSocial"),
              (boolean) fields.get("atd"), (boolean) fields.get("nantissement"),
              (boolean) fields.get("interdictionBancaire"), (boolean) fields.get("identificationBudgetaire"),
              (FormeJuridique) fields.get("formeJuridique"), (String) fields.get("email"),
              (String) fields.get("enseigne"),
              (String) fields.get("localite"), (Integer) fields.get("controle"),
              (String) fields.get("activitePrincipale"), (String) fields.get("autorisationMinisterielle"),
              (LocalDateTime) fields.get("dateCreation"), (Long) fields.get("capitalSocial"),
              (Long) fields.get("chiffresDaffaires"), (Integer) fields.get("effectifPermanent"),
              (Integer) fields.get("nombreEtablissementSecondaires"), (Boolean) fields.get("hasninea"),
              (Boolean) fields.get("isactive"), (Set<Demande>) fields.get("demandes"),
              (Set<PMEDocuments>) fields.get("documents"), (Utilisateur) fields.get("utilisateur"),
              (String) fields.get("cniRepresentant"), (String) fields.get("registre"),
              (Long) fields.get("utilisateurid"));
      log.info("merged");

      return pmeRepository.saveAndFlush(returnPme);
    } catch(Exception ex){
      log.error("Exception occured while updating PME with id : {}",ex.getMessage() );
      throw new RuntimeException();
    }
  }

  
//  @Override
//  @Transactional(propagation = Propagation.REQUIRED)
//  public Optional<Pme> upload(Long id, MultipartFile file, TypeDocument type)
//      throws IOException {
//    log.info("PmeService:upload ");
//    Optional<Pme> pme = pmeRepository.findById(id);
//
//    if (pme.isPresent()) {
//      PMEDocuments doc = (PMEDocuments) documentService.upload(file, id,
//              PMEDocuments.PROVENANCE, type);
//      pme.get().getDocuments().add(doc);
//
//      return Optional.of(pmeRepository.save(pme.get()));
//
//    }
//    return pme;
//  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Optional<Pme> upload(Long demandeId, MultipartFile file, String type)
          throws IOException {

    PMEDocuments doc;

    Optional<Pme> pme = pmeRepository.findById(demandeId);

    List<Documents> documents = documentService.findAll();

    log.info("size of Docu : " + pme.get().getDocuments().size());

    if (pme.isPresent()) {

      doc = (PMEDocuments) documentService.upload(file, demandeId,
              PMEDocuments.PROVENANCE, type);

      documents.forEach((e)->{
        if((e.getIdprovenance().equals(demandeId)) & (pme.get().getDocuments().size() >= 2) ){
          log.info("Id Document : " + e.getId());
          pmeRepository.deleteDocument(e.getId());
        }
      });
      pme.get().getDocuments().add(doc);

      log.info("Final Size of Document : " + documentService.findAll().size());

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
