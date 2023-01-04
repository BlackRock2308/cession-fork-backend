package sn.modelsis.cdmp.entitiesDtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import sn.modelsis.cdmp.entities.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class UtilisateurDto {

    private Long idUtilisateur;

    private String adresse;

    private String codePin;

    private String password;

    private String prenom;
    
    private String nom;

    private String urlImageSignature;

    private String telephone;

    private String email;

    private String urlImageProfil;

    private Set<Role> roles ;

    private boolean updatePassword;

    private boolean updateCodePin;

    private boolean active;

    private MinistereDepensierDto minister;
    // private Set<RoleUtilisateur> rolesUtilisateurs ;


}
