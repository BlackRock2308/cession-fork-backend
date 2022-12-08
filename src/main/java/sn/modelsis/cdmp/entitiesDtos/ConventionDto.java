package sn.modelsis.cdmp.entitiesDtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SNDIAGNEF
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString @Getter @Setter
public class ConventionDto {
 
  private Long idConvention;
  
  private String modePaiement;

  private Long idDemande;

  private double valeurDecoteByDG;

  private double valeurDecote;

  private boolean activeConvention;

  private ParametrageDecoteDTO decoteDTO;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateConvention;
  
  private PmeDto pmeDto;

  private UtilisateurDto utilisatuerDto;

  private Long utilisatuerId;

  private Set<DocumentDto> documents = new HashSet<>();

}
