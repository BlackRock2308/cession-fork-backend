package sn.modelsis.cdmp.security.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor @SuperBuilder
public class AuthentificationDto {

    private String email;
    private String password;
}
