package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;

public class BonEngagementDTOTestData extends TestData{


    public static BonEngagementDto defaultDTO() {
        return BonEngagementDto
                .builder()
                .idBonEngagement(Default.id)
                .montantCreance(Default.montantCreance)
                .nomMarche(Default.nomMarche)
                .reference(Default.reference)
                .naturePrestation(Default.naturePrestation)
                .dateBonEngagement(Default.datebonengagement)
                .build();
    }

    public static BonEngagementDto updatedDTO(){
        return BonEngagementDto
                .builder()
                .idBonEngagement(Update.id)
                .montantCreance(Update.montantCreance)
                .nomMarche(Update.nomMarche)
                .reference(Update.reference)
                .naturePrestation(Update.naturePrestation)
                .dateBonEngagement(Update.datebonengagement)
                .build();

    }

    public static BonEngagement defaultEntity(){
        return BonEngagement
                .builder()
                .idBonEngagement(Update.id)
                .montantCreance(Update.montantCreance)
                .nomMarche(Update.nomMarche)
                .reference(Update.reference)
                .naturePrestation(Update.naturePrestation)
                .datebonengagement(Update.datebonengagement)
                .build();

    }
}
