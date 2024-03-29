package sn.modelsis.cdmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
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

   @Column(name="email",nullable = false,unique = true)
   private String email;

   @Column(name="password")
   private String password;

   @Column(name="urlimageprofil")
   private String urlImageProfil;

   @Column(name="update_password")
   private boolean updatePassword;

   @Column(name="update_codepin")
   private boolean updateCodePin;

   @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
   @JoinColumn(name="ministeredepid")
   private MinistereDepensier ministere;
   
   @ManyToMany(fetch = FetchType.EAGER,cascade =CascadeType.DETACH)
   private Set<Role> roles ;

   @Column(name="active")
   private boolean active = true;

 //  @OneToMany(mappedBy = "utilisateur",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
 //  private Set<Observation> observations = new HashSet<>();
//
//   @OneToMany(mappedBy = "utilisateur",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//   private Set<Convention> conventions = new HashSet<>();

}
