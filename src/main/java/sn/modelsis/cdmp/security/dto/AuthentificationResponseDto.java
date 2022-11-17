package sn.modelsis.cdmp.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.modelsis.cdmp.entities.Utilisateur;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthentificationResponseDto {

    private Utilisateur utilisateur;
    private String token;
}
