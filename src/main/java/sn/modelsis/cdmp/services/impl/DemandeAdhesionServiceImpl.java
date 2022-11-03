package sn.modelsis.cdmp.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.ItemExistsException;
import sn.modelsis.cdmp.exceptions.ItemNotFoundException;
import sn.modelsis.cdmp.mappers.DemandeAdhesionMapper;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.ExceptionUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DemandeAdhesionServiceImpl implements DemandeAdhesionService {
    private final DemandeAdhesionRepository demandeAdhesionRepository;
    private final PmeRepository pmeRepository;
    private final StatutRepository statutRepository;
    private  final DocumentService documentService;

    private final DemandeAdhesionMapper adhesionMapper;

    @Override
    public DemandeAdhesion saveAdhesion(DemandeAdhesionDto demandeAdhesionDto) {
        DemandeAdhesion demandeAdhesion=DtoConverter.convertToEntity(demandeAdhesionDto);
        pmeRepository.findById(demandeAdhesionDto.getIdPME()).ifPresentOrElse(
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

    @Override
    @Transactional
    public DemandeAdhesion rejetAdhesion(Long id) {
        Statut statut=statutRepository.findByLibelle(Statuts.ADHESION_REJETEE);
        DemandeAdhesion demandeAdhesion=demandeAdhesionRepository.findById(id).orElseThrow();
        demandeAdhesion.setStatut(statut);
        return demandeAdhesionRepository.save(demandeAdhesion);
    }

    @Override
    @Transactional
    public DemandeAdhesion validerAdhesion(Long id) {

        Statut statut=statutRepository.findByLibelle(Statuts.ADHESION_ACCEPTEE);
        DemandeAdhesion demandeAdhesion=demandeAdhesionRepository.findById(id).orElseThrow();
        demandeAdhesion.setStatut(statut);
        return demandeAdhesionRepository.save(demandeAdhesion);
    }

//    @Override
//    public Optional<DemandeDTO> findById(UUID uuid) {
//        return repository
//                .findById(uuid)
//                .map(mapper::asDTO);
//    }


    @Override
    public List<DemandeAdhesionDto> findAll(){
        return demandeAdhesionRepository
                .findAll()
                .stream()
                .map(adhesionMapper::asDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DemandeAdhesionDto> findById(Long id) {
        return demandeAdhesionRepository
                .findById(id)
                .map(adhesionMapper::asDTO);
//        ExceptionUtils.absentOrThrow(optional, ItemNotFoundException.DEMANDE_ADHESION_ID, id.toString());
//        return optional;
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
