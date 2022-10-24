package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.util.Date;
import java.util.List;


@Service
public class DemandeCessionServiceImpl implements DemandeCessionService {

    @Autowired
    private DemandeCessionRepository demandecessionRepository;

    @Autowired
    private BonEngagementRepository bonEngagementRepository;

    @Autowired
    private PmeRepository pmeRepository;

    @Autowired
    private StatutRepository statutRepository;


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
        Pme pme = DtoConverter.convertToEntity(demandecessionDto.getPme());
        // pme.setHasninea(true);
        pmeRepository.save(pme);
        statutRepository.save(statut);
        DemandeCession result=demandecessionRepository.save(demandecession);
        return DtoConverter.convertToDto(result) ;
    }

    @Override
    public List<DemandeCession> findAll(){
        return demandecessionRepository.findAll();
    }

}
