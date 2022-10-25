package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
public class Demande implements Serializable {
    /**
   * 
   */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idDemande;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pmeid")
    private Pme pme;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="statutid")
    private Statut statut;


    




    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "demande")
    private Set<DemandeDocuments> documents = new HashSet<>();




}
