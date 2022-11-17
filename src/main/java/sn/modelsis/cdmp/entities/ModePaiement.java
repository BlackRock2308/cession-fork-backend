package sn.modelsis.cdmp.entities;


public enum ModePaiement {
  CHEQUE("CHEQUE"),
  ESPECE("ESPECE"),
  VIREMENT("virement");
  
  private String value;

  ModePaiement(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}

