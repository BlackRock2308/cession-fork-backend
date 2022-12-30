package sn.modelsis.cdmp.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.FormeJuridique;
import sn.modelsis.cdmp.entities.MinistereDepensier;
import sn.modelsis.cdmp.repositories.FormeJuridiqueRepository;
import sn.modelsis.cdmp.repositories.MinistereDepensierRepository;
import sn.modelsis.cdmp.services.FormeJuridiqueService;
import sn.modelsis.cdmp.services.MinistereDepensierService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormeJuridiqueServiceImpl implements FormeJuridiqueService {

    @Autowired
    private FormeJuridiqueRepository repository;

    @Override
    public List<FormeJuridique> findAll() {
        return new ArrayList<>(repository
                .findAll());
    }
    
    @Override
    public FormeJuridique findByCode(String code) {
      return repository.findByCode(code);
    }

    @Override
    public FormeJuridique save(FormeJuridique formeJuridique) {
        try {
            return repository.save(formeJuridique);
        }catch  ( Exception e){
            log.debug("Ce code existe : {}",formeJuridique.getCode());
            return  null;
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
