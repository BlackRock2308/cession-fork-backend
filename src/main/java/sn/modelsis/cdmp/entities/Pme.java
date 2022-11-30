package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name ="prenomrepresentant")
    private String prenomRepresentant;
    
    @Column(name ="nomrepresentant")
    private String nomRepresentant;
    @NotNull
    @Column(name = "rccm")
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
    @Column(name = "ninea")
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
    @Column(name = "email")
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
    @JoinColumn(name = "utilisateurid")
    private Utilisateur utilisateur ;
    
    @Column(name = "cnirepresentant")
    private String cniRepresentant ;
    
    @Column(name = "registre")
    private String registre;
    @Transient
    private Long utilisateurid;

    public void setPME(Long idPME,

    String prenomRepresentant,

   String nomRepresentant,
     String rccm,

   String adressePME,

     String telephonePME,

     LocalDateTime dateImmatriculation,

     String centreFiscal,

     String ninea,

    String raisonSocial,

     boolean atd,

     boolean nantissement,

    boolean interdictionBancaire,

     boolean identificationBudgetaire,

     String formeJuridique,

     String email,

     Integer codePin,

     String urlImageProfile,

     String urlImageSignature,

     LocalDateTime dateAdhesion,

     String enseigne,

    String localite,

     Integer controle,

    String activitePrincipale,

     String autorisationMinisterielle,

    LocalDateTime dateCreation,

     String capitalSocial,

    Long chiffresDaffaires,

     Integer effectifPermanent,

    Integer nombreEtablissementSecondaires,

     Boolean hasninea,

    Boolean isactive,

     Set<Demande> demandes ,

    Set<PMEDocuments> documents ,

    Utilisateur utilisateur ,

    String cniRepresentant ,

     String registre,

     Long utilisateurid){
        this.idPME=idPME;
        this.prenomRepresentant=prenomRepresentant;
                this.nomRepresentant=nomRepresentant;
                this.rccm=rccm;

                this.adressePME=adressePME;
                this.telephonePME=telephonePME;

                this.dateImmatriculation=dateImmatriculation;

                this.centreFiscal=centreFiscal;

                this.ninea=ninea;

                this.raisonSocial=raisonSocial;

        this.atd=atd;

        this.nantissement=nantissement;

        this.interdictionBancaire=interdictionBancaire;

        this.identificationBudgetaire=identificationBudgetaire;

        this.formeJuridique=formeJuridique;

        this.email=email;

        this.codePin=codePin;

        this.urlImageProfile=urlImageProfile;

        this.urlImageSignature=urlImageSignature;

        this.dateAdhesion=dateAdhesion;

        this.enseigne=enseigne;

        this.localite=localite;

        this.controle=controle;

        this.activitePrincipale=activitePrincipale;

        this.autorisationMinisterielle=autorisationMinisterielle;

        this.dateCreation=dateCreation;

        this.capitalSocial=capitalSocial;

        this.chiffresDaffaires=chiffresDaffaires;

        this.effectifPermanent=effectifPermanent;

        this.nombreEtablissementSecondaires=nombreEtablissementSecondaires;

        this.hasninea=hasninea;

        this.isactive=isactive;

        this.demandes=demandes;

        this.documents=documents;

        this.utilisateur=utilisateur;

        this.cniRepresentant=cniRepresentant;

        this.registre=registre;

        this.utilisateurid=utilisateurid;

    }

}
