package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;

public class ParametrageDecoteDTOTest extends TestData{

    public static ParametrageDecoteDTO defaultDTO(){
        return ParametrageDecoteDTO
                .builder()
                .idDecote(Default.idDecote)
                .borneInf(Default.borneInf)
                .borneSup(Default.borneSup)
                .decoteValue(Default.decoteValue)
                .build();
    }

    public static ParametrageDecoteDTO updateDTO(){
        return ParametrageDecoteDTO
                .builder()
                .idDecote(Update.idDecote)
                .borneInf(Update.borneInf)
                .borneSup(Update.borneSup)
                .decoteValue(Update.decoteValue)
                .build();
    }

    public static ParametrageDecote defaultEntity(){
        return ParametrageDecote
                .builder()
                .IdDecote(Default.idDecote)
                .borneInf(Default.borneInf)
                .borneSup(Default.borneSup)
                .decoteValue(Default.decoteValue)
                .build();
    }
}
