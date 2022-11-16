package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.DetailPaiementRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.DetailPaiementService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.services.PaiementService;


@Service
public class DetailPaiementServiceImpl implements DetailPaiementService {


    final private DetailPaiementRepository detailPaiementRepository;
    

    final private DocumentService documentService;

   final private StatutRepository statutRepository;


    final private PaiementService paiementService;

    public DetailPaiementServiceImpl(DetailPaiementRepository detailPaiementRepository, DocumentService documentService, StatutRepository statutRepository, PaiementService paiementService) {
        this.detailPaiementRepository = detailPaiementRepository;
        this.documentService = documentService;
        this.statutRepository = statutRepository;
        this.paiementService = paiementService;
    }

    @Override
    public DetailPaiement paiementPME(DetailPaiement detailPaiement) {
        return null;
    }

    @Override
    public DetailPaiement getPaiement(DetailPaiement detailPaiement) {

        detailPaiement.setTypepaiement(TypePaiement.CDMP_PME);
        Paiement paiement = paiementService.getPaiement(detailPaiement.getPaiement().getIdPaiement()).orElse(null);
        Statut statut = statutRepository.findByCode("PME_PARTIELLEMENT_PAYEE");
        Statut statutEnd = statutRepository.findByCode("PME_TOTALEMENT_PAYEE");
        paiement.setStatutPme(statut);
        DetailPaiement detailPaiementSaved = detailPaiementRepository.save(detailPaiement);
        detailPaiement.setPaiement(paiement);
        paiement.getDetailPaiements().add(detailPaiementSaved);
        Paiement paiementSaved  = paiementService.savePaiement(paiement);

        if(paiementSaved==null)
            throw new CustomException("erreur lors de l'ajout du detail de paiement");
       // paiementService.update(detailPaiement.getPaiement().getIdPaiement(),detailPaiement.getMontant(),detailPaiement.getTypepaiement());
        return detailPaiementSaved;
    }

    @Override
    public DetailPaiement paiementCDMP(DetailPaiement detailPaiement) {

        detailPaiement.setTypepaiement(TypePaiement.SICA_CDMP);
        Paiement paiement = paiementService.getPaiement(detailPaiement.getPaiement().getIdPaiement()).orElse(null);
        Statut statut = statutRepository.findByCode("CDMP_PARTIELLEMENT_PAYEE");
        Statut statutEnd = statutRepository.findByCode("CDMP_TOTALEMENT_PAYEE");
        paiement.setStatutCDMP(statut);
        detailPaiement.setPaiement(paiement);
        DetailPaiement detailPaiementSaved = detailPaiementRepository.save(detailPaiement);
        paiement.getDetailPaiements().add(detailPaiementSaved);
        Paiement paiementSaved  = paiementService.savePaiement(paiement);
        if(paiementSaved==null)
            throw new CustomException("erreur lors de l'ajout du detail de paiement");
        // paiementService.update(detailPaiement.getPaiement().getIdPaiement(),detailPaiement.getMontant(),detailPaiement.getTypepaiement());
        return detailPaiementSaved;
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
