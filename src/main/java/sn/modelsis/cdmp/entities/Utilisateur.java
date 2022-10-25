package sn.modelsis.cdmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
public class Utilisateur implements Serializable {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "idutilisateur")
   private Long idUtilisateur;

   @Column(name="adresse")
   private String adresse;

   @Column(name="codePin")
   private String codePin;

   @Column(name="prenom")
   private String prenom;

   @Column(name="nom")
   private String nom;

   @Column(name="urlImagesignature")
   private String urlImageSignature;

   @Column(name="telephone")
   private String telephone;

   @Column(name="email")
   private String email;

   @Column(name="urlimageprofil")
   private String urlImageProfil;

   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "utilisateurid")
   private Set<RoleUtilisateur> rolesUtilisateurs = new HashSet<>();

   
}
