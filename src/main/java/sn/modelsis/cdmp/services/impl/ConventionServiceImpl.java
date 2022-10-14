/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.ConventionDocuments;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.repositories.ConventionRepository;
import sn.modelsis.cdmp.services.ConventionService;
import sn.modelsis.cdmp.services.DocumentService;

/**
 * @author SNDIAGNEF
 *
 */
@Service
public class ConventionServiceImpl implements ConventionService{
  @Autowired
  private ConventionRepository conventionRepository;
  
  @Autowired
  private DocumentService documentService;
 
  @Override
  public Convention save(Convention convention) {
    return conventionRepository.save(convention);
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
