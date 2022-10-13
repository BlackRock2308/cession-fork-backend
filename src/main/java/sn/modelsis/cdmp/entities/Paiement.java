/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author SNDIAGNEF
 *
 */
@Entity
@Table(name = "paiement")
@Getter
@Setter
@NoArgsConstructor
public class Paiement implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idPaiement;
  
//  @Column(name = "montant")
//  private double montant;

  @Column(name = "montantRecuCDMP")
  private double montantRecuCDMP;

  @Column(name = "soldePME")
  private double soldePME;

  
  @Column(name = "datepaiement")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime datePaiement; 
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "statutid", nullable = false)
  private Statut statut;

}
