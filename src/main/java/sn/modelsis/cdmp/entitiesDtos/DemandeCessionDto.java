package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entities.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString
public class DemandeCessionDto {

    private Long idDemande;
    private PmeDto pme;
    private StatutDto statut;
    private Set<ObservationDto> observations = new HashSet<>();
    private Set<DocumentDto> documents = new HashSet<>();
    private Set<PaiementDto> paiements = new HashSet<>();
    private BonEngagementDto bonEngagement;

}
