package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DetailPaiementDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.DetailPaiementRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.*;
import sn.modelsis.cdmp.util.DtoConverter;

@Service
public class DetailPaiementServiceImpl implements DetailPaiementService {

  private final DetailPaiementRepository detailPaiementRepository;

  private final DocumentService documentService;

  private final StatutRepository statutRepository;

  private final PaiementService paiementService;

  @Autowired
  private DemandeCessionService demandeService;

  public DetailPaiementServiceImpl(
      DetailPaiementRepository detailPaiementRepository,
      DocumentService documentService,
      StatutRepository statutRepository,
      PaiementService paiementService) {
    this.detailPaiementRepository = detailPaiementRepository;
    this.documentService = documentService;
    this.statutRepository = statutRepository;
    this.paiementService = paiementService;
  }

 // @Override
  //public DetailPaiement paiementPME(DetailPaiement detailPaiement) {
   // return null;
 // }

  @Override
  public DetailPaiement paiementPME(DetailPaiement detailPaiement) {

    double montant = detailPaiement.getMontant();
    if(montant<=0)
      throw new CustomException("Le montant doit etre supérieur a ZERO");
    detailPaiement.setTypepaiement(TypePaiement.CDMP_PME);
    Paiement paiement =
        paiementService.getPaiement(detailPaiement.getPaiement().getIdPaiement()).orElse(null);
    if (paiement == null) throw new CustomException("payement not found");
    double soldePme = paiement.getSoldePME();
    if(soldePme<montant)
      throw new CustomException("Erreur Revoir le montant de paiment il doit etre inferieur au Solde du PME ");
    paiement.setSoldePME(paiement.getSoldePME() - detailPaiement.getMontant());
    Statut statut = statutRepository.findByCode("PME_PARTIELLEMENT_PAYEE");
    Statut statutEnd = statutRepository.findByCode("PME_TOTALEMENT_PAYEE");
    paiement.setStatutPme(statut);
    DetailPaiement detailPaiementSaved = detailPaiementRepository.save(detailPaiement);
    detailPaiement.setPaiement(paiement);
    paiement.getDetailPaiements().add(detailPaiementSaved);
    if(soldePme==montant)
      paiement.setStatutPme(statutEnd);
    Paiement paiementSaved = paiementService.savePaiement(paiement);
    if (paiementSaved != null){
      if(paiementSaved.getStatutPme().getCode().equalsIgnoreCase("PME_TOTALEMENT_PAYEE")
      && paiementSaved.getStatutCDMP().getCode().equalsIgnoreCase("CDMP_TOTALEMENT_PAYEE")){
        DemandeCession demandeCession = paiementSaved.getDemandeCession();
        Statut statutD = statutRepository.findByCode("DEMANDE_BOUCLEE");
        demandeCession.setStatut(statutD);
        demandeService.save(demandeCession);
      }
    }else {
      throw new CustomException("erreur lors de l'ajout du detail de paiement");
    }
    // paiementService.update(detailPaiement.getPaiement().getIdPaiement(),detailPaiement.getMontant(),detailPaiement.getTypepaiement());
    return detailPaiementSaved;
  }

  @Override
  public DetailPaiement paiementCDMP(DetailPaiement detailPaiement) {
    double montant = detailPaiement.getMontant();
    if(montant<=0)
      throw new CustomException("Le montant doit etre supérieur a ZERO");
    detailPaiement.setTypepaiement(TypePaiement.SICA_CDMP);
    Paiement paiement =
        paiementService.getPaiement(detailPaiement.getPaiement().getIdPaiement()).orElse(null);
    if (paiement == null) throw new CustomException("payment not found ");
    Statut statut = statutRepository.findByCode("CDMP_PARTIELLEMENT_PAYEE");
    Statut statutEnd = statutRepository.findByCode("CDMP_TOTALEMENT_PAYEE");
    double montantRecuCDMP = paiement.getMontantRecuCDMP();
    double montantCreanceInitial = paiement.getMontantCreanceInitial();
    if(montant>montantCreanceInitial || montantCreanceInitial< montantRecuCDMP+montant)
      throw new CustomException("Erreur Revoir le montant de paiment il doit etre inferieur au montant de creance ");
    paiement.setMontantRecuCDMP(paiement.getMontantRecuCDMP() + montant);
    paiement.setStatutCDMP(statut);
    if(montantRecuCDMP+montant == paiement.getMontantCreanceInitial())
      paiement.setStatutCDMP(statutEnd);
    detailPaiement.setPaiement(paiement);
    DetailPaiement detailPaiementSaved = detailPaiementRepository.save(detailPaiement);
    paiement.getDetailPaiements().add(detailPaiementSaved);
    Paiement paiementSaved = paiementService.savePaiement(paiement);
    if (paiementSaved != null){
      if(paiementSaved.getStatutPme().getCode().equalsIgnoreCase("PME_TOTALEMENT_PAYEE")
              && paiementSaved.getStatutCDMP().getCode().equalsIgnoreCase("CDMP_TOTALEMENT_PAYEE")){
        DemandeCession demandeCession = paiementSaved.getDemandeCession();
        Statut statutD = statutRepository.findByCode("DEMANDE_BOUCLEE");
        demandeCession.setStatut(statutD);
        demandeService.save(demandeCession);
      }
    }else {
      throw new CustomException("erreur lors de l'ajout du detail de paiement");
    }
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

      DPaiementDocuments doc =
          (DPaiementDocuments)
              documentService.upload(file, id, DPaiementDocuments.PROVENANCE, type);
      be.get().getDocuments().add(doc);

      return Optional.of(detailPaiementRepository.save(be.get()));
    }
    return be;
  }

  @Override
  public Set<DetailPaiementDto> getDetailPaiementByTypePaiement(Long id, String typePaiement) {

    Paiement paiement = paiementService.getPaiement(id).orElse(null);
    if (paiement == null) throw new CustomException("Le paiement n'existe pas");
    Set<DetailPaiement> detailPaiements = paiement.getDetailPaiements();
    Set<DetailPaiementDto> sortDetailPaiements = new HashSet<>();
    if (detailPaiements.isEmpty()) return sortDetailPaiements;
    for (DetailPaiement detailPaiement : detailPaiements) {
      if (detailPaiement.getTypepaiement().name().equals(typePaiement))
        sortDetailPaiements.add(DtoConverter.convertToDto(detailPaiement));
    }
    return sortDetailPaiements;
  }

  @Override
  public double getSommePaiementByTypePaiement(Long id, String typePaiement) {
    double somme = 0;
    Set<DetailPaiementDto> detailPaiementDtos = getDetailPaiementByTypePaiement(id, typePaiement);
    if (detailPaiementDtos.isEmpty()) return somme;
    for (DetailPaiementDto detailPaiementDto : detailPaiementDtos) {
      if (typePaiement.equals("CDMP_PME")) somme = detailPaiementDto.getPaiementDto().getSoldePME();
      else if (typePaiement.equals("SCIA_CDMP"))
        somme = detailPaiementDto.getPaiementDto().getMontantRecuCDMP();
    }
    return somme;
  }
}
