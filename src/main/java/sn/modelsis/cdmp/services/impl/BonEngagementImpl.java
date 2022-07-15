package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.services.BonEngagementService;

import java.util.List;
import java.util.Optional;

@Service
public class BonEngagementImpl implements BonEngagementService {
    @Autowired
    private BonEngagementRepository bonEngagementRepository;

    @Override
    public BonEngagement save(BonEngagement bonEngagement) {
        return bonEngagementRepository.save(bonEngagement);
    }

    @Override
    public List<BonEngagement> findAll(){
        return bonEngagementRepository.findAll();
    }

    @Override
    public Optional<BonEngagement> getBonEngagement(Long id) {
        return bonEngagementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        bonEngagementRepository.deleteById(id);
    }
}
