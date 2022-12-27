package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
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

    @Column(name = "montantCreance")
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
    
    @Column(name = "nomMarche")
    private String nomMarche;

    @Column(name = "typeMarche")
    @Enumerated( EnumType.STRING)
    private TypeMarche typeMarche;
    
    @Column(name = "typeDepense")
    private String typeDepense;

    @Column(name = "modeReglement")
    private String modeReglement;

    @Column(name = "datebonengagement")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime datebonengagement;

    @Column(name = "exercice")
    private String exercice;

    @Column(name = "designationBeneficiaire")
    private String designationBeneficiaire;

    @Column(name = "actionDestination")
    private String destinationAction;

    @Column(name = "activiteDestination")
    private String destinationActivite;

    @Column(name = "dateSoumissionServiceDepensier")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateSoumissionServiceDepensier;

    @Column(name = "identificationcomptable")
    private String identificationComptable;
    
    @OneToMany(orphanRemoval = true,fetch = FetchType.EAGER, mappedBy = "bonEngagement")
    private Set<DemandeCession> demandeCessions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bonEngagement")
    private Set<BEDocuments> documents = new HashSet<>();

}
