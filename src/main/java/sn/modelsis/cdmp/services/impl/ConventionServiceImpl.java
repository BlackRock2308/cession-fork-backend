/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.ConventionDocuments;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.ConventionRepository;
import sn.modelsis.cdmp.services.ConventionService;
import sn.modelsis.cdmp.services.DocumentService;

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
        log.info("ConventionService:save ........");
        newConvention = conventionRepository.save(convention);
      } catch (Exception ex){
        throw new CustomException("Error");
    }
    return newConvention;
	}

  @Override
  public List<Convention> findAll(){
    return conventionRepository.findAll();
  }

  @Override
  public Optional<Convention> getConvention(Long id) {
    return conventionRepository.findById(id);
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

}
