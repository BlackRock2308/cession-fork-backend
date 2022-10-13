/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author SNDIAGNEF
 *
 */
@Entity
@Table(name = "agent")
@Getter
@Setter
@NoArgsConstructor
public class Agent implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idAgent;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id", nullable = true)
  private Utilisateur idUtilisateur;


  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "agent")
  private Set<Observation> observations = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="id" , insertable = false,updatable = false)
  private Pme pme;
}
