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
@Table(name = "pme")
public class Pme implements Serializable {
  
    /**
   * 
   */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPME")
    private Long idPME;
    
    @Column(name = "prenomRepresentant")
    private String prenomRepresentant;
    
    @Column(name = "nomRepresentant")
    private String nomRepresentant;
    
    @Column(name = "rccm")
    private String rccm;
    
    @Column(name = "adressePME")
    private String adressePME;
    
    @Column(name = "telephonePME")
    private String telephonePME;
    
    @Column(name = "dateImmatriculation")
    private Date dateImmatriculation;
    
    @Column(name = "centreFiscal")
    private String centreFiscal;
    
    @Column(name = "ninea")
    private String ninea;
    
    @Column(name = "raisonSocial")
    private String raisonSocial;
    
    @Column(name = "atd")
    private String atd;
    
    @Column(name = "nantissement")
    private String nantissement;
    
    @Column(name = "interdictionBancaire")
    private String interdictionBancaire;
    
    @Column(name = "formeJuridique")
    private String formeJuridique;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "codePin")
    private  int codePin;
    
    @Column(name = "urlImageProfile")
    private String urlImageProfile;
    
    @Column(name = "urlImageSignature")
    private String urlImageSignature;
    
}
