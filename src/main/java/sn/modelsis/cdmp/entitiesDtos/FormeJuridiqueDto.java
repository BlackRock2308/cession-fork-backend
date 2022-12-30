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
public class FormeJuridiqueDto {
  
  private Long id;

  private String code;
  
  private String libelle;

}
