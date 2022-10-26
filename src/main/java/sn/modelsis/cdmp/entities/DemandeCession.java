package sn.modelsis.cdmp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "demandecession")
public class DemandeCession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idDemande;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pmeid")
    private Pme pme;

    @Column(name = "datedemandecession")
    private Date dateDemandeCession;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="statutid")
    private Statut statut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bonengagementid")
    private BonEngagement bonEngagement;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "demandeCession")
    private Set<Convention> conventions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "demandecession")
    private Set<Observation> observations = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "demandeid", nullable = false)
    private Demande demande;

   /* @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "demandecession")
    private Set<DemandeDocuments> documents = new HashSet<>();*/
}

