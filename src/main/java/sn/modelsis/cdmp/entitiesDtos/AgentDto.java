package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AgentDto {
  
  private Long idAgent;

  private String nom;
  
  private String prenom;
  
  private String adresse;
  
  private int codePin;
  
  private String urlImageSignature;
  
  private String telephone;
  
  private String email;

  private String urlImageProfil;

}
