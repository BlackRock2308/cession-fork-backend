/**
 * 
 */
package sn.modelsis.cdmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="demandeCessionid")
    private DemandeCession demandeCession;

    @OneToMany(mappedBy = "paiement", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    
  @OneToOne
  private Statut statutPme;

  @OneToOne
  private Statut statutCDMP;

  @OneToMany(mappedBy = "paiement",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DetailPaiement> detailPaiements = new HashSet<>();

}
