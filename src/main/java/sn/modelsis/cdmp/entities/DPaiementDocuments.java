package sn.modelsis.cdmp.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vw_DPaiement_document")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("DETAIL_PAIEMENT")
@Immutable
public class DPaiementDocuments extends Documents {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static final String FOLDER_PATH = "DETAIL_PAIEMENT/%s";
  public static final String PROVENANCE = "DETAIL_PAIEMENT";
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_provenance", nullable = true, insertable = false, updatable = false)
  private DetailPaiement dp;  

}
