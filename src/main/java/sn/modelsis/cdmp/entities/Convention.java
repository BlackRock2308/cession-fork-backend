/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author SNDIAGNEF
 *
 */
@Entity
@Table(name = "convention")
@Getter
@Setter
@NoArgsConstructor
public class Convention implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idConvention;
  
  @Column(name = "modepaiement")
  @Enumerated(EnumType.STRING)
  private ModePaiement modePaiement;

  private double valeurDecote;

  @Column(name = "valeur_decote_dg")
  private double valeurDecoteByDG;

  @Column(name = "active_convention",columnDefinition = "boolean default true")
  private boolean activeConvention = true;

  @Column(name = "dateconvention")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateConvention;
  
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "pmeid", nullable = true)
  private Pme pme;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnore
  @JoinColumn(name = "utilisateurid", nullable = true)
  private Utilisateur utilisateur;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="demandeid")
  private DemandeCession demandeCession;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="decoteid")
  private ParametrageDecote decote;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "convention")
  private Set<ConventionDocuments> documents = new HashSet<>();


}
