package sn.modelsis.cdmp.entities;

public enum TypePaiement {

    SICA_CDMP("Paiement par la SICA à la CDMP") ,
    CDMP_PME("Paiement par la CDMP à la PME");

    private String value;

    TypePaiement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
