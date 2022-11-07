package sn.modelsis.cdmp.entities;

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
//@ToString
@Table(name = "demandecession")
public class DemandeCession extends Demande{

    @Column(name = "datedemandecession")
    private Date dateDemandeCession;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bonengagement_id")
    private BonEngagement bonEngagement;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL ,mappedBy = "demandeCession")
    private Set<Convention> conventions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Observation> observations = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Paiement paiement;
}

