package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;

import java.util.List;

public interface DemandeAdhesionService {

    /**
     *
     * @param demandeadhesion
     * @return
     */

    DemandeAdhesion saveAdhesion(DemandeAdhesion demandeadhesion);

    DemandeAdhesionDto rejetAdhesion(DemandeAdhesionDto demandeadhesion);

    DemandeAdhesionDto validerAdhesion(DemandeAdhesionDto demandeahdesion);



    /**
     *
     * @return
     */
    List<DemandeAdhesion> findAll();
}
