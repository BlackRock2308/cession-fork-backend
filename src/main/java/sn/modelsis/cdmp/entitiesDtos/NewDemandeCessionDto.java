package sn.modelsis.cdmp.entitiesDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NewDemandeCessionDto implements Serializable {

    private Long idDemande;
    private Date dateDemandeCession;
    private StatutDto statut;
    private String numeroDemande;
    private Set<NewObservationDto> observations = new HashSet<>();
    private Set<DocumentDto> documents = new HashSet<>();
    private Set<ConventionDto> conventions = new HashSet<>();
    private PaiementDto paiement ;
    @NotBlank
    private BonEngagementDto bonEngagement;
    @NotBlank
    private PmeDto pme;
}
