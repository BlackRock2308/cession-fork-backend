package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.Role;

public class RoleDTOTestData extends TestData{

    public static Role defaultEntity(){
        return Role
                .builder()
                .id(Default.id)
                .description(Default.description)
                .libelle(Default.libelle)
                .build();
    }


    public static Role updatedEntity(){
        return Role
                .builder()
                .id(Update.id)
                .description(Update.description)
                .libelle(Update.libelle)
                .build();
    }
}
