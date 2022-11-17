/**
 * 
 */
package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author SNDIAGNEF
 *
 */
@Entity
@Table(name = "parametrage")
@Getter
@Setter
@NoArgsConstructor
public class Parametrage implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idParam;

  @Column(name = "code")
  private String code;
  
  @Column(name = "valeur")
  private String valeur;
 
}
