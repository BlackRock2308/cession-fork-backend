package sn.modelsis.cdmp.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DemandeAdhesionServiceImpl implements DemandeAdhesionService {
    private final DemandeAdhesionRepository demandeAdhesionRepository;
    private final PmeRepository pmeRepository;
    private final StatutRepository statutRepository;

    private  final DocumentService documentService;

    @Override
    public DemandeAdhesion saveAdhesion(DemandeAdhesionDto demandeAdhesionDto) {
        DemandeAdhesion demandeAdhesion=DtoConverter.convertToEntity(demandeAdhesionDto);
        pmeRepository.findById(demandeAdhesionDto.getPmeId()).ifPresentOrElse(
                (value)
                        -> {
                    demandeAdhesion.setPme(value);
                    demandeAdhesion.setDateDemandeAdhesion(new Date());
                    Statut statut=statutRepository.findByLibelle(Statuts.ADHESION_SOUMISE);
                    demandeAdhesion.setStatut(statut);
                },
                ()
                        -> {
                    throw new CustomException("La PME n'existe pas");
                }
        );

        return demandeAdhesionRepository.save(demandeAdhesion);
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

    @Override
    public Optional<DemandeAdhesion> upload(Long demandeId, MultipartFile file, TypeDocument type)
            throws IOException {
        Optional<DemandeAdhesion> demandeAdhesion = demandeAdhesionRepository.findById(demandeId);
        if (demandeAdhesion.isPresent()) {

            DemandeDocuments doc = (DemandeDocuments) documentService.upload(file, demandeId,
                    DemandeDocuments.PROVENANCE, type);
            demandeAdhesion.get().getDocuments().add(doc);

            return Optional.of(demandeAdhesionRepository.save(demandeAdhesion.get()));

        }
        return demandeAdhesion;
    }
}
