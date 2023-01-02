package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entitiesDtos.StatutDto;

public class StatutDTOTestData extends TestData{

    public static StatutDto defaultDTO(){
        return StatutDto
                .builder()
                .idStatut(Default.id)
                .libelle(Default.libelle)
                .code(Default.code)
                .build();
    }

    public static StatutDto updatedDTO(){
        return StatutDto
                .builder()
                .idStatut(Update.id)
                .libelle(Update.libelle)
                .code(Update.code)
                .build();
    }


    public static Statut defaultEntity(){
        return Statut
                .builder()
                .idStatut(Update.id)
                .libelle(Update.libelle)
                .code(Update.code)
                .build();
    }
}
