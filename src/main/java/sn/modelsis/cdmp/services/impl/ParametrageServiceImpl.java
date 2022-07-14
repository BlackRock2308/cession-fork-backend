/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Parametrage;
import sn.modelsis.cdmp.repositories.ParametrageRepository;
import sn.modelsis.cdmp.services.ParametrageService;

/**
 * @author SNDIAGNEF
 *
 */
@Service
public class ParametrageServiceImpl implements ParametrageService{
  @Autowired
  private ParametrageRepository parametrageRepository;
 
  @Override
  public Parametrage save(Parametrage parametrage) {
    return parametrageRepository.save(parametrage);
	}

  @Override
  public List<Parametrage> findAll(){
    return parametrageRepository.findAll();
  }

  @Override
  public Optional<Parametrage> getParametrage(Long id) {
    return parametrageRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    parametrageRepository.deleteById(id);

  }

}
