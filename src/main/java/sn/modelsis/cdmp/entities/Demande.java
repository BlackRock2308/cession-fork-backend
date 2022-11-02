package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "demande")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Demande implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long idDemande;

//    @Column(nullable = true)
//    private Long idPme;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pme_id")
    private Pme pme;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="statutid")
    private Statut statut;



    




    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "demande")
    private Set<DemandeDocuments> documents = new HashSet<>();




}
