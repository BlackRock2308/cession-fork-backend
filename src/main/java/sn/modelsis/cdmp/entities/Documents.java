package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author SNDIAGNEF
 *
 */
@Entity
@Table(name = "document")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@DiscriminatorColumn(name = "provenance", discriminatorType = DiscriminatorType.STRING, length = 50)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("UNDEFINED")
public class Documents implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "public.document_sequence", strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "date_sauvegarde")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateSauvegarde;

  @Column(name = "url_file")
  private String urlFile;

  @Column(name = "provenance", updatable = false)
  private String provenance;
  
  @Column(name = "nom")
  private String nom;

 // @Enumerated(EnumType.STRING)
  @Column(name = "typeDocument")
  private String typeDocument;

  @Column(name = "id_provenance", updatable = false)
  private Long idprovenance;

}
