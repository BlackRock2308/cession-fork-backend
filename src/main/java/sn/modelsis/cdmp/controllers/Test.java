package sn.modelsis.cdmp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.security.dto.EmailMessageWithTemplate;
import sn.modelsis.cdmp.util.RestTemplateUtil;

@RestController
@RequestMapping("/api")
public class Test {

    @Value("${server.notification}")
    private String HOST_NOTIFICATION ;

    final private String baseUrl ="/api/notification/v1/messages";
    @Autowired
    private RestTemplateUtil restTemplateUtil ;

    @PostMapping
    public EmailMessageWithTemplate tester(@RequestBody EmailMessageWithTemplate emailMessageWithTemplate){
       var response = restTemplateUtil.post(HOST_NOTIFICATION+baseUrl+"/email/with-template" , emailMessageWithTemplate);
        return response;

    }
}

