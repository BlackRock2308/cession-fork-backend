package sn.modelsis.cdmp.dbPersist;

import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.services.BonEngagementService;

public class PersistBonEngagement {


    private final BonEngagementService bonEngagementService;

    public  PersistBonEngagement(BonEngagementService bonEngagementService){
        this.bonEngagementService = bonEngagementService;

        BonEngagement bE1 = new BonEngagement();
        bE1.setNatureDepense("Frais de mission à l'intérieur du \n" +
                "pays\n");
        bE1.setExercice("2020");
        bE1.setTypeDepense("ENGAGEMENT");
        bE1.setMontantCreance(500000);
        bE1.setModeReglement("DMRI");
        bE1.setObjetDepense("REGULARISATIONS");
       this.bonEngagementService.save(bE1);

        BonEngagement bE2 = new BonEngagement();
        bE1.setNatureDepense("Frais de mission à l'intérieur du \n" +
                "pays\n");
        bE1.setExercice("2021");
        bE1.setTypeDepense("ENGAGEMENT");
        bE1.setMontantCreance(4500000);
        bE1.setModeReglement("DMRI");
        bE1.setObjetDepense("REGULARISATIONS");
        this.bonEngagementService.save(bE2);

    }


}
