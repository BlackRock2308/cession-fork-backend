package sn.modelsis.cdmp.entitiesDtos.email;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
@Setter
@Builder
public class EmailMessageWithTemplate {

    private String expediteur;

    private String destinataire;

    private String templateName;

    private String objet;

    private Map<String, Object> templateVariable = new HashMap<>();

}
