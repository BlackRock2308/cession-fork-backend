package sn.modelsis.cdmp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "demande")
public class Demande implements Serializable {
    /**
   * 
   */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idDemande;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dateDemandeCession")
    private Date dateDemandeCession;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pmeid")
    private Pme pme;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="statutid")
    private Statut statut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bonengagementid")
    private BonEngagement bonEngagement;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="conventionid")
    private Convention convention;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "demande")
    private Set<Observation> observations = new HashSet<>();
}
