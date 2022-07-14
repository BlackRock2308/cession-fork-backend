/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
  @Column(name = "idAgent")
  private Long idAgent;

  @Column(name = "nom")
  private String nom;
  
  @Column(name = "prenom")
  private String prenom;
  
  @Column(name = "adresse")
  private String adresse;
  
  @Column(name = "codePin")
  private int codePin;
  
  @Column(name = "urlImageSignature")
  private String urlImageSignature;
  
  @Column(name = "telephone")
  private String telephone;
  
  @Column(name = "email")
  private String email;

  @Column(name = "urlImageProfil")
  private String urlImageProfil;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "agent")
  private Set<Observation> observations = new HashSet<>();


}
