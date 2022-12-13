package sn.modelsis.cdmp.entitiesDtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @SuperBuilder
public class DemandeCessionDto implements Serializable {

    private Long idDemande;
    private LocalDateTime dateDemandeCession;
    private StatutDto statut;
    private String numeroDemande;
    private Set<ObservationDto> observations = new HashSet<>();
    private Set<DocumentDto> documents = new HashSet<>();
    private Set<ConventionDto> conventions = new HashSet<>();
    private PaiementDto paiement ;
    @NotBlank
    private BonEngagementDto bonEngagement;
    @NotBlank
    private PmeDto pme;

}
