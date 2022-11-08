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

    Page<DemandeCessionDto> findAll(Pageable pageable);

    Optional<DemandeCessionDto> findById (Long id);

    // Recevabilite Demande de Cession
    DemandeCession validerRecevabilite(Long idDemande );
    DemandeCession rejeterRecevabilite(Long idDemande );

    // Analyse de risques Demande de Cession
    DemandeCession analyseDemandeCessionRisque(Long idDemande );
    DemandeCession analyseDemandeCessionNonRisque (Long idDemande );
    DemandeCession analyseDemandeCessionComplement (Long idDemande);

    Optional<DemandeCessionDto> getDemandeCession(Long id);

    List<DemandeCession> findAllPMEDemandes(Long id);

    Page<DemandeCessionDto> findAllPMEDemandes(Pageable pageable,Long id);

    List<DemandeCession> findAllDemandeRejetee();

    List<DemandeCession> findAllDemandeAcceptee();

    List<DemandeCession> findAllDemandeComplementRequis();

//    Page<DemandeCessionDto> findAllDemandeComplementRequis(Pageable pageable);

    Page<DemandeCessionDto> findAllByStatut(Pageable pageable, String statut);


    }
