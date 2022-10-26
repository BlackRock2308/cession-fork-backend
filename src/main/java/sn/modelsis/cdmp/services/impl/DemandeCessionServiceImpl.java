package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.util.DtoConverter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class DemandeCessionServiceImpl implements DemandeCessionService {

    private final DemandeCessionRepository demandecessionRepository;

    private final BonEngagementRepository bonEngagementRepository;


    private final PmeRepository pmeRepository;

    private final StatutRepository statutRepository;


    private final ObservationRepository observationRepository;

    public DemandeCessionServiceImpl(DemandeCessionRepository demandecessionRepository,
                                     BonEngagementRepository bonEngagementRepository,
                                     PmeRepository pmeRepository,
                                     StatutRepository statutRepository,
                                     ObservationRepository observationRepository) {
        this.demandecessionRepository = demandecessionRepository;
        this.bonEngagementRepository = bonEngagementRepository;
        this.pmeRepository = pmeRepository;
        this.statutRepository = statutRepository;
        this.observationRepository = observationRepository;
    }

    @Override
    @Transactional
    public DemandeCession saveCession(DemandeCession demandecession) {
        BonEngagement be = demandecession.getBonEngagement();
        bonEngagementRepository.save(be);
        demandecession.setDateDemandeCession(new Date());
        Statut statut=new Statut();
        statut.setLibelle(Statuts.SOUMISE);
        demandecession.setStatut(statut);
        statutRepository.save(statut);
        /*for (Documents document:demandecession.getDocuments()
        ) {
            documentService.upload(document.);
        }*/

        return demandecessionRepository.save(demandecession);
    }

    @Override
    public Optional<DemandeCession> getDemandeCession(Long id) {
        return demandecessionRepository.findById(id);
    }


    @Override
    @Transactional
    public DemandeCessionDto rejeterCession(DemandeCessionDto demandecessionDto) {
        Statut statut = DtoConverter.convertToEntity(demandecessionDto.getStatut());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        Pme pme = DtoConverter.convertToEntity(demandecessionDto.getPme());
        //pme.setHasninea(false);
        pmeRepository.save(pme);
        statutRepository.save(statut);
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
    }

    @Override
    @Transactional
    public DemandeCessionDto validerCession(DemandeCessionDto demandecessionDto) {
        Statut statut = DtoConverter.convertToEntity(demandecessionDto.getStatut());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        BonEngagement bonEngagement = DtoConverter.convertToEntity(demandecessionDto.getBonEngagement());
        Pme pme = DtoConverter.convertToEntity(demandecessionDto.getPme());
        Observation observation = DtoConverter.convertToEntity((ObservationDto) demandecessionDto.getObservations());
        // pme.setHasninea(true);
        pmeRepository.save(pme);
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
        demandecession.setStatut(statutRepository.findAllByLibelle(Statuts.NON_RISQUEE));
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
        statut.setLibelle(Statuts.REJETEE);
        pmeRepository.save(pme);
        statutRepository.save(statut);
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
        statut.setLibelle(Statuts.COMPLEMENT_REQUIS);
        pmeRepository.save(pme);
        statutRepository.save(statut);
        observationRepository.save(observation);
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
    }


    //Validation de la recevabilité de la demande de cession
    @Override
    @Transactional
    public DemandeCessionDto validerRecevabilite(DemandeCessionDto demandecessionDto) {
        Observation observation = DtoConverter.convertToEntity((ObservationDto) demandecessionDto.getObservations());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        Pme pme = demandecession.getPme();
        BonEngagement be = demandecession.getBonEngagement();
        observationRepository.save(observation);
        pmeRepository.save(pme);
        bonEngagementRepository.save(be);
        demandecession.setStatut(statutRepository.findAllByLibelle(Statuts.RECEVABLE));
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
    }


    //Rejet de la recevabilité de la demande de cession
    @Override
    @Transactional
    public DemandeCessionDto rejeterRecevabilite(DemandeCessionDto demandecessionDto) {
        Observation observation = DtoConverter.convertToEntity((ObservationDto) demandecessionDto.getObservations());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        Pme pme = demandecession.getPme();
        BonEngagement be = demandecession.getBonEngagement();
        observationRepository.save(observation);
        pmeRepository.save(pme);
        bonEngagementRepository.save(be);
        demandecession.setStatut(statutRepository.findAllByLibelle(Statuts.REJETEE));
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
    }





    @Override
    public Optional<DemandeCession> findById(Long id) {
        return demandecessionRepository.findById(id);
    }

    @Override
    public List<DemandeCession> findAllPMEDemandes(Long id) {
        return demandecessionRepository.findAllByPmeIdPME(id);
    }

    @Override
    public List<DemandeCession> findAll(){
        return demandecessionRepository.findAll();
    }

}
