package sn.modelsis.cdmp.entitiesDtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author SNDIAGNEF
 *
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data @SuperBuilder
public class ObservationDto implements Serializable {
  
  private Long id;

  private Long utilisateurid;

  private Long demandeid;
  
  private String libelle;

  private StatutDto statut;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateObservation;

}
