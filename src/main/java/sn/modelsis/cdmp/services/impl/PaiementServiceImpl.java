package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.PaiementRepository;
import sn.modelsis.cdmp.services.PaiementService;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementServiceImpl implements PaiementService {


    private final PaiementRepository paiementRepository;

    private final BonEngagementRepository bonEngagementRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository,
                               BonEngagementRepository bonEngagementRepository) {
        this.paiementRepository = paiementRepository;
        this.bonEngagementRepository = bonEngagementRepository;
    }

    @Override
    public Paiement save(Paiement paiement) {
//        BonEngagement be = paiement.getDemandeCession().getBonEngagement();
//        bonEngagementRepository.save(be);

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


