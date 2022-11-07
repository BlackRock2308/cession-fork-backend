package sn.modelsis.cdmp.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.ItemNotFoundException;
import sn.modelsis.cdmp.mappers.CreanceMapper;
import sn.modelsis.cdmp.mappers.DemandeCessionMapper;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.ExceptionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class DemandeCessionServiceImpl implements DemandeCessionService {

    private final DemandeCessionRepository demandecessionRepository;
    private final BonEngagementRepository bonEngagementRepository;
    private final PmeRepository pmeRepository;
    private final StatutRepository statutRepository;
    private final ObservationRepository observationRepository;
    private final DemandeCessionMapper cessionMapper;

    private final CreanceMapper creanceMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCession saveCession(DemandeCession demandeCession) {

        DemandeCession newDemandeCession;

        try{
            log.info("DemandeCessionService:saveCession request started");
            demandeCession.setDateDemandeCession(new Date());

            Statut statut = statutRepository.findByLibelle("SOUMISE");
            demandeCession.setStatut(statut);
            log.debug("DemandeCessionService:saveCession request Parameters {}",demandeCession);
            newDemandeCession = demandecessionRepository.save(demandeCession);
            log.debug("DemandeCessionService:saveCession received response from database {}",newDemandeCession);
        }catch(Exception e) {
            log.error("Exception occured while persisting a new Demande Cession in the database. Exception message : {}", e.getMessage());
            throw new CustomException("Exception occured while creating a new Demande Cession");
        }
        return newDemandeCession;
    }

    @Override
    public Page<DemandeCessionDto> findAll(Pageable pageable){
        log.info("DemandeCessionService:findAll request started");
        return demandecessionRepository
                .findAll(pageable)
                .map(cessionMapper::asDTO);
    }

    @Override
    public Optional<DemandeCessionDto> findById(Long id) {
        log.info("DemandeCessionService:findById request started");
        log.debug("DemandeCessionService:findById request params {}", id);
        final Optional <DemandeCessionDto> optional = Optional.of(demandecessionRepository
                .findById(id)
                .map(cessionMapper::asDTO)
                .orElseThrow());
        ExceptionUtils.absentOrThrow(optional, ItemNotFoundException.DEMANDE_CESSION_BY_ID, id.toString());
        log.debug("DemandeCessionService:findById received from database {}", optional.get());
        return optional;
    }

    @Override
    public Optional <DemandeCessionDto> getDemandeCession(Long id) {
        log.info("DemandeCessionService:getDemandeCession request started");
        log.debug("DemandeCessionService:getDemandeCession request params {}", id);        return demandecessionRepository
                .findById(id)
                .map(cessionMapper::asDTO);
    }

    @Override
    public List<DemandeCession> findAllPMEDemandes(Long id) {
        return demandecessionRepository.findAllByPmeIdPME(id);
    }

    /** Recebavilite des Demande de Cession REJETTEE ou RECEVABLE **/

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCession rejectionDemandeCession(Long idDemande ){
        DemandeCession demandeCessionDto;
        try{
            log.info("DemandeCessionService:rejectionDemandeCession request started", idDemande);
            Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
            log.debug("DemandeCessionService:rejectionDemandeCession request params {}", idDemande);
            Statut updatedStatut = statutRepository.findByLibelle("REJETEE");
            optional.get().setStatut(updatedStatut);
            demandeCessionDto = demandecessionRepository.save(optional.get());
        }catch (Exception ex){
            log.error("Exception occured while calling rejectionDemandeCession method. Error message : {}", ex.getMessage());
            throw new CustomException("Exceptiom occur while rejecting the Demande");
        }
        log.debug("DemandeCessionService:rejectionDemandeCession received response from Database {}", demandeCessionDto);
        return demandeCessionDto;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCession acceptDemandeCession(Long idDemande ){
        DemandeCession demandeCessionDto;
        try {
            log.info("DemandeCessionService:acceptDemandeCession request params {}", idDemande);
            Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
            log.debug("DemandeCessionService:acceptDemandeCession request params {}", idDemande);
            Statut updatedStatut = statutRepository.findByLibelle("RECEVABLE");
            optional.get().setStatut(updatedStatut);

            demandeCessionDto = demandecessionRepository.save(optional.get());
            log.debug("DemandeCessionService:acceptDemandeCession received from Database {}", demandeCessionDto.getIdDemande());
        }catch (Exception ex) {
            log.error("Exception occured while calling acceptDemandeCession method. Error message : {}", ex.getMessage());
            throw new CustomException("Exceptiom occur while accepting the Demande");
        }
        return demandeCessionDto;

    }

    /** Analyse de risque des Demande de Cession RISQUEE, NON_RISQUEE ou COMPLEMENT **/

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCession analyseDemandeCessionRisque(Long idDemande ){
        log.info("DemandeCessionService:validateDemandeCession request started...");
        Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
        log.debug("DemandeCessionService:validateDemandeCession request params {}", idDemande);
        Statut updatedStatut = statutRepository.findByLibelle("RISQUEE");
        optional.get().setStatut(updatedStatut);

        DemandeCession demandeCessionDto = demandecessionRepository.save(optional.get());
        log.info("DemandeCessionService:validateDemandeCession received from Database {}", demandeCessionDto.getIdDemande());
        return demandeCessionDto;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCession analyseDemandeCessionNonRisque(Long idDemande ){
        log.info("DemandeCessionService:analyseDemandeCessionNonRisque request params {}", idDemande);
        Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
        Statut updatedStatut = statutRepository.findByLibelle("NON_RISQUEE");
        optional.get().setStatut(updatedStatut);

        DemandeCession demandeCessionDto = demandecessionRepository.save(optional.get());
        log.info("DemandeCessionService:analyseDemandeCessionNonRisque received from Database {}", demandeCessionDto.getIdDemande());
        return demandeCessionDto;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCession analyseDemandeCessionComplement(Long idDemande ){
        log.info("DemandeCessionService:analyseDemandeCessionComplement request started");
        Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
        log.debug("DemandeCessionService:analyseDemandeCessionComplement request params {}", idDemande);
        Statut updatedStatut = statutRepository.findByLibelle("COMPLEMENT_REQUIS");
        optional.get().setStatut(updatedStatut);

        DemandeCession demandeCessionDto = demandecessionRepository.save(optional.get());
        log.info("DemandeCessionService:analyseDemandeCessionComplement received from Database {}", demandeCessionDto.getIdDemande());
        return demandeCessionDto;

    }


    /**
     This function is used inside validerRecevabilite and rejeterRecevabilite
     to avoid boilerplate code inside those two functions
     */
    public DemandeCession getDemandeCessionDto(DemandeCessionDto demandecessionDto) {
        Observation observation = DtoConverter.convertToEntity((ObservationDto) demandecessionDto.getObservations());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        Pme pme = demandecession.getPme();
        BonEngagement be = demandecession.getBonEngagement();
        observationRepository.save(observation);
        pmeRepository.save(pme);
        bonEngagementRepository.save(be);
        return demandecession;
    }

    //Validation de la recevabilité de la demande de cession
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCessionDto validerRecevabilite(DemandeCessionDto demandecessionDto) {

        DemandeCession demandeCession = getDemandeCessionDto(demandecessionDto);
        //demandeCession.setStatut(statutRepository.findByLibelle(Statuts.RECEVABLE));
        demandeCession.setStatut(statutRepository.findByLibelle("RECEVABLE"));

        DemandeCession result=demandecessionRepository.save(demandeCession);
        return DtoConverter.convertToDto(result) ;
    }

    //Rejet de la recevabilité de la demande de cession
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCessionDto rejeterRecevabilite(DemandeCessionDto demandecessionDto) {
        DemandeCession demandeCession = getDemandeCessionDto(demandecessionDto);
        //demandeCession.setStatut(statutRepository.findByLibelle(Statuts.REJETEE));
        demandeCession.setStatut(statutRepository.findByLibelle("REJETEE"));

        DemandeCession result=demandecessionRepository.save(demandeCession);
        return DtoConverter.convertToDto(result) ;

    }
}
