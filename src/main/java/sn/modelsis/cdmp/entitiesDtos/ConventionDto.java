package sn.modelsis.cdmp.entitiesDtos;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ConventionDto {
 
  private Long idConvention;
  
  private String modePaiement;

  private String decote;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateConvention; 
  
  private PmeDto pmeDto;

  private AgentDto agentDto;

}
