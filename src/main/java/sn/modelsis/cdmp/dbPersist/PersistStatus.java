package sn.modelsis.cdmp.dbPersist;

import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.Statuts;
import sn.modelsis.cdmp.repositories.StatutRepository;

public class PersistStatus {

    private final StatutRepository statutRepository;

    public PersistStatus(StatutRepository statutRepository){
        this.statutRepository = statutRepository;

        Statut adhesionSoumise=new Statut();
        adhesionSoumise.setCode("ADHESION_SOUMISE");
        adhesionSoumise.setLibelle(Statuts.ADHESION_SOUMISE);

        Statut adhesionAcceptee=new Statut();
        adhesionAcceptee.setCode("ADHESION_ACCEPTEE");
        adhesionAcceptee.setLibelle(Statuts.ADHESION_ACCEPTEE);

        Statut adhesionRejetee=new Statut();
        adhesionRejetee.setCode("ADHESION_REJETEE");
        adhesionRejetee.setLibelle(Statuts.ADHESION_REJETEE);

        Statut demandeCessionSoumise=new Statut();
        demandeCessionSoumise.setCode("SOUMISE");
        demandeCessionSoumise.setLibelle(Statuts.SOUMISE);

        Statut demandeCessionRecevable=new Statut();
        demandeCessionRecevable.setCode("RECEVABLE");
        demandeCessionRecevable.setLibelle(Statuts.RECEVABLE);

        Statut demandeCessionNonRecevable=new Statut();
        demandeCessionNonRecevable.setCode("REJETEE");
        demandeCessionNonRecevable.setLibelle(Statuts.REJETEE);

        Statut demandeCessionComplementRequis=new Statut();
        demandeCessionComplementRequis.setCode("COMPLEMENT_REQUIS");
        demandeCessionComplementRequis.setLibelle(Statuts.COMPLEMENT_REQUIS);

        Statut demandeCessionCompletee=new Statut();
        demandeCessionCompletee.setCode("COMPLETEE");
        demandeCessionCompletee.setLibelle(Statuts.COMPLETEE);

        Statut demandeCessionNonRisquee=new Statut();
        demandeCessionNonRisquee.setCode("NON_RISQUEE");
        demandeCessionNonRisquee.setLibelle(Statuts.NON_RISQUEE);

        Statut demandeCessionRisquee=new Statut();
        demandeCessionRisquee.setCode("RISQUEE");
        demandeCessionRisquee.setLibelle(Statuts.RISQUEE);

        Statut demandeCessionConventionGeneree=new Statut();
        demandeCessionConventionGeneree.setCode("CONVENTION_GENEREE");
        demandeCessionConventionGeneree.setLibelle(Statuts.CONVENTION_GENEREE);


        Statut demandeCessionConventionCorrigee=new Statut();
        demandeCessionConventionCorrigee.setCode("CONVENTION_CORRIGEE");
        demandeCessionConventionCorrigee.setLibelle(Statuts.CONVENTION_CORRIGEE);

        Statut demandeCessionConventionSigneePme=new Statut();
        demandeCessionConventionSigneePme.setCode("CONVENTION_SIGNEE_PAR_PME");
        demandeCessionConventionSigneePme.setLibelle(Statuts.CONVENTION_SIGNEE_PAR_PME);

        Statut demandeCessionConventionSigneeDG=new Statut();
        demandeCessionConventionSigneeDG.setCode("CONVENTION_SIGNEE_PAR_DG");
        demandeCessionConventionSigneeDG.setLibelle(Statuts.CONVENTION_SIGNEE_PAR_DG);

        Statut demandeCessionConventionAcceptee=new Statut();
        demandeCessionConventionAcceptee.setCode("CONVENTION_ACCEPTEE");
        demandeCessionConventionAcceptee.setLibelle(Statuts.CONVENTION_ACCEPTEE);

        Statut demandeCessionConventionRejetee=new Statut();
        demandeCessionConventionRejetee.setCode("CONVENTION_REJETEE");
        demandeCessionConventionRejetee.setLibelle(Statuts.CONVENTION_REJETEE);

        Statut demandeCessionPMEAttentePaiement=new Statut();
        demandeCessionPMEAttentePaiement.setCode("PME_EN_ATTENTE_DE_PAIEMENT");
        demandeCessionPMEAttentePaiement.setLibelle(Statuts.PME_EN_ATTENTE_DE_PAIEMENT);

        Statut demandeCessionCDMPAttentePaiement=new Statut();
        demandeCessionCDMPAttentePaiement.setCode("CDMP_EN_ATTENTE_DE_PAIEMENT");
        demandeCessionCDMPAttentePaiement.setLibelle(Statuts.CDMP_EN_ATTENTE_DE_PAIEMENT);

        Statut demandeCessionPMEPaiementEnCours=new Statut();
        demandeCessionPMEPaiementEnCours.setCode("PME_PARTIELLEMENT_PAYEE");
        demandeCessionPMEPaiementEnCours.setLibelle(Statuts.PME_PARTIELLEMENT_PAYEE);

        Statut demandeCessionCDMPPaiementEnCours=new Statut();
        demandeCessionCDMPPaiementEnCours.setCode("CDMP_PARTIELLEMENT_PAYEE");
        demandeCessionCDMPPaiementEnCours.setLibelle(Statuts.CDMP_PARTIELLEMENT_PAYEE);

        Statut demandeCessionPMEPayee=new Statut();
        demandeCessionPMEPayee.setCode("PME_TOTALEMENT_PAYEE");
        demandeCessionPMEPayee.setLibelle(Statuts.PME_TOTALEMENT_PAYEE);

        Statut demandeCessionCDMPPayee=new Statut();
        demandeCessionCDMPPayee.setCode("CDMP_TOTALEMENT_PAYEE");
        demandeCessionCDMPPayee.setLibelle(Statuts.CDMP_TOTALEMENT_PAYEE);

        statutRepository.save(adhesionSoumise);
        statutRepository.save(adhesionAcceptee);
        statutRepository.save(adhesionRejetee);
        statutRepository.save(demandeCessionSoumise);
        statutRepository.save(demandeCessionRecevable);
        statutRepository.save(demandeCessionNonRecevable);
        statutRepository.save(demandeCessionComplementRequis);
        statutRepository.save(demandeCessionCompletee);
        statutRepository.save(demandeCessionNonRisquee);
        statutRepository.save(demandeCessionRisquee);
        statutRepository.save(demandeCessionConventionGeneree);
        statutRepository.save(demandeCessionConventionCorrigee);
        statutRepository.save(demandeCessionConventionSigneePme);
        statutRepository.save(demandeCessionConventionSigneeDG);
        statutRepository.save(demandeCessionConventionAcceptee);
        statutRepository.save(demandeCessionConventionRejetee);
        statutRepository.save(demandeCessionPMEAttentePaiement);
        statutRepository.save(demandeCessionCDMPAttentePaiement);
        statutRepository.save(demandeCessionPMEPaiementEnCours);
        statutRepository.save(demandeCessionCDMPPaiementEnCours);
        statutRepository.save(demandeCessionPMEPayee);
        statutRepository.save(demandeCessionCDMPPayee);
    }

        //insertion des differents statuts que l'on peut avoir
        //chaque statut est inséré une fois et indique l'étape de la demande dans la procédure









}
