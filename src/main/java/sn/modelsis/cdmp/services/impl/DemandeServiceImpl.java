package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeServiceImpl implements DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private BonEngagementRepository bonEngagementRepository;

    @Autowired
    private PmeRepository pmeRepository;

    @Autowired
    private StatutRepository statutRepository;
    @Autowired
    private DocumentService documentService;

    @Override
    public Demande saveAdhesion(Demande demande){
        //demande.setStatut(Statuts.NON_RISQUEE);
        Pme pme =demande.getPme();
        Statut statut=new Statut();
        statut.setLibelle(Statuts.ADHESION_SOUMISE);
        demande.setStatut(statut);
        pmeRepository.save(pme);
        statutRepository.save(demande.getStatut());
        return demandeRepository.save(demande);
    }

    @Override
    public Demande saveCession(Demande demande) {
        BonEngagement be = demande.getBonEngagement();
        bonEngagementRepository.save(be);
        demande.setDateDemandeCession(new Date());
        Statut statut=new Statut();
        statut.setLibelle(Statuts.SOUMISE);
        demande.setStatut(statut);
        statutRepository.save(statut);
        /*for (Documents document:demande.getDocuments()
        ) {
            documentService.upload(document.);
        }*/

        return demandeRepository.save(demande);
    }

    @Override
    @Transactional
    public DemandeDto rejetAdhesion(DemandeDto demandeDto) {
        Statut statut = DtoConverter.convertToEntity(demandeDto.getStatut());
        Demande demande = DtoConverter.convertToEntity(demandeDto);
        Pme pme = DtoConverter.convertToEntity(demandeDto.getPme());
        //pme.setHasninea(false);
        pmeRepository.save(pme);
        statutRepository.save(statut);
        Demande result=demandeRepository.save(demande);
        return DtoConverter.convertToDto(result) ;
    }

    @Override
    @Transactional
    public DemandeDto validerAdhesion(DemandeDto demandeDto) {
        Statut statut = DtoConverter.convertToEntity(demandeDto.getStatut());
        Demande demande = DtoConverter.convertToEntity(demandeDto);
        Pme pme = DtoConverter.convertToEntity(demandeDto.getPme());
       // pme.setHasninea(true);
        pmeRepository.save(pme);
        statutRepository.save(statut);
        Demande result=demandeRepository.save(demande);
        return DtoConverter.convertToDto(result) ;
    }

    @Override
    public List<Demande> findAll(){
        return demandeRepository.findAll();
    }

    @Override
    public List<Demande> findAllDemandesAdhesion() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.ADHESION_SOUMISE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.ADHESION_REJETEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.ADHESION_ACCEPTEE));

        return demandes;
    }

    @Override
    public List<Demande> findAllNouvellesDemandes() {
        return null;
    }

    @Override
    public List<Demande> findAllAnalyseRisque() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.COMPLEMENT_REQUIS));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.RECEVABLE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.RISQUEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.NON_RISQUEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.COMPLETEE));

        return demandes;
    }

    @Override
    public List<Demande> findAllConventionsComptable() {
        return null;
    }

    @Override
    public List<Demande> findAllPaiements() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.PME_EN_ATTENTE_DE_PAIEMENT));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.PME_PARTIELLEMENT_PAYEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.PME_TOTALEMENT_PAYEE));
        return demandes;
    }

    @Override
    public List<Demande> findAllConventionsOrdonnateur() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_ACCEPTEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_REJETEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_TRANSMISE));
        return demandes;
    }

    @Override
    public List<Demande> findAllConventionsDG() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_SIGNEE_PAR_DG));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_SIGNEE_PAR_PME));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_REJETEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_TRANSMISE));

        return demandes;
    }

    @Override
    public List<Demande> findAllCreances() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.RISQUEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.RECEVABLE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.NON_RISQUEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.COMPLETEE));
        return demandes;

    }

    @Override
    public List<Demande> findAllPMEDemandes() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.COMPLETEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.RECEVABLE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.RISQUEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.NON_RISQUEE));
        return demandes;
    }

    @Override
    public List<Demande> findAllConventionsPME() {
        return null;
    }

    @Override
    public List<Demande> findAllPaiementsPME() {
        return null;
    }

    @Override
    public Optional<Demande> getDemande(Long id) {
        return demandeRepository.findById(id);
    }





    @Override
    public void delete(Long id) {
        demandeRepository.deleteById(id);
    }
    
    @Override
    public Optional<Demande> upload(Long demandeId, MultipartFile file, TypeDocument type)
        throws IOException {
      Optional<Demande> demande = demandeRepository.findById(demandeId);
      if (demande.isPresent()) {

        DemandeDocuments doc = (DemandeDocuments) documentService.upload(file, demandeId,
                DemandeDocuments.PROVENANCE, type);
        demande.get().getDocuments().add(doc);

        return Optional.of(demandeRepository.save(demande.get()));

      }
      return demande;
    }
}
