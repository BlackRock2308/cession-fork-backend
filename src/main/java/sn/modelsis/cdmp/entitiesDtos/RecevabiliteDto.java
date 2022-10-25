package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString
public class RecevabiliteDto {
    private String nomMarche;
    private String raisonSociale;
    private String referenceBE;
    private Date dateDemandeCreance;
    private String ninea;
    private String rccm;
    private Set<DocumentDto> documents = new HashSet<>();
    private ObservationDto observationDto;

}
