package sn.modelsis.cdmp.entitiesDtos;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ObservationDto {
  
  private Long id;
  
  private String libelle;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateObservation; 

  private DemandeDto demandeDto;

  private AgentDto agentDto;

}
