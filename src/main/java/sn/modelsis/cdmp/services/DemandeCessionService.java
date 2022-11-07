package sn.modelsis.cdmp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;

import java.util.List;
import java.util.Optional;

public interface DemandeCessionService {
    DemandeCession saveCession(DemandeCession demandeCession);

//    DemandeCession addCession(DemandeCessionDto demandeCessionDto);

    //    List<DemandeCession> findAll();

    Page<DemandeCessionDto> findAll(Pageable pageable);

    Optional<DemandeCessionDto> findById (Long id);

    public DemandeCessionDto rejeterRecevabilite(DemandeCessionDto demandecessionDto);


    // Analyse Demande de Cession
    DemandeCession rejectionDemandeCession(Long idDemande );
    DemandeCession acceptDemandeCession(Long idDemande );

    // Analyse Demande de Cession
    DemandeCession analyseDemandeCessionRisque(Long idDemande );
    DemandeCession analyseDemandeCessionNonRisque (Long idDemande );
    DemandeCession analyseDemandeCessionComplement (Long idDemande);

    public DemandeCessionDto validerRecevabilite(DemandeCessionDto demandecessionDto);

    Optional<DemandeCessionDto> getDemandeCession(Long id);

//    DemandeCessionDto validerCession(DemandeCessionDto demandecession);




//    DemandeCessionDto rejeterAnalyse(DemandeCessionDto demandecessionDto);





    List<DemandeCession> findAllPMEDemandes(Long id);

    Page<DemandeCessionDto> findAllByStatut(Pageable pageable, String statut);


    /**
     *
     * @return
     */

    }
