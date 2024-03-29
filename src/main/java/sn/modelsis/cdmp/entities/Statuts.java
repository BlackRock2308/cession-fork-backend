package sn.modelsis.cdmp.entities;

public enum Statuts {

    ADHESION_SOUMISE("ADHESION SOUMISE"),
    ADHESION_REJETEE("ADHESION REJETEE"),
    ADHESION_ACCEPTEE("ADHESION ACCEPTEE"),
    SOUMISE("SOUMISE"),
    RECEVABLE("RECEVABLE"),
    REJETEE("REJETEE"),
    RISQUEE("RISQUEE"),
    NON_RISQUEE("NON RISQUEE"),
    COMPLEMENT_REQUIS("COMPLEMENT REQUIS") ,
    COMPLETEE("COMPLETEE") ,
    CONVENTION_GENEREE("CONVENTION GENEREE") ,
    CONVENTION_SIGNEE_PAR_PME("CONVENTION SIGNEE PAR PME"),
    CONVENTION_REJETEE_PAR_PME("CONVENTION REJETEE PAR PME"),
    CONVENTION_SIGNEE_PAR_DG("CONVENTION SIGNEE PAR DG") ,
    CONVENTION_REJETEE_PAR_DG("CONVENTION REJETEE PAR DG"),
    CONVENTION_TRANSMISE("CONVENTION TRANSMISE"),
    CONVENTION_REJETEE("CONVENTION REJETEE"),
    CONVENTION_CORRIGEE("CONVENTION CORRIGEE"),
    CONVENTION_ACCEPTEE("CONVENTION ACCPTEE"),
    PME_EN_ATTENTE_DE_PAIEMENT("PME EN ATTENTE DE PAIEMENT"),
    CDMP_EN_ATTENTE_DE_PAIEMENT("CDMP EN ATTENTE DE PAIEMENT"),
    PME_PARTIELLEMENT_PAYEE("PME PARTIELLEMENT PAYEE"),
    CDMP_PARTIELLEMENT_PAYEE("CDMP PARTIELLEMENT PAYEE"),
    PME_TOTALEMENT_PAYEE("PME TOTALEMENT PAYEE"),
    CDMP_TOTALEMENT_PAYEE("CDMP TOTALEMENT PAYEE"),

    ConventionTransmise ("CONVENTION_TRANSMISE"),
    ConventionRejeteeParDG ("CONVENTION_REJETEE_PAR_DG"),
    ConventionRejeteeParPME ("CONVENTION_REJETEE_PAR_PME"),
    ConventionRejetee ("CONVENTION_REJETEE"),
    ConventionAcceptee ("CONVENTION_ACCEPTEE"),
    conventionGeneree ("CONVENTION_GENEREE"),
    conventionSigneeParPME ("CONVENTION_SIGNEE_PAR_PME"),
    conventionSigneeParDG ("CONVENTION_SIGNEE_PAR_DG"),
    conventionCorrigee ("CONVENTION_CORRIGEE"),

    adhesionSoumise ("ADHESION_SOUMISE"),
    adhesionRejetee ("ADHESION_REJETEE"),
    adhesionAcceptee ("ADHESION_ACCEPTEE"),

    soumise ("SOUMISE"),
    recevable ("RECEVABLE"),
    rejetee ("REJETEE"),
    risquee ("RISQUEE"),
    nonRisquee ("NON_RISQUEE"),
    complementRequis ("COMPLEMENT_REQUIS"),
    completee ("COMPLETEE"),

    pmeEnAttenteDePaiement ("PME_EN_ATTENTE_DE_PAIEMENT"),
    cdmpEnAttenteDePaiement ("CDMP_EN_ATTENTE_DE_PAIEMENT"),
    pmePartiellementPayee ("PME_PARTIELLEMENT_PAYEE"),
    cdmpPartiellementPayee ("CDMP_PARTIELLEMENT_PAYEE"),
    pmeTotalementPayee ("PME_TOTALEMENT_PAYEE"),
    cdmpTotalementPayee ("CDMP_TOTALEMENT_PAYEE");
    private String value;

    Statuts(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
