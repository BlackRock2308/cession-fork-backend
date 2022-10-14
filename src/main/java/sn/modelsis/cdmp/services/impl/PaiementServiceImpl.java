package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.repositories.PaiementRepository;
import sn.modelsis.cdmp.services.PaiementService;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementServiceImpl implements PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Override
    public Paiement save(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> findAll(){
        return paiementRepository.findAll();
    }

    @Override
    public Optional<Paiement> getPaiement(Long id) {
        return paiementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        paiementRepository.deleteById(id);

    }

}


