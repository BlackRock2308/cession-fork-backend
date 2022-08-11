package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.services.DemandeService;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeServiceImpl implements DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Override
    public Demande save(Demande demande){
        return demandeRepository.save(demande);
    }

    @Override
    public List<Demande> findAll(){
        return demandeRepository.findAll();
    }

    @Override
    public Optional<Demande> getDemande(Long id) {
        return demandeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        demandeRepository.deleteById(id);
    }
}
