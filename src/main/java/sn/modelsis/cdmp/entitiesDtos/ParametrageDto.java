package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ParametrageDto {
  
  private Long idParam;

  private String code;
  
  private String valeur;

}
