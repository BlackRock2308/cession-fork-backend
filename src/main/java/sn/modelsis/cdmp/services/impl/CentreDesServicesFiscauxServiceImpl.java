package sn.modelsis.cdmp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.CentreDesServicesFiscaux;
import sn.modelsis.cdmp.repositories.CentreDesServicesFiscauxRepository;
import sn.modelsis.cdmp.services.CentreDesServicesFiscauxService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CentreDesServicesFiscauxServiceImpl implements CentreDesServicesFiscauxService {

    @Autowired
    private CentreDesServicesFiscauxRepository repository;

    @Override
    public List<CentreDesServicesFiscaux> findAll() {
        return new ArrayList<>(repository
                .findAll());
    }
    
    @Override
    public CentreDesServicesFiscaux findByCode(String code) {
      return repository.findByCode(code);
    }

    @Override
    public CentreDesServicesFiscaux save(CentreDesServicesFiscaux centreDesServicesFiscaux) {
        try {
            return repository.save(centreDesServicesFiscaux);
        }catch  ( Exception e){
            log.debug("Ce code existe : {}",centreDesServicesFiscaux.getCode());
            return  null;
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
