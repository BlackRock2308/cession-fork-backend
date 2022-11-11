package sn.modelsis.cdmp.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiqueDemandeCession;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.ItemNotFoundException;
import sn.modelsis.cdmp.exceptions.*;
import sn.modelsis.cdmp.mappers.DemandeCessionMapper;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.util.ExceptionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class DemandeCessionServiceImpl implements DemandeCessionService {

    private final DemandeCessionRepository demandecessionRepository;
    private final StatutRepository statutRepository;
    private final DemandeCessionMapper cessionMapper;
     private final DemandeService demandeService;



    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCession saveCession(DemandeCession demandeCession) {
        DemandeCession newDemandeCession;

        try{
            log.info("DemandeCessionService:saveCession request started");
            demandeCession.setDateDemandeCession(new Date());

            Date dateBonEngagement = new Date();

            demandeCession.getBonEngagement().setDatebonengagement(dateBonEngagement);

            Statut statut = statutRepository.findByLibelle("SOUMISE");
            demandeCession.setStatut(statut);
            log.debug("DemandeCessionService:saveCession request Parameters {}",demandeCession);

             if(demandeCession.getIdDemande()==null){
            demandeCession.setNumeroDemande(demandeService.getNumDemande());
        }
            newDemandeCession = demandecessionRepository.save(demandeCession);
            log.debug("DemandeCessionService:saveCession received response from database {}",newDemandeCession);
        }catch(Exception e) {
            log.error("Exception occured while persisting a new Demande Cession in the database. Exception message : {}", e.getMessage());
            throw new CustomException("Exception occured while creating a new Demande Cession");
        }
        return newDemandeCession;
    }

    @Override
    public DemandeCession save(DemandeCession demandeCession) {
        return demandecessionRepository.save(demandeCession);
    }

    @Override
    public Page<DemandeCessionDto> findAll(Pageable pageable){
        log.info("DemandeCessionService:findAll : fetching .....");
        var x =demandecessionRepository.findAll();
        return demandecessionRepository
                .findAll(pageable)
                .map(cessionMapper::asDTO);
    }

    @Override
    public Optional<DemandeCessionDto> findById(Long id) {
        log.info("DemandeCessionService:findById :fetching .....");
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
    public Optional<DemandeCession> findByIdDemande(Long id) {
        return demandecessionRepository.findById(id);
    }

    @Override
    public Optional <DemandeCessionDto> getDemandeCession(Long id) {
        log.info("DemandeCessionService:getDemandeCession request started");
        log.debug("DemandeCessionService:getDemandeCession request params {}", id);
        return demandecessionRepository
                .findById(id)
                .map(cessionMapper::asDTO);
    }


    /** ********** [RECEVABILITE} Demande de Cession REJETTEE ou RECEVABLE  ************* **/

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeCession rejeterRecevabilite(Long idDemande ){
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
    public DemandeCession validerRecevabilite(Long idDemande ){
        DemandeCession demandeCessionDto;
        try {
            log.info("DemandeCessionService:acceptDemandeCession request params {}", idDemande);
            Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
            log.debug("DemandeCessionService:acceptDemandeCession request params {}", idDemande);
            Statut updatedStatut = statutRepository.findByLibelle("RECEVABLE");

            optional.ifPresent(demandeCession -> optional.get().setStatut(updatedStatut));

            demandeCessionDto = demandecessionRepository.save(optional.get());
            log.debug("DemandeCessionService:acceptDemandeCession received from Database {}", demandeCessionDto.getIdDemande());
        }catch (Exception ex) {
            log.error("Exception occured while calling acceptDemandeCession method. Error message : {}", ex.getMessage());
            throw new CustomException("Exceptiom occur while accepting the Demande");
        }
        return demandeCessionDto;

    }

    /** ****** [ANALYSE DE RISQUE] des Demande de Cession RISQUEE, NON_RISQUEE ou COMPLEMENT  ****** **/

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

    /** ************** FIlter les demandes en fonction de leur statut*************************** **/
    @Override
    public List<DemandeCession> findAllDemandeRejetee(){
        log.info("DemandeCessionService:findAllDemandeRejetee : fetching .....");
        return demandecessionRepository
                .findAll().stream()
                .filter(demande -> demande.getStatut().getLibelle().equals("REJETEE"))
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandeCession> findAllDemandeAcceptee(){
        log.info("DemandeCessionService:findAllDemandeAcceptee : fetching .....");
        return demandecessionRepository
                .findAll().stream()
                .filter(demande -> demande.getStatut().getLibelle().equals("RECEVABLE"))
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandeCession> findAllDemandeComplementRequis() {
        log.info("DemandeCessionService:findAllBfindAllDemandeComplementRequis : fetching .....");
        return demandecessionRepository
                .findAll().stream()
                .filter(demande -> demande.getStatut().getLibelle().equals("COMPLEMENT_REQUIS"))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DemandeCessionDto> findAllByStatut(Pageable pageable, String statut) {
        log.info("DemandeCessionService:findAllByStatut .....");
        return demandecessionRepository
                .findAllByStatut_Libelle(pageable,statut)
                .map(cessionMapper::asDTO);
    }

    @Override
    public List<StatistiqueDemandeCession> getStatistiqueDemandeCession(int anne) {
        List<StatistiqueDemandeCession> statistiqueDemandeCessions = new ArrayList<>();
        LocalDate toDay =  LocalDate.of(anne, 1, 1);
        for(int i=0 ; i<12;i++){
        StatistiqueDemandeCession statistiqueDemandeCession = new StatistiqueDemandeCession();
        statistiqueDemandeCession.setMois(toDay);
        statistiqueDemandeCession.setNombreDemandeAccepte(demandecessionRepository.getDemandeByStautAntMonth("ACCEPTE", toDay));
        statistiqueDemandeCession.setNombreDemandeRejete(demandecessionRepository.getDemandeByStautAntMonth("REJETE", toDay));
        toDay = toDay.plusMonths(1);
        statistiqueDemandeCessions.add(statistiqueDemandeCession);
        }
        return statistiqueDemandeCessions;}
    public void signerConventionDG(Long idDemande) {
        log.info("DemandeCessionService:signerConventionDG request started");
        Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
        log.debug("DemandeCessionService:signerConventionDG request params {}", idDemande);
        Statut updatedStatut = statutRepository.findByLibelle("CONVENTION_SIGNEE_PAR_DG");
        optional.get().setStatut(updatedStatut);

        DemandeCession demandeCessionDto = demandecessionRepository.save(optional.get());
        log.info("DemandeCessionService:signerConventionDG received from Database {}", demandeCessionDto.getIdDemande());
    }

    @Override
    public void signerConventionPME(Long idDemande) {
        log.info("DemandeCessionService:signerConventionPME request started");
        Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
        log.debug("DemandeCessionService:signerConventionPME request params {}", idDemande);
        Statut updatedStatut = statutRepository.findByLibelle("CONVENTION_SIGNEE_PAR_PME");
        optional.get().setStatut(updatedStatut);

        DemandeCession demandeCessionDto = demandecessionRepository.save(optional.get());
        log.info("DemandeCessionService:signerConventionPME received from Database {}", demandeCessionDto.getIdDemande());

    }

    @Override
    public DemandeCession updateStatut(Long idDemande, String statut) {
        demandecessionRepository.findById(idDemande).ifPresentOrElse(
                (value)
                        -> {
                    value.setStatut(statutRepository.findByLibelle(statut));
                    demandecessionRepository.save(value);
                },
                () -> {
                    throw new NotFoundException("La demande n'existe pas");
                }
        );
        return demandecessionRepository.findById(idDemande).orElseThrow();
    }

    @Override
    public Page<DemandeCessionDto> findAllByStatutAndPME(Pageable pageable, String statut, Long idPME) {
        log.info("DemandeCessionService:findAllByStatutAndPME .....");
        return demandecessionRepository
                .findAllByPmeIdPMEAndStatut_Libelle(pageable,idPME,statut)
                .map(cessionMapper::asDTO);    }


    @Override
    public List<DemandeCession> findAllPMEDemandes(Long id) {
        log.info("DemandeCessionService:findAllPMEDemandes request params idPme : {}", id);

        return  demandecessionRepository.findAllByPmeIdPME(id);
    }

    @Override
    public Page<DemandeCessionDto> findAllPMEDemandes(Pageable pageable,Long id) {
        log.info("DemandeCessionService:findAllPMEDemandes request params idPme : {}", id);

        return  demandecessionRepository
                .findAllByPmeIdPME(pageable,id)
                .map(cessionMapper::asDTO);
    }

}
