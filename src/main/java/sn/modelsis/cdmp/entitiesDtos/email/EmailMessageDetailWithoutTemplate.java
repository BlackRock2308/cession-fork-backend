package sn.modelsis.cdmp.entitiesDtos.email;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailMessageDetailWithoutTemplate {

    private String codeApp;

    private String typeMessage;

    private String frontLink;

    private String destinataire;

    private String expediteur;

    private String objetDeLEmail;

    private String content;

}
