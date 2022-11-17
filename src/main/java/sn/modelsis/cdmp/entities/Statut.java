package sn.modelsis.cdmp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "statut")
public class Statut implements Serializable {
    /**
   * 
   */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idStatut;
    
    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "statut")
    private Set<Demande> demandes = new HashSet<>() ;
}
