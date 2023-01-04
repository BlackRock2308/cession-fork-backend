/**
 * 
 */
package sn.modelsis.cdmp.entitiesDtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;
import sn.modelsis.cdmp.entities.TypeDocument;

/**
 * @author SNDIAGNEF
 *
 */
@Getter
@Setter
public class DocumentDto {
  
  private Long id;
  
  private String nom;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime dateSauvegarde;
  
  private byte[] documentScanee;

  private String documentScaneeContentType;

  private String urlFile;

  private String typeDocument;
  
}
