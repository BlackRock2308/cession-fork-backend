package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.DocumentService;
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
    public DemandeCessionDto rejetCession(DemandeCessionDto demandecessionDto) {
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
        Statut statut = DtoConverter.convertToEntity(demandecessionDto.getStatut());
        DemandeCession demandecession = DtoConverter.convertToEntity(demandecessionDto);
        Pme pme = DtoConverter.convertToEntity(demandecessionDto.getPme());
        // pme.setHasninea(true);
        pmeRepository.save(pme);
        statutRepository.save(statut);
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
    }

    @Override
    public DemandeCessionDto rejetAnalyse(DemandeCessionDto demandecession) {
        return null;

    }

    @Override
    public DemandeCessionDto demanderComplements(DemandeCessionDto demandecession) {
        return null;
    }

    @Override
    public List<DemandeCession> findAll(){
        return demandecessionRepository.findAll();
    }

}
