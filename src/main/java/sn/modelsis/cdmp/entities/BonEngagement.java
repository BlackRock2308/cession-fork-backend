package sn.modelsis.cdmp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Long montantCreance;
    
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
    private LocalDateTime dateBonEngagement;
    
    @Column(name = "identificationcomptable")
    private String identificationComptable;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bonEngagement")
    private Set<Demande> demandes = new HashSet<>();

  @Column(name = "nommarche")
  private String nomMarche;

  @Column(name = "exercice")
  private String exercice;

  @Column(name = "designatinonbeneficiaire")
  private String designationBeneficiaire;

  @Column(name = "actiondestination")
  private String destinationAction;

  @Column(name = "activitedestination")
  private String destinationActivite;

  @Column(name = "typeDepense")
  private String typeDepense;

  @Column(name = "modeReglement")
  private String modeReglement;

  @Column(name = "dateSoumissionServiceDepensier")
  private LocalDateTime dateSoumissionServiceDepensier;
}
