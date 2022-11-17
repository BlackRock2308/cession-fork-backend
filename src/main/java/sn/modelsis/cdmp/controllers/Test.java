package sn.modelsis.cdmp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.modelsis.cdmp.entitiesDtos.email.EmailMessageDetailWithoutTemplate;
import sn.modelsis.cdmp.util.Constants;
import sn.modelsis.cdmp.util.RestTemplateUtil;

@RestController
public class Test {

  private final RestTemplateUtil restTemplateUtil;

  private final String sendMail = Constants.SEND_MAIL_WItHOUT_TEMPLATE;

  @Value("${server.notification}")
  private String HOST_NOTIFICATION;

  @Value("${server.email_cdmp}")
  private String EMAIL_CDMP;

  public Test(RestTemplateUtil restTemplateUtil) {
    this.restTemplateUtil = restTemplateUtil;
  }

  @GetMapping("api/test")
  public EmailMessageDetailWithoutTemplate emailMessageDetailWithoutTemplate(){
    EmailMessageDetailWithoutTemplate emailMessageDetailWithoutTemplate = new EmailMessageDetailWithoutTemplate();
    emailMessageDetailWithoutTemplate.setTypeMessage("Hello word");
    emailMessageDetailWithoutTemplate.setDestinataire("andiaye@modelsis.sn");
    emailMessageDetailWithoutTemplate.setObjetDeLEmail("Tets");
    emailMessageDetailWithoutTemplate.setContent("Content Content");
    emailMessageDetailWithoutTemplate.setExpediteur(EMAIL_CDMP);
    emailMessageDetailWithoutTemplate.setCodeApp("CDMP");
    emailMessageDetailWithoutTemplate.setTypeMessage("email");
    return restTemplateUtil.sendEmailWithoutTemplate(HOST_NOTIFICATION+sendMail,emailMessageDetailWithoutTemplate);
  }
}
