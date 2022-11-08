package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.repositories.DetailPaiementRepository;
import sn.modelsis.cdmp.services.DetailPaiementService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.services.PaiementService;


@Service
public class DetailPaiementServiceImpl implements DetailPaiementService {

    @Autowired
    private DetailPaiementRepository detailPaiementRepository;
    
    @Autowired
    private DocumentService documentService;

    @Autowired
    private PaiementService paiementService;

    @Override
    public DetailPaiement paiementPME(DetailPaiement detailPaiement) {
        return null;
    }

    @Override
    public Paiement getPaiement(DetailPaiement detailPaiement) {

        detailPaiement.setTypepaiement(TypePaiement.CDMP_PME);
        Paiement paiement = paiementService.getPaiement(detailPaiement.getPaiement().getIdPaiement()).orElse(null);
        detailPaiement.setPaiement(paiement);
        paiement.getDetailPaiements().add(detailPaiementRepository.save(detailPaiement));
        Paiement paiementSaved  = paiementService.savePaiement(paiement);
       // paiementService.update(detailPaiement.getPaiement().getIdPaiement(),detailPaiement.getMontant(),detailPaiement.getTypepaiement());
        return paiementSaved;
    }

    @Override
    public Paiement paiementCDMP(DetailPaiement detailPaiement) {

        detailPaiement.setTypepaiement(TypePaiement.SICA_CDMP);
        Paiement paiement = paiementService.getPaiement(detailPaiement.getPaiement().getIdPaiement()).orElse(null);
        detailPaiement.setPaiement(paiement);
        paiement.getDetailPaiements().add(detailPaiementRepository.save(detailPaiement));
        Paiement paiementSaved  = paiementService.savePaiement(paiement);
        // paiementService.update(detailPaiement.getPaiement().getIdPaiement(),detailPaiement.getMontant(),detailPaiement.getTypepaiement());
        return paiementSaved;
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
    
    @Override
    public Optional<DetailPaiement> upload(Long id, MultipartFile file, TypeDocument type)
        throws IOException {
      Optional<DetailPaiement> be = detailPaiementRepository.findById(id);
      if (be.isPresent()) {

        DPaiementDocuments doc = (DPaiementDocuments) documentService.upload(file, id,
                DPaiementDocuments.PROVENANCE, type);
        be.get().getDocuments().add(doc);

        return Optional.of(detailPaiementRepository.save(be.get()));

      }
      return be;
    }
}
