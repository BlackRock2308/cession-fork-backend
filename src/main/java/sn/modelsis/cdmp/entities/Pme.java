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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
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
    private Date dateImmatriculation;
    
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
    private Boolean identificationBudgetaire;
    
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

    @Column(name = "dateDemandeAdhesion")
    private Date dateAdhesion;

    @Column(name = "enseigne")
    private String enseigne;

    @Column(name = "localite")
    private String localite;

    @Column(name = "controle")
    private int controle;

    @Column(name = "activitePrincipal")
    private String activitePrincipale;

    @Column(name = "autorisationMinisterielle")
    private String autorisationMinisterielle;

    @Column(name = "dateCreation")
    private Date dateCreation;

    @Column(name = "capitalSocial")
    private String capitalSocial;

    @Column(name = "chiffresDaffaires")
    private int chiffresDaffaires;

    @Column(name = "effectifPermanent")
    private int effectifPermanent;

    @Column(name = "nombreEtablissementSecondaires")
    private int nombreEtablissementSecondaires;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agentid", nullable = true)
    private Agent agentId;
//
//
//    private Boolean hasNinea;
//    private Boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pme")
    private Set<Demande> demandes = new HashSet<>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pme")
    private Set<PMEDocuments> documents = new HashSet<>();
}
