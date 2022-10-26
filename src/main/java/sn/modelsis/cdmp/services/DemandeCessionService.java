package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
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

    DemandeCessionDto rejeterCession(DemandeCessionDto demandecession);

    DemandeCessionDto validerCession(DemandeCessionDto demandecession);

    DemandeCessionDto validerAnalyse (DemandeCessionDto demandecessionDto);

    DemandeCessionDto rejeterAnalyse (DemandeCessionDto demandecessionDto);

    DemandeCessionDto demanderComplements (DemandeCessionDto demandecessionDto);

    DemandeCessionDto validerRecevabilite (DemandeCessionDto demandecessionDto);

    DemandeCessionDto rejeterRecevabilite (DemandeCessionDto demandecessionDto);




    /**
     *
     * @return
     */
    List<DemandeCession> findAll();
}
