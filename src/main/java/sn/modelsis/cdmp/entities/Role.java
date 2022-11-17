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
