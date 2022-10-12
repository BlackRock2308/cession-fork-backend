package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @Column(name = "id")
    private Long idBonEngagement;
    
    @Column(name = "montantcreance")
    private double montantCreance;
    
    @Column(name = "reference")
    private String reference;
    
    @Column(name = "natureprestation")
    private String naturePrestation;
    
    @Column(name = "naturedepense")
    private String natureDepense;
    
    @Column(name = "objetdepense")
    private String objetDepense;
    
    @Column(name = "imputation")
    private String imputation;
    
    @Column(name = "datebonengagement")
    private Date dateBonEngagement;
    
    @Column(name = "identificationcomptable")
    private String identificationComptable;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bonEngagement")
    private Set<Demande> demandes = new HashSet<>();
<<<<<<< HEAD
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bonEngagement")
    private Set<BEDocuments> documents = new HashSet<>();
=======

  @Column(name = "nomMarche")
  private String nomMarche;

  @Column(name = "exercice")
  private int exercice;

  @Column(name = "designationBeneficiare")
  private String designationBeneficiaire;

  @Column(name = "destinationAction")
  private String destinationAction;

  @Column(name = "destinationActivite")
  private String destinationActivite;

  @Column(name = "typeDepense")
  private String typeDepense;

  @Column(name = "modeReglement")
  private String modeReglement;

  @Column(name = "dateSoumissionServiceDepensier")
  private String dateSoumissionServiceDepensier;
>>>>>>> f39963a62aebecbdadd6342b6b3c37854e0fe58e
}
