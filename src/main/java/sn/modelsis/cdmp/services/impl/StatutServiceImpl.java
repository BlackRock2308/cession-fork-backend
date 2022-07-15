package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.StatutService;

import java.util.List;
import java.util.Optional;

@Service
public class StatutServiceImpl implements StatutService {

    @Autowired
    private StatutRepository statutRepository;

    @Override
    public Statut save(Statut statut){return statutRepository.save(statut);}

    @Override
    public List<Statut> findAll(){return statutRepository.findAll();}

    @Override
    public Optional<Statut> getStatut(Long id){return statutRepository.findById(id);}

    @Override
    public void delete(Long id){statutRepository.deleteById(id);}
}


