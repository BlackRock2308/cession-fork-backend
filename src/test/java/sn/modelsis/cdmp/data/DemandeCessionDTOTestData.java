package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;

public class DemandeCessionDTOTestData extends TestData{

    public static DemandeCessionDto defaultDTO(){
        return DemandeCessionDto
                .builder()
                .idDemande(Default.id)
                .numeroDemande(Default.numeroDemande)
                .bonEngagement(Default.bonEngagementDto)
                .pme(Default.pmeDTO)
                .dateDemandeCession(Default.dateDemandeCession)
                .build();
    }

    public static DemandeCessionDto updatedDTO(){
        return DemandeCessionDto
                .builder()
                .idDemande(Update.id)
                .numeroDemande(Update.numeroDemande)
                .bonEngagement(Update.bonEngagementDto)
                .pme(Update.pmeDTO)
                .dateDemandeCession(Update.dateDemandeCession)
                .build();

    }

//    public static DemandeCession defaultEntity(){
//        return DemandeCession
//                .builder()
//                .
//                .numeroDemande(Update.numeroDemande)
//                .bonEngagement(Update.bonEngagementDto)
//                .pme(Update.pmeDTO)
//                .dateDemandeCession(Update.dateDemandeCession)
//                .build();
//
//    }
}
