package sn.modelsis.cdmp.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vw_PME_document")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("PME")
public class PMEDocuments extends Documents {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static final String FOLDER_PATH = "PME/%s";
  public static final String PROVENANCE = "PME";
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_provenance", nullable = true, insertable = false, updatable = false)
  private Pme pme;  

}
