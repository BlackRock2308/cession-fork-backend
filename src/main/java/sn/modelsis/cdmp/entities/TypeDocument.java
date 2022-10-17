package sn.modelsis.cdmp.entities;

public enum TypeDocument {
  CNI("CNI"), RCCM("RCCM"), NINEA("NINEA"), 
  BUSSINESS_PLAN("BUSSINESS_PLAN"), 
  CHEQUE("CHEQUE"), PREUVE_VIREMENT("PREUVE DE VIRREMENT"), 
  ETAT_FINANCIER("ETAT FINANCIER") , 
  FACTURE("FACTURE") ,
  DECHARGE("DECHARGE") ,

  AUTRE("AUTRE");

  private String value;

  TypeDocument(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}

