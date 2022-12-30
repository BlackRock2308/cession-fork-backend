package sn.modelsis.cdmp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name="centreDesServicesFiscaux")
public class CentreDesServicesFiscaux implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;
    
    @Column(name = "libelle")
    private String libelle;

    
}
