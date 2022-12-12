package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;

public class DemandeAdhesionDTOTestData extends TestData{

    public static DemandeAdhesionDto defaultDTO(){
        return DemandeAdhesionDto
                .builder()
                .idDemande(Default.id)
                .dateDemandeAdhesion(Default.dateDemandeAdhesion)
                .idPME(Default.id)
                .pme(Default.pmeDTO)
                .build();
    }


    public static DemandeAdhesionDto updatedDTO(){
        return DemandeAdhesionDto
                .builder()
                .idDemande(Update.id)
                .dateDemandeAdhesion(Update.dateDemandeAdhesion)
                .idPME(Update.id)
                .pme(Update.pmeDTO)
                .build();
    }


}
