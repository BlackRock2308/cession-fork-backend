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
public class ParametrageDto {
  
  private Long idParam;

  private String code;
  
  private String valeur;

}
