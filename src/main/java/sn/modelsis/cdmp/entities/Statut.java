package sn.modelsis.cdmp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "bonEngagement")
public class Statut implements Serializable {
    /**
   * 
   */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idStatut")
    private Long idStatut;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "libelle")
    private String libelle;
}
