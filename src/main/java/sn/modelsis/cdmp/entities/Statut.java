package sn.modelsis.cdmp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString @SuperBuilder
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
    
    @Column(name = "code",unique = true)
    private String code;

    @Column(name = "libelle",unique = true)
    private String libelle;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "statut")
    private Set<Demande> demandes = new HashSet<>() ;

    public Statut(Long idStatut, String code, String libelle) {
        this.idStatut = idStatut;
        this.code = code;
        this.libelle = libelle;
    }
}
