package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entities.Utilisateur;

import javax.persistence.Column;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString
public class PmeDto {
  private Long idPME;
  private String prenomRepresentant;
  private String nomRepresentant;
  private String rccm;
  private String adressePME;
  private String telephonePME;
  private Date dateImmatriculation;
  private String centreFiscal;
  private String ninea;
  private String raisonSocial;
  private String atd;
  private String nantissement;
  private String interdictionBancaire;
  private String formeJuridique;
  private String email;
  private int codePin;
  private String urlImageProfile;
  private String urlImageSignature;
  private Set<DocumentDto> documents = new HashSet<>();
  private Utilisateur utilisateur;

}

