package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * @author SNDIAGNEF
 *
 */
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
