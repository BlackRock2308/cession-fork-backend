package sn.modelsis.cdmp.entities;

public enum Statuts {
    ADHESION_SOUMISE("ADHESION SOUMISE"),
    ADHESION_REJETEE("ADHESION REJETEE"),
    ADHESION_ACCEPTEE("ADHESION ACCEPTEE"),
    SOUMISE("SOUMISE"),
    RECEVABLE("RECEVABLE"),
    REJETEE("REJETEE"),
    RISQUEE("RISQUEE"), NON_RISQUEE("NON RISQUEE"),
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
    CDMP_TOTALEMENT_PAYEE("CDMP TOTALEMENT PAYEE")
    ;

    private String value;

    Statuts(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
