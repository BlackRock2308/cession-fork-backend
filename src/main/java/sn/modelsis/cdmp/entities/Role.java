package sn.modelsis.cdmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

   // @Enumerated(EnumType.STRING)
    @Column(name="libelle")
    private String libelle;
    // @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "roleid")
    // private Set<RoleUtilisateur> rolesUtilisateurs = new HashSet<>();
}
