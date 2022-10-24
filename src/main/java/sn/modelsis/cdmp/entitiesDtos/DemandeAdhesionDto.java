package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entities.DemandeDocuments;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Statut;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString
public class DemandeAdhesionDto {

    private PmeDto pme;
    private StatutDto statut;
    private Set<ObservationDto> observations = new HashSet<>();
    private Set<DocumentDto> documents = new HashSet<>();

}
