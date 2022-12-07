/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.ConventionDocuments;
import sn.modelsis.cdmp.entities.Documents;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.ItemNotFoundException;
import sn.modelsis.cdmp.repositories.ConventionRepository;
import sn.modelsis.cdmp.services.ConventionService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.util.ExceptionUtils;

/**
 * @author SNDIAGNEF
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConventionServiceImpl implements ConventionService{
  private final ConventionRepository conventionRepository;
  private final DocumentService documentService;

  @Override
  public Convention save(Convention convention) {
    Convention newConvention;
    try
      {
        log.info("ConventionService:saving new convention ........");
        newConvention = conventionRepository.save(convention);
      } catch (Exception ex){
      log.error("Exception occured while adding convention. Error message : {}", ex.getMessage());
        throw new CustomException("Exception occured while adding new convention");
    }
    return newConvention;
	}

  @Override
  public List<Convention> findAll(){
    log.info("ConventionService:findAll fetching all conventions ........");

    return new ArrayList<>(conventionRepository
            .findAll());
  }

  @Override
  public Optional<Convention> getConvention(Long id) {
    log.info("ConventionService:getConvention fetching single convention with id : {}", id);

    return conventionRepository
            .findById(id)
            ;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    log.info("ConventionService:delete deleting convention with id : {}", id);

    conventionRepository.deleteConvention(id);

  }
  
  @Override
  public Optional<Convention> upload(Long id, MultipartFile file, TypeDocument type)
      throws IOException {
    Optional<Convention> convention = conventionRepository.findById(id);
    if (convention.isPresent()) {

      ConventionDocuments doc = (ConventionDocuments) documentService.upload(file, id,
              ConventionDocuments.PROVENANCE, type);
      convention.get().getDocuments().add(doc);

      return Optional.of(conventionRepository.save(convention.get()));

    }
    return convention;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Convention updateValeurDecote(Long idConvention, double newValue){
    log.info("ConventionService:updateValeurDecote request params {}", idConvention);

    Optional <Convention> optional = Optional.ofNullable(conventionRepository.findById(idConvention).orElse(null));
    optional.get().setValeurDecoteByDG(newValue);

    log.info("ValeurDecote by DG in convention before saving: {}", optional.get().getValeurDecoteByDG());
    Convention convention = conventionRepository.saveAndFlush(optional.get());
    log.info("ValeurDecote by DG  after saving : {}", convention.getValeurDecoteByDG());

    log.info("ConventionService:updateValeurDecote saved in Database with value Decote : {}", convention.getValeurDecote());

    return convention;
  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Convention transmettreConvention (Long id,
                                                 Convention newConvention) {
    Optional <Convention> existingConvention;
    try{
      log.info("ConventionService:transmettreConvention updating ........");
      existingConvention = conventionRepository.findById(id);

      existingConvention.get().setValeurDecote(newConvention.getValeurDecote());
      existingConvention.get().setValeurDecoteByDG(newConvention.getValeurDecoteByDG());
      existingConvention.get().setActiveConvention(newConvention.isActiveConvention());
      existingConvention.get().setDateConvention(newConvention.getDateConvention());
      existingConvention.get().setPme(newConvention.getPme());
      log.info("DocumentService:supression de l'ancien document de la convention ........");
//      for (Documents doc:existingConvention.get().getDocuments()
//           ) {
//        documentService.delete(doc.getId());
//      }
      log.info("DocumentService:supression de l'ancien document de la convention terminée.");

      existingConvention.get().setDocuments(newConvention.getDocuments());

      log.info("ConventionService:transmettreConvention with document : {}",existingConvention.get().getDocuments());

      conventionRepository.saveAndFlush(existingConvention.get());
      log.info("ConventionService:transmettreConvention update convention with id : {}",existingConvention.get().getIdConvention());
    }catch (Exception ex){
      log.error("Exception occured while updating convention with id : {}",id );
      throw new CustomException("Error occured while updating this convention");
    }
    return existingConvention.get();
  }


  /*La correction d'une convention consiste en la création d'une toute nouvelle convention*/
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void corrigerConvention (Long id) {
    Optional <Convention> existingConvention;
    try{
      log.info("ConventionService:corrigerConvention  ........");
      existingConvention = conventionRepository.findById(id);
      log.info("convention á corriger : {}", existingConvention.get());
      ExceptionUtils.absentOrThrow(existingConvention, ItemNotFoundException.CONVENTION_BY_ID, id.toString());
      log.info("existingConvention : {} ,", existingConvention.get());
      if(existingConvention.isPresent()){
        conventionRepository.deleteById(id);
        log.info("convention with id : {} deleted successfully",existingConvention.get().getIdConvention());
      }
    }catch (Exception ex){
      log.error("Exception occured while making correction on convention with id : {}",id );
      throw new CustomException("Error occured while making correction");
    }


  }




}
