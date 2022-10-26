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

    DemandeCessionDto rejeterCession(DemandeCessionDto demandecessionDto);

    DemandeCessionDto validerCession(DemandeCessionDto demandecession);

    DemandeCessionDto validerAnalyse (DemandeCessionDto demandecession);

    DemandeCessionDto rejeterAnalyse(DemandeCessionDto demandecessionDto);

    DemandeCessionDto demanderComplements (DemandeCessionDto demandecession);

    DemandeCessionDto validerRecevabilite(DemandeCessionDto demandecessionDto);

    DemandeCessionDto rejeterRecevabilite(DemandeCessionDto demandecessionDto);

    Optional<DemandeCession> getDemandeCession(Long id);




    /**
     *
     * @return
     */
    List<DemandeCession> findAll();
}
