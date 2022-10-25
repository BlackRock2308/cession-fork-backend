package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;

import java.util.List;
import java.util.Optional;

public interface DemandeCessionService {

    /**
     *
     * @param demandecession
     * @return
     */

    DemandeCession saveCession(DemandeCession demandecession);

    Optional<DemandeCession> getDemandeCession(Long id);

    DemandeCessionDto rejetCession(DemandeCessionDto demandecession);

    DemandeCessionDto validerCession(DemandeCessionDto demandecession);

    DemandeCessionDto validerAnalyse (DemandeCessionDto demandecession);

    DemandeCessionDto rejetAnalyse (DemandeCessionDto demandecession);

    DemandeCessionDto demanderComplements (DemandeCessionDto demandecession);




    /**
     *
     * @return
     */
    List<DemandeCession> findAll();
}
