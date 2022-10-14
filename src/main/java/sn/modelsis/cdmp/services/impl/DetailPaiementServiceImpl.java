package sn.modelsis.cdmp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.modelsis.cdmp.entities.DetailPaiement;
import sn.modelsis.cdmp.repositories.DetailPaiementRepository;
import sn.modelsis.cdmp.services.DetailPaiementService;


@Service
public class DetailPaiementServiceImpl implements DetailPaiementService {

    @Autowired
    private DetailPaiementRepository detailPaiementRepository;

    @Override
    public DetailPaiement save(DetailPaiement detailPaiement) {
        return detailPaiementRepository.save(detailPaiement);
    }

    @Override
    public List<DetailPaiement> findAll() {
        return detailPaiementRepository.findAll();
    }

    @Override
    public Optional<DetailPaiement> getDetailPaiement(Long id) {
        return detailPaiementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        detailPaiementRepository.deleteById(id);

    }
}
