package sn.modelsis.cdmp.entitiesDtos;

import lombok.*;
import sn.modelsis.cdmp.entities.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString @Getter @Setter
public class DemandeCessionDto {

    private Long idDemande;
    private Long idPme;
    private StatutDto statut;
    private Set<ObservationDto> observations = new HashSet<>();
    private Set<DocumentDto> documents = new HashSet<>();
    private Set<PaiementDto> paiements = new HashSet<>();
    private BonEngagementDto bonEngagement;
    private PmeDto pme;
}
