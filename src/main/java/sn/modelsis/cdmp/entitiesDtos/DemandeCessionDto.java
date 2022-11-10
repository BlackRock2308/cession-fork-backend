package sn.modelsis.cdmp.entitiesDtos;

import lombok.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class DemandeCessionDto implements Serializable {

    private Long idDemande;
    private Date dateDemandeCession;
    private StatutDto statut;
    private Set<ObservationDto> observations = new HashSet<>();
    private Set<DocumentDto> documents = new HashSet<>();
    private Set<ConventionDto> conventions = new HashSet<>();
    private PaiementDto paiement ;
    @NotBlank
    private BonEngagementDto bonEngagement;
    @NotBlank
    private PmeDto pme;

    private String numeroDemande;
}
