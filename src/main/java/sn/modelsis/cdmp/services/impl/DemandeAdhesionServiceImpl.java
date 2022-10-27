package sn.modelsis.cdmp.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.util.DtoConverter;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class DemandeAdhesionServiceImpl implements DemandeAdhesionService {
    private final DemandeAdhesionRepository demandeAdhesionRepository;
    private final PmeRepository pmeRepository;
    private final StatutRepository statutRepository;

    @Override
    public DemandeAdhesion saveAdhesion(DemandeAdhesion demandeadhesion) {
        demandeadhesion.setDateDemandeAdhesion(new Date());
        Statut statut=new Statut();
        statut.setLibelle(Statuts.SOUMISE);
        demandeadhesion.setStatut(statut);
        statutRepository.save(statut);
        return demandeAdhesionRepository.save(demandeadhesion);
    }

    /**
     This function is used inside rejetAdhesion and validerAdhesion
     to avoid boilerplate code inside those two functions
     */
    public DemandeAdhesionDto getDemandeAdhesionDto(DemandeAdhesionDto demandeAdhesionDto) {
        Statut statut = DtoConverter.convertToEntity(demandeAdhesionDto.getStatut());
        DemandeAdhesion demandeadhesion = DtoConverter.convertToEntity(demandeAdhesionDto);
        Pme pme = demandeadhesion.getPme();
        pmeRepository.save(pme);
        statutRepository.save(statut);
        DemandeAdhesion result=demandeAdhesionRepository.save(demandeadhesion);
        return DtoConverter.convertToDto(result) ;
    }

    @Override
    @Transactional
    public DemandeAdhesionDto rejetAdhesion(DemandeAdhesionDto demandeAdhesionDto) {
        return getDemandeAdhesionDto(demandeAdhesionDto);
    }

    @Override
    @Transactional
    public DemandeAdhesionDto validerAdhesion(DemandeAdhesionDto demandeAdhesionDto) {
        return getDemandeAdhesionDto(demandeAdhesionDto);
    }

    @Override
    public List<DemandeAdhesion> findAll(){
        return demandeAdhesionRepository.findAll();
    }
}
