package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Table(name = "role")
@Getter
@Setter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="libelle")
    private String libelle;

    @Column(name="description")
    private String description;


}
