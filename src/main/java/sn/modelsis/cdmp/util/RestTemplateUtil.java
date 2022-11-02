package sn.modelsis.cdmp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sn.modelsis.cdmp.security.dto.EmailMessageWithTemplate;

@Slf4j
@Service
public class RestTemplateUtil {

    public EmailMessageWithTemplate post(String url, EmailMessageWithTemplate data){

        EmailMessageWithTemplate emailMessageWithTemplate = new EmailMessageWithTemplate();
        try{
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<EmailMessageWithTemplate> request = new HttpEntity<>(data);
        emailMessageWithTemplate = restTemplate.postForObject(url, request, EmailMessageWithTemplate.class);
        log.info("POST request for sending email  :  {} with notifications microservice" , url );
        }catch (Exception exception){
        exception.printStackTrace();
            log.error("Erreur lors de l'appel du microservice de notification", exception.getMessage());
        }

        return emailMessageWithTemplate;
    }


}
