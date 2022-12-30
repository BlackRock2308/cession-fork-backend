package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;

public class DemandeDTOTestData extends TestData{

    public static DemandeDto defaultDTO(){
        return DemandeDto
                .builder()
                .idDemande(Default.id)
                .numeroDemande(Default.numeroDemande)
                .bonEngagement(Default.bonEngagementDto)
                .pme(Default.pmeDTO)
                .build();
    }

    public static DemandeDto updatedDTO(){
        return DemandeDto
                .builder()
                .idDemande(Update.id)
                .numeroDemande(Update.numeroDemande)
                .bonEngagement(Update.bonEngagementDto)
                .pme(Update.pmeDTO)
                .build();
    }

}
