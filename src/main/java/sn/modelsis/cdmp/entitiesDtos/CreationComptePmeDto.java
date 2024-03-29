package sn.modelsis.cdmp.entitiesDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entitiesDtos.email.EmailMessageWithTemplate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreationComptePmeDto {

    private String email;
    private EmailMessageWithTemplate emailMessageWithTemplate ;
}
