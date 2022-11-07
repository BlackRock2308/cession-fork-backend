/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;

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
  private double montantRecuCDMP;

  @Column(name = "soldepme")
  private double soldePME;

  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name="demandeId", nullable = true, updatable = false, insertable = false)
  private DemandeCession demandeCession;

  @OneToMany(mappedBy = "paiement",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DetailPaiement> detailPaiements = new HashSet<>();


}
