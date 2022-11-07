package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
@Table(name = "pme")
public class Pme implements Serializable {
  
    /**
   * 
   */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idPME;

    @NotNull
    @Column(name = "rccm", unique = true)
    private String rccm;
    
    @Column(name = "adresse")
    private String adressePME;
    
    @Column(name = "telephone", unique = true)
    private String telephonePME;
    
    @Column(name = "dateimmatriculation")
    private LocalDateTime dateImmatriculation;
    
    @Column(name = "centrefiscal")
    private String centreFiscal;

    @NotEmpty
    @Column(name = "ninea", unique = true)
    private String ninea;
    
    @Column(name = "raisonsocial")
    private String raisonSocial;
    
    @Column(name = "atd")
    private boolean atd;
    
    @Column(name = "nantissement")
    private boolean nantissement;
    
    @Column(name = "interdictionbancaire")
    private boolean interdictionBancaire;

    @Column(name = "identificationBudgetaire")
    private boolean identificationBudgetaire;
    
    @Column(name = "formejuridique")
    private String formeJuridique;

    @Email
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "codepin")
    private  Integer codePin;
    
    @Column(name = "urlimageprofil")
    private String urlImageProfile;
    
    @Column(name = "urlimagesignature")
    private String urlImageSignature;

    @Column(name = "datedemandeadhesion")
    private LocalDateTime dateAdhesion;

    @Column(name = "enseigne")
    private String enseigne;

    @Column(name = "localite")
    private String localite;

    @Column(name = "controle")
    private Integer controle;

    @Column(name = "activiteprincipale")
    private String activitePrincipale;

    @Column(name = "autorisationministerielle")
    private String autorisationMinisterielle;

    @Column(name = "datecreation")
    private LocalDateTime dateCreation;

    @Column(name = "capitalsocial")
    private String capitalSocial;

    @Column(name = "chiffresdaffaires")
    private Long chiffresDaffaires;

    @Column(name = "effectifpermanent")
    private Integer effectifPermanent;

    @Column(name = "nombreetablissementsecondaires")
    private Integer nombreEtablissementSecondaires;

    @Column(name = "hasninea")
    private Boolean hasninea;
    
    @Column(name = "isactive")
    private Boolean isactive;

    @OneToMany(mappedBy = "pme",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Demande> demandes = new HashSet<>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pme")
    private Set<PMEDocuments> documents = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Utilisateur utilisateur ;
}
