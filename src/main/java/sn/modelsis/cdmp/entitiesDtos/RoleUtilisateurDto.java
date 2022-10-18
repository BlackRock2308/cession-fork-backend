package sn.modelsis.cdmp.entitiesDtos;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.entities.Utilisateur;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@ToString
public class RoleUtilisateurDto {

    private Role roleid;
    private Utilisateur utilisateurid;
}

