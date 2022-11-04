package sn.modelsis.cdmp.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;
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
public class DemandeCessionServiceImpl implements DemandeCessionService {

    private final DemandeCessionRepository demandecessionRepository;
    private final BonEngagementRepository bonEngagementRepository;
    private final PmeRepository pmeRepository;
    private final StatutRepository statutRepository;
    private final ObservationRepository observationRepository;
    private final DemandeCessionMapper cessionMapper;

    private final CreanceMapper creanceMapper;


    @Override
    @Transactional
    public DemandeCession saveCession(DemandeCession demandeCession) {
        //BonEngagement be = demandecession.getBonEngagement();
        //bonEngagementRepository.save(be);
//        Optional<DemandeCession> optional = demandecessionRepository.findB(vm.getPhone());
//        ExceptionUtils.absentOrThrow(optional, ItemExistsException.PHONE_EXISTS, vm.getPhone());
        demandeCession.setDateDemandeCession(new Date());
//        Statut statut = new Statut();
//        statut.setLibelle(statutRepository.findByLibelle("SOUMISE").getLibelle());
        Statut statut = statutRepository.findByLibelle("SOUMISE");
        demandeCession.setStatut(statut);
        //statutRepository.save(statut);

        return demandecessionRepository.save(demandeCession);
    }
//    @Override
//    public List<DemandeCession> findAll(){
//        return demandecessionRepository.findAll();
//    }

//    @Override
//    @Transactional
//    public DemandeCession addCession(DemandeCessionDto demandeCessionDto) {
//        DemandeCession demandeCession = cessionMapper.asEntity(demandeCessionDto);
//
//        Optional<Pme> optional = pmeRepository.findByNinea(demandeCessionDto.getPme().getNinea());
//        ExceptionUtils.absentOrThrow(optional, ItemExistsException.NINEA_PME_EXIST, demandeCessionDto.getPme().getNinea());
//
//        Optional<BonEngagement> optionalBe = bonEngagementRepository.findById(demandeCessionDto.getBonEngagement().getId());
//        ExceptionUtils.absentOrThrow(optionalBe, ItemNotFoundException.BONENGAGEMENT_BY_ID, demandeCessionDto.getBonEngagement().getId().toString());
//
//        if(optional.isPresent() ){
//            demandeCession.setPme(optional.get());
//            demandeCession.setBonEngagement(optionalBe.get());
//            demandeCession.setDateDemandeCession(new Date());
//            Statut statut=statutRepository.findByLibelle(Statuts.ADHESION_SOUMISE);
//            demandeCession.setStatut(statut);
//            return demandecessionRepository.save(demandeCession);
//        }
//        else {
//            throw new CustomException("Erreur, Impossible d'effectuer cette demande");
//        }
//    }

    @Override
    public Page<DemandeCessionDto> findAll(Pageable pageable){
        return demandecessionRepository
                .findAll(pageable)
                .map(cessionMapper::asDTO);
    }

    @Override
    public Optional<DemandeCessionDto> findById(Long id) {
        final Optional <DemandeCessionDto> optional = Optional.of(demandecessionRepository
                .findById(id)
                .map(cessionMapper::asDTO)
                .orElseThrow());
        ExceptionUtils.absentOrThrow(optional, ItemNotFoundException.DEMANDE_CESSION_BY_ID, id.toString());

        return optional;

    }

    @Override
    public Optional <DemandeCessionDto> getDemandeCession(Long id) {
        return demandecessionRepository
                .findById(id)
                .map(cessionMapper::asDTO);

    }

    @Override
    public List<DemandeCession> findAllPMEDemandes(Long id) {
        return demandecessionRepository.findAllByPmeIdPME(id);
    }

    /** Analyse des Demande de Cession REJETTEE ou RECEVABLE **/

    @Override
    @Transactional
    public DemandeCession rejectionDemandeCession(Long idDemande ){
        Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
        Statut updatedStatut = statutRepository.findByLibelle("REJETEE");
        optional.get().setStatut(updatedStatut);

        DemandeCession demandeCessionDto = optional.get();

        return demandecessionRepository.save(demandeCessionDto);

    }

    @Override
    @Transactional
    public DemandeCession acceptDemandeCession(Long idDemande ){
        Optional<DemandeCession> optional = Optional.ofNullable(demandecessionRepository.findByDemandeId(idDemande));
        Statut updatedStatut = statutRepository.findByLibelle("RECEVABLE");
        optional.get().setStatut(updatedStatut);

        DemandeCession demandeCessionDto = optional.get();

        return demandecessionRepository.save(demandeCessionDto);

    }


    @Override
    @Transactional
    public DemandeCessionDto validerCession(DemandeCessionDto demandecessionDto) {
        Statut statut = DtoConverter.convertToEntity(demandecessionDto.getStatut());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        BonEngagement bonEngagement = DtoConverter.convertToEntity(demandecessionDto.getBonEngagement());
       // Pme pme = DtoConverter.convertToEntity(demandecessionDto.getPme());
        Observation observation = DtoConverter.convertToEntity((ObservationDto) demandecessionDto.getObservations());
        // pme.setHasninea(true);
       // pmeRepository.save(pme);
        statutRepository.save(statut);
        bonEngagementRepository.save(bonEngagement);
        observationRepository.save(observation);
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
    }

    // Validation du dossier a l'étape d'analyse du risque
    @Override
    @Transactional
    public DemandeCessionDto validerAnalyse(DemandeCessionDto demandecessionDto) {
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        Observation observation = DtoConverter.convertToEntity((ObservationDto) demandecessionDto.getObservations());
        Pme pme = demandecession.getPme();
        //statut.setLibelle(Statuts.NON_RISQUEE);
        pmeRepository.save(pme);
        //statutRepository.save(statut);
        demandecession.setStatut(statutRepository.findByLibelle("NON_RISQUEE"));
        observationRepository.save(observation);
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
    }


    //rejet du dossier a l'étape d'analyse du risque
    @Override
    @Transactional
    public DemandeCessionDto rejeterAnalyse(DemandeCessionDto demandecessionDto) {
        Statut statut = DtoConverter.convertToEntity(demandecessionDto.getStatut());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        Observation observation = DtoConverter.convertToEntity((ObservationDto) demandecessionDto.getObservations());
        Pme pme = demandecession.getPme();
        //statut.setLibelle(statutRepository.findByLibelle("REJETEE").getLibelle());
        demandecession.setStatut(statutRepository.findByLibelle("REJETEE"));
        pmeRepository.save(pme);
        //statutRepository.save(statut);
        observationRepository.save(observation);
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;

    }

    @Override
    @Transactional
    public DemandeCessionDto demanderComplements(DemandeCessionDto demandecessionDto) {
        Statut statut = DtoConverter.convertToEntity(demandecessionDto.getStatut());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        Observation observation = DtoConverter.convertToEntity((ObservationDto) demandecessionDto.getObservations());
        Pme pme = demandecession.getPme();
        //statut.setLibelle(Statuts.COMPLEMENT_REQUIS);
        demandecession.setStatut(statutRepository.findByLibelle("COMPLEMENT_REQUIS"));
        pmeRepository.save(pme);
        //statutRepository.save(statut);
        observationRepository.save(observation);
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
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
    @Transactional
    public DemandeCessionDto validerRecevabilite(DemandeCessionDto demandecessionDto) {

        DemandeCession demandeCession = getDemandeCessionDto(demandecessionDto);
        //demandeCession.setStatut(statutRepository.findByLibelle(Statuts.RECEVABLE));
        demandeCession.setStatut(statutRepository.findByLibelle("RECEVABLE"));

        DemandeCession result=demandecessionRepository.save(demandeCession);
        return DtoConverter.convertToDto(result) ;
    }

    //Rejet de la recevabilité de la demande de cession
    @Override
    @Transactional
    public DemandeCessionDto rejeterRecevabilite(DemandeCessionDto demandecessionDto) {
        DemandeCession demandeCession = getDemandeCessionDto(demandecessionDto);
        //demandeCession.setStatut(statutRepository.findByLibelle(Statuts.REJETEE));
        demandeCession.setStatut(statutRepository.findByLibelle("REJETEE"));

        DemandeCession result=demandecessionRepository.save(demandeCession);
        return DtoConverter.convertToDto(result) ;

    }
}
