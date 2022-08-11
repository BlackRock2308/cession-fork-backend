/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Column(name = "decote")
  private String decote;
  
  @Column(name = "dateconvention")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime dateConvention; 
  
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "pmeid", nullable = true)
  private Pme pme;
  
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "agentid", nullable = true)
  private Agent agent;
  
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "convention")
  private Set<Demande> demandes = new HashSet<>();

}
