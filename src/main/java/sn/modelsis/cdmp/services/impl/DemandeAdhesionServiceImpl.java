package sn.modelsis.cdmp.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.mappers.DemandeAdhesionMapper;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.DocumentService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import sn.modelsis.cdmp.repositories.DemandeAdhesionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;


@AllArgsConstructor
@Service
@Slf4j
public class DemandeAdhesionServiceImpl implements DemandeAdhesionService {
    private final DemandeAdhesionRepository demandeAdhesionRepository;
    private final PmeRepository pmeRepository;
    private final StatutRepository statutRepository;
    private  final DocumentService documentService;
    private final DemandeAdhesionMapper adhesionMapper;

    private  final DemandeService demandeService;

//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public DemandeAdhesion saveAdhesion(DemandeAdhesion demandeAdhesion) {
//                    demandeAdhesion.setDateDemandeAdhesion(new Date());
//                    Statut statut=statutRepository.findByLibelle("ADHESION_SOUMISE");
//                    demandeAdhesion.setStatut(statut);
//        if(demandeAdhesion.getIdDemande()==null){
//            demandeAdhesion.setNumeroDemande(demandeService.getNumDemande());
//        }
//        DemandeAdhesion demandeAdhesion1 = demandeAdhesionRepository.save(demandeAdhesion);
//        log.info("DemandeAdhesionService:saveAdhesion execution finished successfully");
//        return demandeAdhesion1;
//
//    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeAdhesion saveAdhesion(DemandeAdhesionDto demandeAdhesionDto) {
        DemandeAdhesion demandeAdhesion = adhesionMapper.asEntity(demandeAdhesionDto);
        pmeRepository.findById(demandeAdhesionDto.getIdPME()).ifPresentOrElse(
                (value)
                        -> {
                    demandeAdhesion.setPme(value);
                    demandeAdhesion.setDateDemandeAdhesion(new Date());
                    Statut statut=statutRepository.findByLibelle("ADHESION_SOUMISE");
                    demandeAdhesion.setStatut(statut);
                    if(demandeAdhesion.getIdDemande()==null){
                        demandeAdhesion.setNumeroDemande(demandeService.getNumDemande());
                    }
                },
                ()
                        -> {
                    throw new CustomException("La PME n'existe pas");
                }
        );
        return demandeAdhesionRepository.save(demandeAdhesion);
    }


    @Override
    public Page<DemandeAdhesionDto> findAll(Pageable pageable){
        log.info("DemandeAdhesionService:findAll execution started ...");
        return demandeAdhesionRepository
                .findAll(pageable)
                .map(adhesionMapper::asDTO);
    }

    @Override
    public Optional<DemandeAdhesionDto> findById(Long id) {
        log.info("DemandeAdhesionService:findById request param {}", id);
        return Optional.of(demandeAdhesionRepository
                .findById(id)
                .map(adhesionMapper::asDTO)
                .orElseThrow());
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeAdhesion rejetAdhesion(Long id) {
        DemandeAdhesion demandeAdhesion;
        try{
            log.info("DemandeAdhesionService:rejetAdhesion request started");
            Optional<DemandeAdhesion> optional = demandeAdhesionRepository.findById(id);
            log.debug("DemandeAdhesionService:rejetAdhesion request params {}", id);
            Statut updatedStatut=statutRepository.findByLibelle("ADHESION_REJETEE");
            optional.get().setStatut(updatedStatut);
            demandeAdhesion = demandeAdhesionRepository.save(optional.get());
            log.info("DemandeAdhesionService:rejetAdhesion receive response from Database {}",optional.get());
        } catch (Exception ex){
            log.error("Exception occured while rejecting Demande Adhesion. Exception message : {}", ex.getMessage());
            throw new CustomException("Exception occured while rejecting Demande Adhesion");
        }
        return demandeAdhesion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeAdhesion validerAdhesion(Long id) {
        DemandeAdhesion demandeAdhesion;
        try{
            log.info("DemandeAdhesionService:validerAdhesion request started");
            Optional<DemandeAdhesion> optional = demandeAdhesionRepository.findById(id);
            log.debug("DemandeAdhesionService:validerAdhesion request params {}", id);
            Statut updatedStatut=statutRepository.findByLibelle("ADHESION_ACCEPTEE");
            optional.get().setStatut(updatedStatut);
            demandeAdhesion = demandeAdhesionRepository.save(optional.get());
        }catch (Exception ex){
            log.error("Exception occured while Accepting Demande Adhesion. Exception message : {}", ex.getMessage());
            throw new CustomException("Exception occured while accepting Demande Adhesion");
        }
        return demandeAdhesion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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
