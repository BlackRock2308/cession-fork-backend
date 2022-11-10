package sn.modelsis.cdmp.entities;

public enum Roles {
    DRC("drc"),
    PME("pme"),
    DAF("daf"),
    JURISTE("juriste"),
    DSEAR("dsear"),
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
