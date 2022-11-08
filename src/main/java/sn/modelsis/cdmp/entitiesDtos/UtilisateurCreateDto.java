package sn.modelsis.cdmp.entitiesDtos;

import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UtilisateurCreateDto {

    private Long idUtilisateur;

    @Column(name="adresse")
    private String adresse;

    @Column(name="codePin")
    private String codePin;

    @Column(name="password")
    private String password;

    @Column(name="prenom")
    private String prenom;

    @Column(name="urlImagesignature")
    private String urlImageSignature;

    @Column(name="telephone")
    private int telephone;

    @Column(name="email")
    private String email;

    @Column(name="urlimageprofil")
    private String urlImageProfil;

    private List<String> roles ;

    // private Set<RoleUtilisateur> rolesUtilisateurs ;

}
