package sn.modelsis.cdmp.entitiesDtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ObservationDto {
  
  private Long id;

  private Long idDemande;
  
  private String libelle;

  private StatutDto statut;

  private DemandeCessionDto demande;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateObservation;

}
