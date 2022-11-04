package sn.modelsis.cdmp.entitiesDtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
public class ConventionDto {
 
  private Long idConvention;
  
  private String modePaiement;

  private String decote;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateConvention; 
  
  private PmeDto pmeDto;

  private Long demandeCessionId ;

  private boolean activeConvention;

  private UtilisateurDto utilisatuerDto;
  
  private Set<DocumentDto> documents = new HashSet<>();

}
