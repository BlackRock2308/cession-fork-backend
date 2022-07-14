/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author SNDIAGNEF
 *
 */
@Entity
@Table(name = "convention")
@Getter
@Setter
@NoArgsConstructor
public class Convention implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idConvention")
  private Long idConvention;
  
  @Column(name = "modePaiement")
  private ModePaiement modePaiement;

  @Column(name = "decote")
  private String decote;
  
  @Column(name = "dateConvention")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateConvention; 
  
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "idPME", nullable = true)
  private Pme pme;
  
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "idAgent", nullable = true)
  private Agent agent;

}
