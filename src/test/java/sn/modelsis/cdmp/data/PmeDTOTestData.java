package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;

public class PmeDTOTestData extends TestData {

    public static PmeDto defaultDTO() {
        return PmeDto
                .builder()
                .idPME(Default.id)
                .adressePME(Default.adressePME)
                .activitePrincipale(Default.activitePrincipale)
                .prenomRepresentant(Default.prenomRepresentant)
                .nomRepresentant(Default.nomRepresentant)
                .rccm(Default.rccm)
                .telephonePME(Default.telephonePME)
                .raisonSocial(Default.raisonSocial)
                .ninea(Default.ninea)
                .email(Default.email)
                .atd(Default.atd)
                .hasninea(Default.hasninea)
                .isactive(Default.isactive)
                .nantissement(Default.nantissement)
                .cniRepresentant(Default.cniRepresentant)
                .dateImmatriculation(Default.dateImmatriculation)
                .build();
    }


    public static PmeDto updatedDTO() {
        return PmeDto
                .builder()
                .idPME(Update.id)
                .adressePME(Update.adressePME)
                .activitePrincipale(Update.activitePrincipale)
                .prenomRepresentant(Update.prenomRepresentant)
                .nomRepresentant(Update.nomRepresentant)
                .rccm(Update.rccm)
                .telephonePME(Update.telephonePME)
                .raisonSocial(Update.raisonSocial)
                .ninea(Update.ninea)
                .email(Update.email)
                .atd(Update.atd)
                .hasninea(Update.hasninea)
                .isactive(Update.isactive)
                .nantissement(Update.nantissement)
                .cniRepresentant(Update.cniRepresentant)
                .dateImmatriculation(Default.dateImmatriculation)
                .build();
    }

    public static Pme defaultEntity() {
        return Pme
                .builder()
                .idPME(Default.id)
                .adressePME(Default.adressePME)
                .activitePrincipale(Default.activitePrincipale)
                .prenomRepresentant(Default.prenomRepresentant)
                .nomRepresentant(Default.nomRepresentant)
                .rccm(Default.rccm)
                .telephonePME(Default.telephonePME)
                .raisonSocial(Default.raisonSocial)
                .ninea(Default.ninea)
                .email(Default.email)
                .atd(Default.atd)
                .hasninea(Default.hasninea)
                .isactive(Default.isactive)
                .nantissement(Default.nantissement)
                .cniRepresentant(Default.cniRepresentant)
                .dateImmatriculation(Default.dateImmatriculation)
                .build();
    }

}
