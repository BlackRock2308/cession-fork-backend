package sn.modelsis.cdmp.entitiesDtos;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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

  //private boolean activeConvention;

  private UtilisateurDto utilisatuerDto;

  private Long utilisatuerId;

  private Set<DocumentDto> documents = new HashSet<>();

}
