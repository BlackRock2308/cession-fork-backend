package sn.modelsis.cdmp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "bonEngagement")
public class BonEngagement implements Serializable {
    
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBonEngagement")
    private Long idBonEngagement;
    
    @Column(name = "montantCreance")
    private double montantCreance;
    
    @Column(name = "reference")
    private String reference;
    
    @Column(name = "naturePrestation")
    private String naturePrestation;
    
    @Column(name = "natureDepense")
    private String natureDepense;
    
    @Column(name = "objetDepense")
    private String objetDepense;
    
    @Column(name = "imputation")
    private String imputation;
    
    @Column(name = "dateBonEngagement")
    private Date dateBonEngagement;
    
    @Column(name = "identificationComptable")
    private String identificationComptable;
}
