package sn.modelsis.cdmp.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.DocumentService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DemandeServiceImpl implements DemandeService {

    private final DemandeRepository demandeRepository;
    private final DocumentService documentService;

    @Override
    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }


    //La liste des demandes a l'étape analyse du risque
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


    //La liste des demandes a l'étape de paiement

    @Override
    public List<Demande> findAllPaiements() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.PME_EN_ATTENTE_DE_PAIEMENT));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.PME_PARTIELLEMENT_PAYEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.PME_TOTALEMENT_PAYEE));
        return demandes;
    }


    //La liste des demandes a l'étape des conventions pour l'ordonnateur

    @Override
    public List<Demande> findAllConventionsOrdonnateur() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_ACCEPTEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_REJETEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_TRANSMISE));
        return demandes;
    }

    //La liste des demandes a l'étape des conventions pour le DG

    @Override
    public List<Demande> findAllConventionsDG() {
        List<Demande> demandes=new ArrayList<>();
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_SIGNEE_PAR_DG));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_SIGNEE_PAR_PME));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_REJETEE));
        demandes.addAll(demandeRepository.findAllByStatut_Libelle(Statuts.CONVENTION_TRANSMISE));

        return demandes;
    }

    //La liste des creances (cédées et rejetées)
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

    @Override
    public String getNumDemande() {
        StringBuilder numero = new StringBuilder();
        Integer annee = Calendar.getInstance().get(Calendar.YEAR);
        Integer num = 0;
        Optional<Demande> optDemande = demandeRepository.findTopByOrderByIdDesc();
        Demande demande = new Demande();
        if(optDemande!=null && optDemande.isPresent()){
            demande = optDemande.get();
            String c =  demande.getNumeroDemande().substring(demande.getNumeroDemande().length()-4, demande.getNumeroDemande().length());
            num = Integer.parseInt(c)+1;
        }
        if(demande.getNumeroDemande()==null || !demande.getNumeroDemande().substring(0, demande.getNumeroDemande().length()-5).equals(annee)){
            num = 1;
        }
        numero.append(annee);
        numero.append("-");
        if(num<10){
            numero.append("000");
        }
        else if(num<100){
            numero.append("00");
        }else {
            numero.append("0");
        }
        numero.append(num);
        return numero.toString();
    }
}
