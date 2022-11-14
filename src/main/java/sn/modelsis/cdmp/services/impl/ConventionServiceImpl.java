/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.mappers.ConventionMapper;
import sn.modelsis.cdmp.repositories.ConventionRepository;
import sn.modelsis.cdmp.services.ConventionService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.services.ParametrageDecoteService;

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

  private final ParametrageDecoteService decoteService;

  private final ConventionMapper conventionMapper;
 
  @Override
  public Convention save(Convention convention) {
    Convention newConvention;
    try
      {
        log.info("ConventionService:saving new convention ........");
        newConvention = conventionRepository.save(convention);
      } catch (Exception ex){
        throw new CustomException("Error");
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
    return conventionRepository
            .findById(id);
  }

  @Override
  public void delete(Long id) {
    conventionRepository.deleteById(id);

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
  public Convention updateEntireConvention (Long id,
                                                 Convention newConvention) {
    Optional <Convention> existingConvention;
    try{
      log.info("ConventionService:updateEntireConvention updating ........");
      existingConvention = conventionRepository.findById(id);

      existingConvention.get().setValeurDecote(newConvention.getValeurDecote());
      existingConvention.get().setModePaiement(newConvention.getModePaiement());
     existingConvention.get().setValeurDecoteByDG(newConvention.getValeurDecoteByDG());

      conventionRepository.saveAndFlush(existingConvention.get());
      log.info("ConventionService:updateDecoteInConvention  update Pme with id : {}",existingConvention.get().getIdConvention());
    }catch (Exception ex){
      log.error("Exception occured while updating Decote with id : {}",id );
      throw new CustomException("Error occured while updating this Decote param ");
    }
    return existingConvention.get();
  }

}
