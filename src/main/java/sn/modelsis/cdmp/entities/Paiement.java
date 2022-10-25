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

  @Column(name = "demande_id")
  private Long demandeId;

  
  @Column(name = "datepaiement")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime datePaiement;



  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="demandeId", nullable = true, updatable = false, insertable = false)
  private DemandeCession demandeCession;

  @OneToMany(mappedBy = "paiementId",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<DetailPaiement> detailPaiements = new HashSet<>();

}
