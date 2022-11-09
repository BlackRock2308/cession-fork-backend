package sn.modelsis.cdmp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name="parametrage_decote")
public class ParametrageDecote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long IdDecote;

    @Column(name = "borne_inferieure")
    private Long borneInf;

    @Column(name = "borne_superieure")
    private Long borneSup;

    @Column(name = "valeur_decote")
    private Double decoteValue;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL ,mappedBy = "decote_id")
    private Set<Convention> conventions = new HashSet<>();
}
