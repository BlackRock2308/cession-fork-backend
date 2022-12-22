/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.entitiesDtos.StatutDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.ConventionRepository;
import sn.modelsis.cdmp.repositories.ObservationRepository;
import sn.modelsis.cdmp.repositories.TextConventionRepository;
import sn.modelsis.cdmp.services.*;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.Qrcode;
import sn.modelsis.cdmp.util.Status;

/**
 * @author SNDIAGNEF
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConventionServiceImpl implements ConventionService{
  private final ConventionRepository conventionRepository;
  private final DocumentService documentService;

  @Autowired
  private SpringTemplateEngine thymeleafTemplateEngine;
  @Autowired
  private ObservationRepository observationRepository;

  @Autowired
  private StatutService statutService;

  @Autowired
  private DemandeCessionService demandeCessionService;

  private final ParametrageDecoteService decoteService;

  @Autowired
  private  ObservationService observationService;

  @Autowired
  private TextConventionRepository textConventionRepository;



  @Value("${server.qrcode_folder}")
  private String path;

  @Override
  public Convention save(ConventionDto conventionDto) {
    Convention newConvention = new Convention();
    TextConvention textConvention = textConventionRepository.save(DtoConverter.convertToEntity(conventionDto.getTextConventionDto()));
    newConvention.setTextConvention(textConvention);
    newConvention.setDateConvention(LocalDateTime.now());
    DemandeCession demandeCession =
            demandeCessionService.findByIdDemande(conventionDto.getIdDemande()).orElse(null);
    try
    {
      if(demandeCession.getIdDemande()!=null && demandeCession.getStatut().getCode().equals(Status.getNonRisquee())) {
        ParametrageDecote exactParametrageDecote = decoteService.findIntervalDecote(demandeCession.getBonEngagement().getMontantCreance()).orElse(null);
        newConvention.setDemandeCession(demandeCession);
        newConvention.setDecote(exactParametrageDecote);
        newConvention.setValeurDecote(exactParametrageDecote.getDecoteValue());
        newConvention.setDemandeCession(demandeCession);
        newConvention.setValeurDecoteByDG(exactParametrageDecote.getDecoteValue()); //valeurDecoteDG take the value of the params decote
        newConvention = conventionRepository.save(newConvention);
          saveDocumentConvention(newConvention);
          Statut statut = statutService.findByCode(Status.getConventionGeneree());
          demandeCession.setStatut(statut);
          demandeCession.getConventions().add(newConvention);
          demandeCessionService.save(demandeCession);
              }
    } catch (Exception ex){
      log.error("Exception occured while adding convention. Error message : {}", ex.getMessage());
      throw new CustomException("Exception occured while adding new convention");
    }
    return newConvention;
	}

  @Override
  public Convention corriger(ConventionDto conventionDto){
    Convention convention = null;
    if(conventionDto.getIdConvention()!=null){
      convention = getConvention(conventionDto.getIdConvention()).orElse(null);
      TextConvention textConvention = textConventionRepository.save(DtoConverter.convertToEntity(conventionDto.getTextConventionDto()));
      convention.setTextConvention(textConvention);
        saveDocumentConvention(convention);
        Statut statut = statutService.findByCode(Status.getConventionCorrigee());
        convention.getDemandeCession().setStatut(statut);
        demandeCessionService.save(convention.getDemandeCession());
    }
    return convention;
  }

  @Override
  public List<Convention> findAll(){
    log.info("ConventionService:findAll fetching all conventions ........");

    return new ArrayList<>(conventionRepository
            .findAll());
  }

  @Override
  public Optional<Convention> getConvention(Long id) {
    log.info("ConventionService:getConvention fetching single convention with id : {}", id);

    return conventionRepository
            .findById(id)
            ;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    log.info("ConventionService:delete deleting convention with id : {}", id);

    conventionRepository.deleteConvention(id);

  }
  
  @Override
  public Optional<Convention> upload(Long id, MultipartFile file, TypeDocument type)
      throws IOException {
    Optional<Convention> convention = conventionRepository.findById(id);
    if (convention.isPresent()) {

      ConventionDocuments doc = (ConventionDocuments) documentService.upload(file, id,
              ConventionDocuments.PROVENANCE, type);
      convention.get().getDocuments().add(doc);

      return Optional.of(conventionRepository.save(convention.get()));

    }
    return convention;
  }

  @Override
  public Convention updateValeurDecote(Long idConvention, double newValue){
    Optional <Convention> optional = Optional.ofNullable(conventionRepository.findById(idConvention).orElse(null));
    optional.get().setValeurDecoteByDG(newValue);

    log.info("ValeurDecote by DG in convention before saving: {}", optional.get().getValeurDecoteByDG());
    Convention convention = conventionRepository.saveAndFlush(optional.get());
    saveDocumentConventionSigner(convention);
    return convention;
  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Convention transmettreConvention (Long id,
                                                 Convention newConvention) {
    Optional <Convention> existingConvention;
    try{
      log.info("ConventionService:transmettreConvention updating ........");
      existingConvention = conventionRepository.findById(id);

      existingConvention.get().setValeurDecote(newConvention.getValeurDecote());
      existingConvention.get().setValeurDecoteByDG(newConvention.getValeurDecoteByDG());
      existingConvention.get().setActiveConvention(newConvention.isActiveConvention());
      existingConvention.get().setDateConvention(newConvention.getDateConvention());
      existingConvention.get().setPme(newConvention.getPme());
      log.info("DocumentService:supression de l'ancien document de la convention ........");
      for (Documents doc:existingConvention.get().getDocuments()
           ) {
        documentService.delete(doc.getId());
      }
      existingConvention.get().setDocuments(null);
      log.info("DocumentService:supression de l'ancien document de la convention terminée.");
     
      log.info("ConventionService:transmettreConvention with document : {}",existingConvention.get().getDocuments());

      conventionRepository.saveAndFlush(existingConvention.get());
      log.info("ConventionService:transmettreConvention update convention with id : {}",existingConvention.get().getIdConvention());
    }catch (Exception ex){
      log.error("Exception occured while updating convention with id : {}",id );
      throw new CustomException("Error occured while updating this convention");
    }
    return existingConvention.get();
  }

  

  public String convertDate(LocalDateTime date, boolean signe){
    if(date.getDayOfMonth() <10){
      return "0"+date.getDayOfMonth()+"-"+date.getMonthValue()+"-"+date.getYear()+" à "+date.getHour()+":"+date.getMinute();
    }
    return date.getDayOfMonth()+"-"+date.getMonthValue()+"-"+date.getYear()+" à "+date.getHour()+":"+date.getMinute();
  }

  public String convertDate(LocalDateTime date){
    if(date.getDayOfMonth() <10){
      return "0"+date.getDayOfMonth()+"-"+date.getMonthValue()+"-"+date.getYear();
    }
    return date.getDayOfMonth()+"-"+date.getMonthValue()+"-"+date.getYear();
  }

  public String getInfoQRcode(Observation observation){
    return  "Prénom: "+observation.getUtilisateur().getPrenom()+ "\n Nom: "+ observation.getUtilisateur().getNom()+
            "\n Email: "+observation.getUtilisateur().getEmail()+"\n"+"Singé le "+convertDate(observation.getDateObservation(),true);
  }


  public void saveDocumentConvention(Convention convention)  {
    Map<String, Object> contextModel = new HashMap<>();
    contextModel.put("convention", convention);
    String dateStr= convertDate(LocalDateTime.now());
    contextModel.put("date", dateStr);
    String fileName = "convention_generer.pdf";
    Context thymeleafContext = new Context();
    thymeleafContext.setVariables(contextModel);
    String htmlBody = thymeleafTemplateEngine.process("convention_de_cession.html", thymeleafContext);

    try{
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(htmlBody);
    renderer.layout();
    renderer.createPDF(outputStream, false);
    renderer.finishPDF();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    Documents[] documents = convention.getDocuments().toArray(new Documents[convention.getDocuments().size()]);
    if(documents.length==0){
      MultipartFile file = new MockMultipartFile(fileName,fileName,"", inputStream);
      upload(convention.getIdConvention(), file, TypeDocument.CONVENTION);
    }else {
      FileOutputStream output = new FileOutputStream(documents[0].getUrlFile());
      output.write(inputStream.readAllBytes());
      output.close();
    }
      log.info("ConventionService:générer ", convention.getIdConvention());
    } catch (Exception ex) {
      log.error("Erreur lors de la génération du document : {}", convention.getIdConvention());
      throw new CustomException("Error occured while updating this convention");
    }
  }

  private Observation getLastSignature(Long idDemande, String statut){
    List<Observation> observations = observationRepository.findDistinctByDemandeIdDemandeAndStatut_Code(idDemande,statut);
   return observations.get(observations.size()-1);
  }
  @Override
  public void saveDocumentConventionSigner(Convention convention) {
    Map<String, Object> contextModel = new HashMap<>();
    contextModel.put("convention", convention);
    String dateStr = convertDate(LocalDateTime.now());
    contextModel.put("date", dateStr);
    Observation obPME = getLastSignature(convention.getDemandeCession().getIdDemande(), Status.getConventionSigneeParPME());
    if (obPME != null) {
      String qrCodePME = "Prénom: " + convention.getDemandeCession().getPme().getPrenomRepresentant() + "\n" + "Nom: " + convention.getDemandeCession().getPme().getNomRepresentant() +
              "\n" + "Mail: " + convention.getDemandeCession().getPme().getEmail() + "\n" + "Singé le " + convertDate(obPME.getDateObservation(),true);
      qrCodePME = Qrcode.generateQRCode(qrCodePME, path + "/pme.png");
      contextModel.put("qrCodePME", qrCodePME);
      Observation obDG = getLastSignature(convention.getDemandeCession().getIdDemande(), Status.getConventionSigneeParDG());
      if (obDG != null) {
        String qrCodeCDMP = getInfoQRcode(obDG);
        qrCodeCDMP = Qrcode.generateQRCode(qrCodeCDMP, path + "/cdmp.png");
        contextModel.put("qrCodeCDMP", qrCodeCDMP);
        /*Observation obORD = getLastSignature(convention.getDemandeCession().getIdDemande(), Status.getConventionAcceptee());
        if (obORD != null) {
          String qrCodeORD = getInfoQRcode(obORD);
          qrCodeORD = Qrcode.generateQRCode(qrCodeORD, path + "/ordonnaneur.png");
          contextModel.put("qrCodeORD", qrCodeORD);
        }*/
      }
    }
    Context thymeleafContext = new Context();
    thymeleafContext.setVariables(contextModel);
    String htmlBody = thymeleafTemplateEngine.process("convention_de_cession.html", thymeleafContext);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(htmlBody);
    renderer.layout();
    renderer.createPDF(outputStream, false);
    renderer.finishPDF();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    Documents[] documents = convention.getDocuments().toArray(new Documents[convention.getDocuments().size()]);
    try {
      FileOutputStream output = new FileOutputStream(documents[0].getUrlFile());
      output.write(inputStream.readAllBytes());
      output.close();
      log.info("ConventionService:saveDocumentConventionSigner ", convention.getIdConvention());
    } catch (Exception ex) {
      log.error("Erreur lors de la génération du document id : {}", convention.getIdConvention());
      throw new CustomException("Error occured while updating this convention");
    }
  }

  @Override
  public void conventionSignerParPME(Long idConvention, Long idPME) {
    Convention convention = conventionRepository.findById(idConvention).orElse(null);
    Statut updatedStatut = statutService.findByCode(Status.getConventionSigneeParPME());
    convention.getDemandeCession().setStatut(updatedStatut);
    DemandeCession demandeCessionDto = demandeCessionService.save(convention.getDemandeCession());
    log.info("Convention :signerConventionPME received from Database {}",
            demandeCessionDto.getIdDemande());
    ObservationDto observation = new ObservationDto();
    observation.setDemandeid(demandeCessionDto.getIdDemande());
    observation.setDateObservation(LocalDateTime.now());
    observation.setUtilisateurid(idPME);
    StatutDto statut = new StatutDto();
    statut.setLibelle(Status.getConventionSigneeParPME());
    observation.setStatut(statut);
    observationService.saveNewObservation(observation);
      saveDocumentConventionSigner(convention);
  }

  @Override
  public void conventionSignerParDG(Long idConvention, Long idDG) {
    Convention convention = conventionRepository.findById(idConvention).orElse(null);
    Statut updatedStatut = statutService.findByCode(Status.getConventionSigneeParDG());
    convention.getDemandeCession().setStatut(updatedStatut);
    DemandeCession demandeCessionDto = demandeCessionService.save(convention.getDemandeCession());
    log.info("Convention:signerConventionDG received from Database {}",
            demandeCessionDto.getIdDemande());
    ObservationDto observation = new ObservationDto();
    observation.setDemandeid(demandeCessionDto.getIdDemande());
    observation.setDateObservation(LocalDateTime.now());
    observation.setUtilisateurid(idDG);
    StatutDto statut = new StatutDto();
    statut.setLibelle(Status.getConventionSigneeParDG());
    observation.setStatut(statut);
    observationService.saveNewObservation(observation);
    saveDocumentConventionSigner(convention);
  }
}
