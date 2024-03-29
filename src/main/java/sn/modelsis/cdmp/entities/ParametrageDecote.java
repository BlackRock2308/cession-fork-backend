package sn.modelsis.cdmp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
@Table(name="parametrage_decote")
public class ParametrageDecote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long IdDecote;

    @Column(name = "borne_inf",unique = true)
    private Long borneInf;

    @Column(name = "borne_sup", unique = true)
    private Long borneSup;

    @Column(name = "decote_value")
    private Double decoteValue;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL ,mappedBy = "decote")
    private Set<Convention> conventions = new HashSet<>();

    public ParametrageDecote(Long borneInf,
                             Long borneSup,
                             Double decoteValue) {
        this.borneInf = borneInf;
        this.borneSup = borneSup;
        this.decoteValue = decoteValue;
    }
}
