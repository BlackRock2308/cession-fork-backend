package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.util.DtoConverter;
import java.util.Date;
import java.util.List;


@Service
public class DemandeAdhesionServiceImpl implements DemandeAdhesionService {

    @Autowired
    private DemandeAdhesionRepository demandeAdhesionRepository;


    @Autowired
    private PmeRepository pmeRepository;
    @Autowired
    private StatutRepository statutRepository;


    @Override
        public DemandeAdhesion saveAdhesion(DemandeAdhesion demandeadhesion) {
        demandeadhesion.setDateDemandeAdhesion(new Date());
        Statut statut=new Statut();
        statut.setLibelle(Statuts.SOUMISE);
        demandeadhesion.setStatut(statut);
        statutRepository.save(statut);
        return demandeAdhesionRepository.save(demandeadhesion);
    }

    @Override
    public DemandeAdhesionDto rejetAdhesion(DemandeAdhesionDto demandeadhesion) {
        return null;
    }

    @Override
    public DemandeAdhesionDto validerAdhesion(DemandeAdhesionDto demandeahdesion) {
        return null;
    }

//    @Override
//    @Transactional
//    public DemandeAdhesionDto rejetAdhesion(DemandeAdhesionDto demandeAdhesionDto) {
//        Statut statut = DtoConverter.convertToEntity(demandeAdhesionDto.getStatut());
//        DemandeAdhesion demandeadhesion = DtoConverter.convertToEntity(demandeAdhesionDto);
//        Pme pme = DtoConverter.convertToEntity(demandeAdhesionDto.getPme());
//        pmeRepository.save(pme);
//        statutRepository.save(statut);
//        DemandeAdhesion result=demandeAdhesionRepository.save(demandeadhesion);
//        return DtoConverter.convertToDto(result) ;
//    }

//    @Override
//    @Transactional
//    public DemandeAdhesionDto validerAdhesion(DemandeAdhesionDto demandeAdhesionDto) {
//        Statut statut = DtoConverter.convertToEntity(demandeAdhesionDto.getStatut());
//        DemandeAdhesion demandeAdhesion = DtoConverter.convertToEntity(demandeAdhesionDto);
//        Pme pme = DtoConverter.convertToEntity(demandeAdhesionDto.getPme());
//        // pme.setHasninea(true);
//        pmeRepository.save(pme);
//        statutRepository.save(statut);
//        DemandeAdhesion result=demandeAdhesionRepository.save(demandeAdhesion);
//        return DtoConverter.convertToDto(result) ;
//    }

    @Override
    public List<DemandeAdhesion> findAll(){
        return demandeAdhesionRepository.findAll();
    }
}
