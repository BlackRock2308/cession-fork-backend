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
@ToString
@Table(name = "demandeadhesion")
public class DemandeAdhesion implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idDemande;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pmeid")
    private Pme pme;

    @Column(name = "datedemande")
    private Date dateDemandeAdhesion;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="statutid")
    private Statut statut;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "demandeadhesion")
    private Set<Observation> observations = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "demandeadhesion")
    private Set<DemandeDocuments> documents = new HashSet<>();
}
