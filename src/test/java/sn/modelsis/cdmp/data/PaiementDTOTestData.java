package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entitiesDtos.PaiementDto;

public class PaiementDTOTestData extends TestData{

    public static PaiementDto defaultDTO(){
        return PaiementDto
                .builder()
                .idPaiement(Default.id)
                .montantCreance(Default.montantCreance)
                .nomMarche(Default.nomMarche)
                .raisonSocial(Default.raisonSocial)
                .build();
    }

}
