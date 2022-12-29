package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entitiesDtos.ConventionDto;

public class ConventionDTOTestData extends TestData{

    public static ConventionDto defaultDTO(){
        return ConventionDto
                .builder()

                .build();
    }
}
