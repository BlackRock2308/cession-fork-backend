package sn.modelsis.cdmp.services.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.CreationComptePmeDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.entitiesDtos.email.EmailMessageWithTemplate;
import sn.modelsis.cdmp.exceptions.NotFoundException;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.services.UtilisateurService;
import sn.modelsis.cdmp.util.Constants;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.RestTemplateUtil;
import sn.modelsis.cdmp.util.Util;

import java.util.List;
import java.util.Set;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    final private UtilisateurRepository utilisateurRepository ;

    final private PmeService pmeService;

    final private RestTemplateUtil restTemplateUtil ;

    final   private Util util;

    final  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Logger log = LoggerFactory.getLogger(Utilisateur.class);

    final private RoleRepository roleRepository ;

    final private String sendMail=Constants.SEND_MAIL_WITH_TEMPLATE ;

    @Value("${server.notification}")
    private String HOST_NOTIFICATION ;
    @Value("${server.email_cdmp}")
    private String EMAIL_CDMP ;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, PmeService pmeService, RestTemplateUtil restTemplateUtil, Util util, RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.pmeService = pmeService;
        this.restTemplateUtil = restTemplateUtil;
        this.util = util;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur findByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        return utilisateur;
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        if ( utilisateur.getRoles()!=null){
             Set<Role> roles = utilisateur.getRoles();
             Set<Role> newRoles = utilisateur.getRoles();
             roles.forEach(role -> newRoles.add(roleRepository.save(role)));
             utilisateur.setRoles(newRoles);
        }
        if(utilisateur.getPassword()!=null)
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void delete(Long utilisateurId) {
     utilisateurRepository.deleteById(utilisateurId);
    }

    @Override
    public PmeDto createComptePme(CreationComptePmeDto creationComptePmeDto) throws Exception {
        String email = creationComptePmeDto.getEmail();
        if (email == null) {
            throw new Exception("The email can not be null");
        }
        log.debug("REST request to save Create account for pme  : {}", creationComptePmeDto.getEmail());
        Pme pme = pmeService.findPmeByEmail(email);
        Utilisateur utilisateur = new Utilisateur();
        String password = util.generateRandomPassword(8);
        int codePin = (int) (Math.random()*(9999-1003)+1002);
        utilisateur.setUpdatePassword(true);
        utilisateur.setUpdateCodePin(true);
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateur.setEmail(email);
        utilisateur.setCodePin(Integer.toString(codePin));
        creationComptePmeDto.getEmailMessageWithTemplate().getTemplateVariable().put("password",password);
        creationComptePmeDto.getEmailMessageWithTemplate().getTemplateVariable().put("codePin",codePin);
        creationComptePmeDto.getEmailMessageWithTemplate().getTemplateVariable().put("username",email);
        pme.setUtilisateur(utilisateurRepository.save(utilisateur));
        restTemplateUtil.sendEmailWithTemplate(HOST_NOTIFICATION+sendMail , creationComptePmeDto.getEmailMessageWithTemplate());
        return DtoConverter.convertToDto(pme) ;
    }

    @Override
    public EmailMessageWithTemplate forgetPassword(String email){
        EmailMessageWithTemplate emailMessageWithTemplate = new EmailMessageWithTemplate();
        if (email==null)
            throw new RuntimeException("email can not be null ");
        emailMessageWithTemplate.setTemplateName("cdmp-forget-password");
        emailMessageWithTemplate.setObjet("Mot de pass Oublier");
        emailMessageWithTemplate.setExpediteur(EMAIL_CDMP);
        emailMessageWithTemplate.setDestinataire(email);
        String password = util.generateRandomPassword(8);
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        emailMessageWithTemplate.getTemplateVariable().put("username",utilisateur.getEmail());
        emailMessageWithTemplate.getTemplateVariable().put("nouveauPassword",password);
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateur.setUpdatePassword(true);
        utilisateurRepository.save(utilisateur);
        EmailMessageWithTemplate response = restTemplateUtil.sendEmailWithTemplate(HOST_NOTIFICATION+sendMail , emailMessageWithTemplate);
        return response ;
    }

    @Override
    public boolean signerConvention(Long idUtilisateur,String codePin){
        final String[] codePinValide = new String[1];
        final String[] codePinEntree = new String[1];
        utilisateurRepository.findById(idUtilisateur).ifPresentOrElse(
                (value)->{
                    codePinValide[0] =value.getCodePin();
                    codePinEntree[0] =codePin;
        },
                ()->{
                    throw new NotFoundException("Cette utilisateur n'existe pas");
                }
        );

        return codePinEntree[0].equals(codePinValide[0]);

    }

}
