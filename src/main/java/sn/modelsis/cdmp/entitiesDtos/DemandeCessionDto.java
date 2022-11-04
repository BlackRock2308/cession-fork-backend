package sn.modelsis.cdmp.entitiesDtos;

import lombok.*;
import sn.modelsis.cdmp.entities.*;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString @Getter @Setter
public class DemandeCessionDto {

    private Long idDemande;
//    private Long idPME;
    private Date dateDemandeCession;
    private StatutDto statut;
    private Set<ObservationDto> observations = new HashSet<>();
    private Set<DocumentDto> documents = new HashSet<>();
    private List<Convention> conventions = new ArrayList<>();
    private Set<PaiementDto> paiements = new HashSet<>();
    @NotBlank
    private BonEngagementDto bonEngagement;
    @NotBlank
    private PmeDto pme;
}
