package sn.modelsis.cdmp.dbPersist;

import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.Statuts;
import sn.modelsis.cdmp.repositories.StatutRepository;

public class PersistStatus {

    private final StatutRepository statutRepository;

    public PersistStatus(StatutRepository statutRepository){
        this.statutRepository = statutRepository;

        Statut adhesionSoumise=new Statut();
        adhesionSoumise.setCode("1.0");
        adhesionSoumise.setLibelle(Statuts.ADHESION_SOUMISE);

        Statut adhesionAcceptee=new Statut();
        adhesionAcceptee.setCode("1.1");
        adhesionAcceptee.setLibelle(Statuts.ADHESION_ACCEPTEE);

        Statut adhesionRejetee=new Statut();
        adhesionRejetee.setCode("1.2");
        adhesionRejetee.setLibelle(Statuts.ADHESION_REJETEE);

        Statut demandeCessionSoumise=new Statut();
        demandeCessionSoumise.setCode("2.0");
        demandeCessionSoumise.setLibelle(Statuts.SOUMISE);

        Statut demandeCessionRecevable=new Statut();
        demandeCessionRecevable.setCode("2.1");
        demandeCessionRecevable.setLibelle(Statuts.RECEVABLE);

        Statut demandeCessionNonRecevable=new Statut();
        demandeCessionNonRecevable.setCode("2.2");
        demandeCessionNonRecevable.setLibelle(Statuts.REJETEE);

        Statut demandeCessionComplementRequis=new Statut();
        demandeCessionComplementRequis.setCode("3.1");
        demandeCessionComplementRequis.setLibelle(Statuts.COMPLEMENT_REQUIS);

        Statut demandeCessionCompletee=new Statut();
        demandeCessionCompletee.setCode("3.2");
        demandeCessionCompletee.setLibelle(Statuts.COMPLETEE);

        Statut demandeCessionNonRisquee=new Statut();
        demandeCessionNonRisquee.setCode("3.3");
        demandeCessionNonRisquee.setLibelle(Statuts.NON_RISQUEE);

        Statut demandeCessionRisquee=new Statut();
        demandeCessionRisquee.setCode("3.4");
        demandeCessionRisquee.setLibelle(Statuts.RISQUEE);

        Statut demandeCessionConventionGeneree=new Statut();
        demandeCessionConventionGeneree.setCode("4.0");
        demandeCessionConventionGeneree.setLibelle(Statuts.CONVENTION_GENEREE);


        Statut demandeCessionConventionCorrigee=new Statut();
        demandeCessionConventionCorrigee.setCode("4.1");
        demandeCessionConventionCorrigee.setLibelle(Statuts.CONVENTION_CORRIGEE);

        Statut demandeCessionConventionSigneePme=new Statut();
        demandeCessionConventionSigneePme.setCode("4.2");
        demandeCessionConventionSigneePme.setLibelle(Statuts.CONVENTION_SIGNEE_PAR_PME);

        Statut demandeCessionConventionSigneeDG=new Statut();
        demandeCessionConventionSigneeDG.setCode("4.3");
        demandeCessionConventionSigneeDG.setLibelle(Statuts.CONVENTION_SIGNEE_PAR_DG);

        Statut demandeCessionConventionAcceptee=new Statut();
        demandeCessionConventionAcceptee.setCode("4.4");
        demandeCessionConventionAcceptee.setLibelle(Statuts.CONVENTION_ACCEPTEE);

        Statut demandeCessionConventionRejetee=new Statut();
        demandeCessionConventionRejetee.setCode("4.5");
        demandeCessionConventionRejetee.setLibelle(Statuts.CONVENTION_REJETEE);

        Statut demandeCessionPMEAttentePaiement=new Statut();
        demandeCessionPMEAttentePaiement.setCode("5.0.1");
        demandeCessionPMEAttentePaiement.setLibelle(Statuts.PME_EN_ATTENTE_DE_PAIEMENT);

        Statut demandeCessionCDMPAttentePaiement=new Statut();
        demandeCessionCDMPAttentePaiement.setCode("5.0.2");
        demandeCessionCDMPAttentePaiement.setLibelle(Statuts.CDMP_EN_ATTENTE_DE_PAIEMENT);

        Statut demandeCessionPMEPaiementEnCours=new Statut();
        demandeCessionPMEPaiementEnCours.setCode("5.1.1");
        demandeCessionPMEPaiementEnCours.setLibelle(Statuts.PME_PARTIELLEMENT_PAYEE);

        Statut demandeCessionCDMPPaiementEnCours=new Statut();
        demandeCessionCDMPPaiementEnCours.setCode("5.1.2");
        demandeCessionCDMPPaiementEnCours.setLibelle(Statuts.CDMP_PARTIELLEMENT_PAYEE);

        Statut demandeCessionPMEPayee=new Statut();
        demandeCessionPMEPayee.setCode("5.2.1");
        demandeCessionPMEPayee.setLibelle(Statuts.PME_TOTALEMENT_PAYEE);

        Statut demandeCessionCDMPPayee=new Statut();
        demandeCessionCDMPPayee.setCode("5.2.2");
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
