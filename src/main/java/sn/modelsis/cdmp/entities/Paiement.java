/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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

  @Column(name = "montantrecucdmp")
  private Long montantRecuCDMP;

  @Column(name = "soldepme")
  private Long soldePME;

  
  @Column(name = "datepaiement")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime datePaiement; 
  



  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "demandeid", nullable = false)
  private DemandeCession demandecession;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "paiement")
  private Set<DetailPaiement> detailPaiements = new HashSet<>();

}
