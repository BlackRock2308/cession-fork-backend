package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;

public class ObservationDTOTestData extends TestData{
    public static ObservationDto defaultDto(){

        return ObservationDto
                .builder()
                .id(Default.id)
                .libelle(Default.libelle)
                .demandeid(Default.id)
                .statut(Default.statutDto)
                .build();
    }

    public static Observation defaultEntity(){
        return Observation
                .builder()
                .id(Default.id)
                .demande(Default.demande)
                .libelle(Default.libelle)
                .build();
    }
}
