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
    
    @Column(name = "prenomrepresentant")
    private String prenomRepresentant;
    
    @Column(name = "nomrepresentant")
    private String nomRepresentant;
    
    @Column(name = "rccm")
    private String rccm;
    
    @Column(name = "adresse")
    private String adressePME;
    
    @Column(name = "telephone")
    private String telephonePME;
    
    @Column(name = "dateimmatriculation")
    private LocalDateTime dateImmatriculation;
    
    @Column(name = "centrefiscal")
    private String centreFiscal;
    
    @Column(name = "ninea")
    private String ninea;
    
    @Column(name = "raisonsocial")
    private String raisonSocial;
    
    @Column(name = "atd")
    private String atd;
    
    @Column(name = "nantissement")
    private String nantissement;
    
    @Column(name = "interdictionbancaire")
    private String interdictionBancaire;

    @Column(name = "identificationBudgetaire")
    private boolean identificationBudgetaire;
    
    @Column(name = "formejuridique")
    private String formeJuridique;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "codepin")
    private  int codePin;
    
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
    private int controle;

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
    private int effectifPermanent;

    @Column(name = "nombreetablissementsecondaires")
    private int nombreEtablissementSecondaires;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pme")
    private Set<Agent> agents = new HashSet<>();









    private Boolean hasninea;
    private Boolean isactive;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pme")
    private Set<Demande> demandes = new HashSet<>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pme")
    private Set<PMEDocuments> documents = new HashSet<>();
}
