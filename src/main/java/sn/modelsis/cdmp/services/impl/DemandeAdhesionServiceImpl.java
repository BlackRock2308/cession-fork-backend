package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.DemandeDocuments;
import sn.modelsis.cdmp.entities.Documents;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.email.EmailMessageWithTemplate;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.mappers.DemandeAdhesionMapper;
import sn.modelsis.cdmp.repositories.DemandeAdhesionRepository;
import sn.modelsis.cdmp.repositories.DocumentsRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.util.Constants;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.RestTemplateUtil;



@Service
@Slf4j
public class DemandeAdhesionServiceImpl implements DemandeAdhesionService {
    private final DemandeAdhesionRepository demandeAdhesionRepository;
    private final PmeRepository pmeRepository;
    private final StatutRepository statutRepository;
    private  final DocumentService documentService;

    @Autowired
    private DocumentsRepository documentRepository;
    private final DemandeAdhesionMapper adhesionMapper;

    private  final DemandeService demandeService;

    private final RestTemplateUtil restTemplateUtil;

    private final String sendMail = Constants.SEND_MAIL_WITH_TEMPLATE;

    @Value("${server.notification}")
    private String HOST_NOTIFICATION;

    @Value("${server.email_cdmp}")
    private String EMAIL_CDMP;

    public DemandeAdhesionServiceImpl(DemandeAdhesionRepository demandeAdhesionRepository, PmeRepository pmeRepository, StatutRepository statutRepository, DocumentService documentService, DemandeAdhesionMapper adhesionMapper, DemandeService demandeService, RestTemplateUtil restTemplateUtil) {
        this.demandeAdhesionRepository = demandeAdhesionRepository;
        this.pmeRepository = pmeRepository;
        this.statutRepository = statutRepository;
        this.documentService = documentService;
        this.adhesionMapper = adhesionMapper;
        this.demandeService = demandeService;
        this.restTemplateUtil = restTemplateUtil;
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DemandeAdhesion saveAdhesion(DemandeAdhesionDto demandeAdhesionDto) {
        DemandeAdhesion demandeAdhesion = adhesionMapper.asEntity(demandeAdhesionDto);
        
        pmeRepository.findById(demandeAdhesionDto.getIdPME()).ifPresentOrElse(
                (value)
                        -> { 
                    List<DemandeAdhesion> demandes = demandeAdhesionRepository.findAllByPmeIdPME(value.getIdPME());
                    if(demandes.size() == 0) {
                        demandeAdhesion.setPme(value);
                        demandeAdhesion.setDateDemandeAdhesion(new Date());
                        Statut statut=statutRepository.findByLibelle("ADHESION_SOUMISE");
                        demandeAdhesion.setStatut(statut);
                        if(demandeAdhesion.getIdDemande()==null){
                            demandeAdhesion.setNumeroDemande(demandeService.getNumDemande());
                        }
                    }
                    else {
                        demandes.forEach(d ->{
                            String code = d.getStatut().getCode();
                            if(code == "ADHESION_SOUMISE") {
                                throw new CustomException("Vous avez déjà une demande en cours d'exécution ");
                            }
                            else if(code == "ADHESION_ACCEPTEE") {
                                throw new CustomException("Vous avez déjà un compte ");
                            }
                            else if(code == "ADHESION_REJETEE") {
                                demandeAdhesion.setPme(value);
                                demandeAdhesion.setDateDemandeAdhesion(new Date());
                                Statut statut=statutRepository.findByLibelle("ADHESION_SOUMISE");
                                demandeAdhesion.setStatut(statut);
                                if(demandeAdhesion.getIdDemande()==null){
                                    demandeAdhesion.setNumeroDemande(demandeService.getNumDemande());
                                }
                            }
                        });
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
        return demandeAdhesionRepository.findAll(pageable).map(DtoConverter::convertToDto);
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
            //send email to notify rejection
            demandeAdhesion = demandeAdhesionRepository.save(optional.get());
            String email = optional.get().getPme().getEmail();
            EmailMessageWithTemplate emailMessageWithTemplate = sendEmailRejetAdhesion(email);
            restTemplateUtil.sendEmailWithTemplate(HOST_NOTIFICATION+sendMail,emailMessageWithTemplate);
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
            if(optional.isEmpty())
                throw new CustomException("Can not find this Demand");
        //    String email = optional.get().getPme().getEmail();
        //    EmailMessageWithTemplate emailMessageWithTemplate = sendEmailValiderAdhesion(email);
        //    restTemplateUtil.sendEmailWithTemplate(HOST_NOTIFICATION+sendMail,emailMessageWithTemplate);
            demandeAdhesion = demandeAdhesionRepository.save(optional.get());
        }catch (Exception ex){
            log.error("Exception occured while Accepting Demande Adhesion. Exception message : {}", ex.getMessage());
            throw new CustomException("Exception occured while accepting Demande Adhesion");
        }
        return demandeAdhesion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<DemandeAdhesion> upload(Long demandeId, MultipartFile file, String type)
            throws IOException {

        DemandeDocuments doc;

        Optional<DemandeAdhesion> demandeAdhesion = demandeAdhesionRepository.findById(demandeId);

        List<Documents> documents = documentService.findAll();

        log.info("size of Docu : " + demandeAdhesion.get().getDocuments().size());

        if (demandeAdhesion.isPresent()) {

            doc = (DemandeDocuments) documentService.upload(file, demandeId,
                    DemandeDocuments.PROVENANCE, type);

            documents.forEach((e)->{
                if((e.getIdprovenance().equals(demandeId)) & (demandeAdhesion.get().getDocuments().size() >= 2) ){
                    log.info("Id Document : " + e.getId());
                    documentRepository.deleteDocument(e.getId());
                }
            });
            demandeAdhesion.get().getDocuments().add(doc);

            log.info("Final Size of Document : " + documentService.findAll().size());

            return Optional.of(demandeAdhesionRepository.save(demandeAdhesion.get()));

        }
        return demandeAdhesion;
    }


    public EmailMessageWithTemplate sendEmailValiderAdhesion(String email){
        EmailMessageWithTemplate emailMessageWithTemplate = new EmailMessageWithTemplate();
        emailMessageWithTemplate.setTemplateName("cdmp-accepte-adhesion");
        emailMessageWithTemplate.setExpediteur(EMAIL_CDMP);
        emailMessageWithTemplate.setDestinataire(email);
        emailMessageWithTemplate.setObjet("Acceptation Demande");
        return emailMessageWithTemplate ;
    }

    public EmailMessageWithTemplate sendEmailRejetAdhesion(String email){
        EmailMessageWithTemplate emailMessageWithTemplate = new EmailMessageWithTemplate();
        emailMessageWithTemplate.setTemplateName("cdmp-rejet-demande-adhesion");
        emailMessageWithTemplate.setExpediteur(EMAIL_CDMP);
        emailMessageWithTemplate.setDestinataire(email);
        emailMessageWithTemplate.setObjet("Rejet Demande Adhesion");
        return emailMessageWithTemplate ;
    }
}
