package sn.modelsis.cdmp.entities;

public enum Roles {
    CGR("cgr"),
    PME("pme"),
    COMPTABLE("comptable"),
    ORDONNATEUR("ordonnateur"),
    DG("dg");

    private String value;

    Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
