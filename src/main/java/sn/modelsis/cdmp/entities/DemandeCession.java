package sn.modelsis.cdmp.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
@Table(name = "demandecession")
public class DemandeCession extends Demande{

    @Column(name = "datedemandecession")
    private Date dateDemandeCession;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="bonengagementid")
    private BonEngagement bonEngagement;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL ,mappedBy = "demandeCession")
    private Set<Convention> conventions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL ,mappedBy = "demande")
    private Set<Observation> observations = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="paiementid")
    private Paiement paiement;
}

