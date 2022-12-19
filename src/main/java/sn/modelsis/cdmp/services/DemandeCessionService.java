package sn.modelsis.cdmp.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.CreanceDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.NewDemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiqueDemandeCession;


public interface DemandeCessionService {
    DemandeCession saveCession(DemandeCession demandeCession);

    DemandeCession save(DemandeCession demandeCession);

    Page<DemandeCessionDto> findAll(Pageable pageable);

    Page<NewDemandeCessionDto> findAllWithoutDemande(Pageable pageable);

    Optional<DemandeCessionDto> findById (Long id);

    Optional<DemandeCession> findByIdDemande (Long id);
    
    // Recevabilite Demande de Cession
    DemandeCession validerRecevabilite(Long idDemande );

    DemandeCession rejeterRecevabilite(Long idDemande );

    // Analyse de risques Demande de Cession
    DemandeCession analyseDemandeCessionRisque(Long idDemande );

    DemandeCession analyseDemandeCessionNonRisque (Long idDemande );

    DemandeCession analyseDemandeCessionComplement (Long idDemande);

    Optional<DemandeCessionDto> getDemandeCession(Long id);

    Page<DemandeCessionDto> findAllByStatutAndPME(Pageable pageable, String statut, Long idPME);

    List<DemandeCession> findAllPMEDemandes(Long id);

    Page<DemandeCessionDto> findAllPMEDemandes(Pageable pageable,Long id);

    List<DemandeCession> findAllDemandeRejetee();

    List<DemandeCession> findAllDemandeAcceptee();

    List<DemandeCession> findAllDemandeComplementRequis();

    Page<DemandeCessionDto> findAllByStatut(Pageable pageable, String[] statuts);

   List<StatistiqueDemandeCession>  getStatistiqueDemandeCession(int anne);

    void signerConventionDG(Long idDemande);

    void signerConventionPME(Long idDemande);

    DemandeCession updateStatut(Long idDemande, String statut);



    List<DemandeCessionDto> findDemandeCessionByMultipleParams(String referenceBE,
                                                        String numeroDemande,
                                                        String nomMarche,
                                                        String statutLibelle,
                                                               LocalDateTime startDate,
                                                               LocalDateTime endDate);

    List<DemandeCessionDto> findDemandeCessionByStatutLibelle(String statutLibelle);



    List<DemandeCessionDto> findDemandeCessionByLocalDateTime(LocalDateTime startDate,LocalDateTime endDate);

/*Filter Creance using multpile parameters*/
List<CreanceDto> findCreanceByMultipleParams(String nomMarche,
                                             String raisonSocial,
                                             double montantCreance,
                                             String statutLibelle,
                                             double decote,
                                             LocalDateTime startDateD,
                                             LocalDateTime endDateD,
                                             LocalDateTime startDateM,
                                             LocalDateTime endDateM
);

    List<CreanceDto> findCreanceByRaisonSocial(String raisonSocial);

    List<CreanceDto> findCreanceByNomMarche(String nomMarche);

    List<CreanceDto> findCreanceByMontantCreance(double montantCreance);

    Page<DemandeCessionDto> findAllCreance(Pageable pageable);

    //find all filtering with the right statut libele
    Page<DemandeCessionDto> findAllCreanceWithTheRightStatut(Pageable pageable);





}
