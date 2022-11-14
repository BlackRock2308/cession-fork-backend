package sn.modelsis.cdmp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "observation")
public class Observation implements Serializable, Comparable<Observation> {
    /**
   * 
   */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Transient
    private Long idDemande;
    
    @Column(name = "libelle")
    private String libelle;

    @Column(name = "dateobservation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateObservation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="demandeid")
    private Demande demande;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="utilisateurid")
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name = "statut")
    private Statut statut;
    
    @Override
    public int compareTo(Observation o) {
      this.dateObservation.compareTo(o.dateObservation);
        return 0;
    }
}
