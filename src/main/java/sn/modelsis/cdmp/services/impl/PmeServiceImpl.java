package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.PmeService;

import java.util.List;
import java.util.Optional;
@Service
public class PmeServiceImpl implements PmeService {

    @Autowired
    private PmeRepository pmeRepository;

    @Override
    public Pme save(Pme pme){return pmeRepository.save(pme);}

    @Override
    public List<Pme> findAll(){return pmeRepository.findAll();}

    @Override
    public Optional<Pme> getPme(Long id){return pmeRepository.findById(id);}

    @Override
    public void delete(Long id){pmeRepository.deleteById(id);}
}
