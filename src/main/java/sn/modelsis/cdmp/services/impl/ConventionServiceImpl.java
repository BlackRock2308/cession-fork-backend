/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.repositories.ConventionRepository;
import sn.modelsis.cdmp.services.ConventionService;

/**
 * @author SNDIAGNEF
 *
 */
@Service
public class ConventionServiceImpl implements ConventionService{
  @Autowired
  private ConventionRepository conventionRepository;
 
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

}
