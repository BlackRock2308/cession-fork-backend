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
@Table(name="vw_convention_document")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("CONVENTION")
@Immutable
public class ConventionDocuments extends Documents {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static final String FOLDER_PATH = "CONVENTION/%s";
  public static final String PROVENANCE = "CONVENTION";
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_provenance", nullable = true, insertable = false, updatable = false)
  private Convention convention;  

}
