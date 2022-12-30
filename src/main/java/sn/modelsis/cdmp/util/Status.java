package sn.modelsis.cdmp.util;

public class Status {
    String conventionTransmise;
    String conventionRejeteeParDG;
    String conventionRejeteeParPME;
    String conventionRejetee;
    String conventionAcceptee;
    String conventionGeneree;
    String conventionSigneeParPME;
    String conventionSigneeParDG;
    String conventionCorrigee;

    String adhesionSoumise;
    String adhesionRejetee;
    String adhesionAcceptee;

    String completee;
    String complementRequis;
    String nonRisquee;
    String risquee;
    String rejetee;
    String recevable;
    String soumise;

    String cdmpTotalementPayee;
    String pmeTotalementPayee;
    String cdmpPartiellementPayee;
    String pmePartiellementPayee;
    String cdmpEnAttenteDePaiement;
    String pmeEnAttenteDePaiement;

    public static String getConventionTransmise() {
        return "CONVENTION_TRANSMISE";
    }

    public static String getConventionRejeteeParDG() {
        return "CONVENTION_REJETEE_PAR_DG";
    }

    public static String getConventionRejeteeParPME() {
        return "CONVENTION_REJETEE_PAR_PME";
    }

    public static String getConventionRejetee() {
        return "CONVENTION_REJETEE";
    }

    public static String getConventionAcceptee() {
        return "CONVENTION_ACCEPTEE";
    }

    public static String getConventionGeneree() {
        return "CONVENTION_GENEREE";
    }

    public static String getConventionSigneeParPME() {
        return "CONVENTION_SIGNEE_PAR_PME";
    }

    public static String getConventionSigneeParDG() {
        return "CONVENTION_SIGNEE_PAR_DG";
    }

    public static String getConventionCorrigee() {
        return "CONVENTION_CORRIGEE";
    }

    public static String getAdhesionSoumise() {
        return "ADHESION_SOUMISE";
    }

    public static String getAdhesionRejetee() {
        return "ADHESION_REJETEE";
    }

    public static String getAdhesionAcceptee() {
        return "ADHESION_ACCEPTEE";
    }

    public static String getCompletee() {
        return "COMPLETEE";
    }

    public static String getComplementRequis() {
        return "COMPLEMENT_REQUIS";
    }

    public static String getNonRisquee() {
        return "NON_RISQUEE";
    }

    public static String getRisquee() {
        return "RISQUEE";
    }

    public static String getRejetee() {
        return "REJETEE";
    }

    public static String getRecevable() {
        return "RECEVABLE";
    }

    public static String getSoumise() {
        return "SOUMISE";
    }

    public static String getCdmpTotalementPayee() {
        return "CDMP_TOTALEMENT_PAYEE";
    }

    public static String getPmeTotalementPayee() {
        return "PME_TOTALEMENT_PAYEE";
    }

    public static String getCdmpPartiellementPayee() {
        return "CDMP_PARTIELLEMENT_PAYEE";
    }

    public static String getPmePartiellementPayee() {
        return "PME_PARTIELLEMENT_PAYEE";
    }

    public static String getCdmpEnAttenteDePaiement() {
        return "CDMP_EN_ATTENTE_DE_PAIEMENT";
    }

    public static String getPmeEnAttenteDePaiement() {
        return "PME_EN_ATTENTE_DE_PAIEMENT";
    }

}
