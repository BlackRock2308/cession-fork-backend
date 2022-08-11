package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Statut;

@Data
@NoArgsConstructor
@ToString
public class DemandeDto {
    private Long idDemande;
    private Pme pme;
    private Statut statut;
    private BonEngagement bonEngagement;
}
