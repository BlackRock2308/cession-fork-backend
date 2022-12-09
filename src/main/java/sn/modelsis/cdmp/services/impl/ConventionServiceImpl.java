/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.ConventionDocuments;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.ConventionRepository;
import sn.modelsis.cdmp.repositories.ObservationRepository;
import sn.modelsis.cdmp.services.ConventionService;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.services.StatutService;
import sn.modelsis.cdmp.util.Qrcode;

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

  @Value("${server.qrcode_folder}")
  private String path;

  @Override
  public Convention save(Convention convention) {
    Convention newConvention;
    try
      {
        newConvention = conventionRepository.save(convention);
        if(newConvention.getDemandeCession().getStatut().getCode().equals("NON_RISQUEE")){
          saveDocumentConvention(newConvention);
          Statut statut = statutService.findByCode("CONVENTION_GENEREE");
          newConvention.getDemandeCession().setStatut(statut);
          demandeCessionService.save(newConvention.getDemandeCession());
        }else {
          if(newConvention.getDemandeCession().getStatut().getCode().equals("CONVENTION_SIGNEE_PAR_DG") ||
                  newConvention.getDemandeCession().getStatut().getCode().equals("CONVENTION_REJETEE_PAR_PME")||
                  newConvention.getDemandeCession().getStatut().getCode().equals("CONVENTION_REJETEE")){
            saveDocumentConvention(newConvention);
            Statut statut = statutService.findByCode("CONVENTION_CORRIGEE");
            newConvention.getDemandeCession().setStatut(statut);
            demandeCessionService.save(newConvention.getDemandeCession());
          }
        }
      } catch (Exception ex){
      log.error("Exception occured while adding convention. Error message : {}", ex.getMessage());
        throw new CustomException("Exception occured while adding new convention");
    }
    return newConvention;
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
  @Transactional(propagation = Propagation.REQUIRED)
  public Convention updateValeurDecote(Long idConvention, double newValue){
    log.info("ConventionService:updateValeurDecote request params {}", idConvention);

    Optional <Convention> optional = Optional.ofNullable(conventionRepository.findById(idConvention).orElse(null));
    optional.get().setValeurDecoteByDG(newValue);

    log.info("ValeurDecote by DG in convention before saving: {}", optional.get().getValeurDecoteByDG());
    Convention convention = conventionRepository.saveAndFlush(optional.get());
    log.info("ValeurDecote by DG  after saving : {}", convention.getValeurDecoteByDG());

    log.info("ConventionService:updateValeurDecote saved in Database with value Decote : {}", convention.getValeurDecote());

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
//      for (Documents doc:existingConvention.get().getDocuments()
//           ) {
//        documentService.delete(doc.getId());
//      }
      existingConvention.get().setDocuments(newConvention.getDocuments());

      conventionRepository.saveAndFlush(existingConvention.get());
      log.info("ConventionService:transmettreConvention update convention with id : {}",existingConvention.get().getIdConvention());
    }catch (Exception ex){
      log.error("Exception occured while updating convention with id : {}",id );
      throw new CustomException("Error occured while updating this convention");
    }
    return existingConvention.get();
  }
  

  public String convertDate(Date date){
    if(date.getDay() <10) {
      return "0" + date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
    }
    return date.getDay()+"-"+date.getMonth()+"-"+date.getYear();
  }
  public String convertDate(LocalDateTime date){
    if(date.getDayOfMonth() <10){
      return "0"+date.getDayOfMonth()+"-"+date.getMonthValue()+"-"+date.getYear()+" à "+date.getHour()+":"+date.getMinute();
    }
    return date.getDayOfMonth()+"-"+date.getMonthValue()+"-"+date.getYear()+" à "+date.getHour()+":"+date.getMinute();
  }

  public String getInfoQRcode(Observation observation){
    return  "Prénom: "+observation.getUtilisateur().getPrenom()+ "\n Nom: "+ observation.getUtilisateur().getNom()+
            "\n Email: "+observation.getUtilisateur().getEmail()+"\n"+"Singé le "+convertDate(observation.getDateObservation());
  }


  public void saveDocumentConvention(Convention convention) throws IOException {
    Map<String, Object> contextModel = new HashMap<>();
    contextModel.put("convention", convention);
    String dateStr= convertDate(new Date());
    contextModel.put("date", dateStr);
    String fileName = "convention_generer.pdf";
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
    MultipartFile file = new MockMultipartFile(fileName,fileName,"", inputStream);
    upload(convention.getIdConvention(), file, TypeDocument.CONVENTION);
  }

  @Override
  public void saveDocumentConventionSigner(Convention convention) throws IOException {
    Map<String, Object> contextModel = new HashMap<>();
    contextModel.put("convention", convention);
    String dateStr= convertDate(new Date());
    contextModel.put("date", dateStr);
    String fileName = "convention_generer.pdf";
    Observation obPME = observationRepository.findDistinctFirstByDemandeIdDemandeAndStatut_Code(convention.getDemandeCession().getIdDemande(), "CONVENTION_SIGNEE_PAR_PME");
    if(obPME!=null){
      String qrCodePME = "Prénom: "+convention.getDemandeCession().getPme().getPrenomRepresentant()+"\n"+"Nom: "+convention.getDemandeCession().getPme().getNomRepresentant()+
              "\n"+"Mail: "+convention.getDemandeCession().getPme().getEmail()+"\n"+"Singé le "+convertDate(obPME.getDateObservation());
      qrCodePME = Qrcode.generateQRCode(qrCodePME,path+"/pme.png");
      contextModel.put("qrCodePME", qrCodePME);
      fileName = "convention_signer_par_PME.pdf";
      Observation obDG = observationRepository.findDistinctFirstByDemandeIdDemandeAndStatut_Code(convention.getDemandeCession().getIdDemande(), "CONVENTION_SIGNEE_PAR_DG");
      if(obDG!=null){
        String qrCodeCDMP = getInfoQRcode(obDG);
        qrCodeCDMP = Qrcode.generateQRCode(qrCodeCDMP,path+"/cdmp.png");
        contextModel.put("qrCodeCDMP",  qrCodeCDMP);
        fileName = "convention_signer_par_DG.pdf";
        Observation obORD = observationRepository.findDistinctFirstByDemandeIdDemandeAndStatut_Code(convention.getDemandeCession().getIdDemande(), "CONVENTION_ACCEPTEE");
        if(obORD!=null){
          String qrCodeORD = getInfoQRcode(obORD);
          qrCodeORD = Qrcode.generateQRCode(qrCodeORD,path+"/ordonnaneur.png");
          contextModel.put("qrCodeORD", qrCodeORD);
          fileName = "convention_signer_par_ORDONNATEUR.pdf";
        }
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
    MultipartFile file = new MockMultipartFile(fileName,fileName,"", inputStream);
    upload(convention.getIdConvention(), file, TypeDocument.CONVENTION);
  }
}
