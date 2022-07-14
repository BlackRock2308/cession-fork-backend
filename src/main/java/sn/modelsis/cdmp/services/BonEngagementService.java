package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.exceptions.NotFoundException;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;

import java.util.List;

@Service
public class BonEngagementService {
    private BonEngagementRepository bonEngagementRepository;

    public BonEngagementService(BonEngagementRepository bonEngagementRepository){
        this.bonEngagementRepository = bonEngagementRepository;
    }

    public BonEngagement addBonEngagment(BonEngagement bonEngagement){
        return bonEngagementRepository.save(bonEngagement);
    }

    public List<BonEngagement> findAllBonEngagement() {
        return bonEngagementRepository.findAll();
    }

   /* public BonEngagement findBonEngagementById(Long id) {
        return bonEngagementRepository.findBonEngagementById(id)
                .orElseThrow(()-> new NotFoundException("Bon engagment not found"));
    }*/

    public BonEngagement updateBonEngagement(BonEngagement bonEngagement, Long id) {
        BonEngagement bonEngagementUpdate = bonEngagementRepository.findById(id).get();

        bonEngagementUpdate.setMontantCreance(bonEngagement.getMontantCreance());
        bonEngagementUpdate.setReference(bonEngagement.getReference());
        bonEngagementUpdate.setNaturePrestation(bonEngagement.getNaturePrestation());
        bonEngagementUpdate.setNatureDepense(bonEngagement.getNatureDepense());
        bonEngagementUpdate.setObjetDepense(bonEngagement.getObjetDepense());
        bonEngagementUpdate.setImputation(bonEngagement.getImputation());
        bonEngagementUpdate.setDateBonEngagement(bonEngagement.getDateBonEngagement());
        bonEngagementUpdate.setIdentificationComptable(bonEngagement.getIdentificationComptable());

        return bonEngagementRepository.save(bonEngagementUpdate);
    }
    public void deleteBonEngagement(Long id){
        bonEngagementRepository.deleteById(id);
    }

}
