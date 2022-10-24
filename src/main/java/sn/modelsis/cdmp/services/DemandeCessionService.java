package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;

import java.util.List;

public interface DemandeCessionService {

    /**
     *
     * @param demandecession
     * @return
     */

    DemandeCession saveCession(DemandeCession demandecession);

    DemandeCessionDto rejetAdhesion(DemandeCessionDto demandecession);

    DemandeCessionDto validerAdhesion(DemandeCessionDto demandecession);



    /**
     *
     * @return
     */
    List<DemandeCession> findAll();
}
