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
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.entitiesDtos.email.EmailMessageWithTemplate;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.NotFoundException;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.services.UtilisateurService;
import sn.modelsis.cdmp.util.Constants;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.RestTemplateUtil;
import sn.modelsis.cdmp.util.Util;

import java.util.HashSet;
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

    @Value("${server.link_front}")
    private String frontLink ;

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
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    @Override
    public Utilisateur addUser(Utilisateur utilisateur) {
        String password =generatePassword();
        utilisateur.setUpdatePassword(true);
        utilisateur.setUpdateCodePin(true);
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateur.setCodePin(Integer.toString(generateCodePin()));
        Utilisateur user = utilisateurRepository.findUtilisateurByEmail(utilisateur.getEmail());
        if(user == null) {
            Utilisateur utilisateurSaved = utilisateurRepository.save(utilisateur);
            if (utilisateurSaved == null)
                throw new CustomException("Error while saving the user ");
            sendAccepetAdhesionEmail(utilisateurSaved.getEmail(), password, Integer.parseInt(utilisateurSaved.getCodePin()));
        return utilisateurSaved;
        }
        return null;
    }

    @Override
    public Utilisateur updateUser(UtilisateurDto utilisateurDto) {
        Utilisateur user = utilisateurRepository.findById(utilisateurDto.getIdUtilisateur()).orElse(null);
        user.setPrenom(utilisateurDto.getPrenom());
        user.setNom(utilisateurDto.getNom());
        user.setTelephone(utilisateurDto.getTelephone());
        user.setAdresse(utilisateurDto.getAdresse());
        user.setMinistere(DtoConverter.convertToEntity(utilisateurDto.getMinister()));
        user.setRoles(utilisateurDto.getRoles());
        if(utilisateurDto.getEmail().equals(user.getEmail()) == false){
            Utilisateur userEx = utilisateurRepository.findUtilisateurByEmail(utilisateurDto.getEmail());
            if(userEx == null) {
                user.setEmail(utilisateurDto.getEmail());
                user.setUpdatePassword(true);
                user.setUpdateCodePin(true);
                String password =generatePassword();
                user.setPassword(passwordEncoder.encode(password));
                user.setCodePin(Integer.toString(generateCodePin()));
                Utilisateur utilisateurSaved = utilisateurRepository.save(user);
                if (utilisateurSaved == null)
                    throw new CustomException("Error while saving the user ");
                sendAccepetAdhesionEmail(utilisateurSaved.getEmail(), password, Integer.parseInt(utilisateurSaved.getCodePin()));
                return utilisateurSaved;
            }
            return null;
        }else {
            return utilisateurRepository.save(user);
        }
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Boolean active_desactive(Long id) {
        Utilisateur user = utilisateurRepository.findById(id).orElse(null);
        if(user != null){
            user.setActive(!user.isActive());
            utilisateurRepository.save(user);
            return true;
        }
    return false;
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        Utilisateur utilisateurToUpdate = utilisateurRepository.findById(utilisateur.getIdUtilisateur()).orElse(null);
        utilisateurToUpdate.setCodePin(utilisateur.getCodePin());
        utilisateurToUpdate.setUpdateCodePin(false);
        return utilisateurRepository.save(utilisateurToUpdate);
    }

    @Override
    public Utilisateur updateCodePin(Utilisateur utilisateur) {
        Utilisateur utilisateurToUpdate = utilisateurRepository.findById(utilisateur.getIdUtilisateur()).orElse(null);
        utilisateurToUpdate.setCodePin(utilisateur.getCodePin());
        utilisateurToUpdate.setUpdateCodePin(false);
        return utilisateurRepository.save(utilisateurToUpdate);
    }


    @Override
    public Utilisateur updatePassword(Utilisateur utilisateur) {
        Utilisateur utilisateurToUpdate = utilisateurRepository.findById(utilisateur.getIdUtilisateur()).orElse(null);
        utilisateurToUpdate.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurToUpdate.setUpdatePassword(false);
        return utilisateurRepository.save(utilisateurToUpdate);
    }

    @Override
    public Utilisateur updateRoles(Utilisateur utilisateur) {

        Utilisateur utilisateurToUpdate = utilisateurRepository.findById(utilisateur.getIdUtilisateur()).orElse(null);

        Set<Role> newRoles = new HashSet<>();
        if ( utilisateur.getRoles()!=null){
             Set<Role> roles = utilisateur.getRoles();
             roles.forEach(role -> newRoles.add(roleRepository.findByLibelle(role.getLibelle())));
            utilisateurToUpdate.setRoles(newRoles);
        }
        return utilisateurRepository.save(utilisateurToUpdate);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
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
        String password = generatePassword();
        int codePin = generateCodePin();
        utilisateur.setUpdatePassword(true);
        utilisateur.setUpdateCodePin(true);
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateur.setEmail(email);
        utilisateur.setCodePin(Integer.toString(codePin));
        utilisateur = setRole(utilisateur);
        utilisateur.setNom(pme == null ? null : pme.getNomRepresentant());
        utilisateur.setPrenom(pme == null ? null: pme.getPrenomRepresentant() );
        Utilisateur user1 = utilisateurRepository.findUtilisateurByEmail(email);
        if(user1 == null) {
            
            Utilisateur utilisateurSaved = utilisateurRepository.save(utilisateur);
            if (utilisateurSaved == null)
                throw new CustomException("Error while saving the user ");
            pme.setUtilisateur(utilisateurSaved);
            sendAccepetAdhesionEmail(email, password, codePin);
            pmeService.savePme(pme);
        }else {
            Utilisateur user2 = utilisateurRepository.findUtilisateurByEmail(email);
            user2.setUpdateCodePin(true);
            user2.setUpdatePassword(true);
            user2.setPassword(passwordEncoder.encode(password));
            user2.setCodePin(Integer.toString(codePin));
            Utilisateur utilisateurSaved = utilisateurRepository.save(user2);
            if (utilisateurSaved == null)
                throw new CustomException("Error while saving the user ");
            pme.setUtilisateur(utilisateurSaved);
            sendAccepetAdhesionEmail(email, password, codePin);
            pmeService.savePme(pme);
        }
        

        return DtoConverter.convertToDto(pme);
    }

    @Override
    public EmailMessageWithTemplate forgetPassword(String email){
        EmailMessageWithTemplate emailMessageWithTemplate = new EmailMessageWithTemplate();
        if (email==null)
            throw new RuntimeException("email can not be null ");
        emailMessageWithTemplate.setTemplateName("cdmp-forget-password");
        emailMessageWithTemplate.setObjet("Mot de pass Oublier");
        emailMessageWithTemplate.setExpediteur(EMAIL_CDMP);
        emailMessageWithTemplate.setFrontLink(frontLink);
        emailMessageWithTemplate.setDestinataire(email);
        String password = util.generateRandomPassword(8);
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        emailMessageWithTemplate.getTemplateVariable().put("username",utilisateur.getEmail());
        emailMessageWithTemplate.getTemplateVariable().put("front",frontLink);
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

    private EmailMessageWithTemplate  sendAccepetAdhesionEmail(String email  ,String password ,int codePin){


       EmailMessageWithTemplate emailMessageWithTemplate = new EmailMessageWithTemplate();
        emailMessageWithTemplate.getTemplateVariable().put("password",password);
        emailMessageWithTemplate.setTemplateName("cdmp-create-account");
        emailMessageWithTemplate.setObjet("Creation Compte CDMP ");
        emailMessageWithTemplate.setExpediteur(EMAIL_CDMP);
        emailMessageWithTemplate.setFrontLink(frontLink);
        emailMessageWithTemplate.setDestinataire(email);
        emailMessageWithTemplate.getTemplateVariable().put("codePin",codePin);
        emailMessageWithTemplate.getTemplateVariable().put("front",frontLink);
        emailMessageWithTemplate.getTemplateVariable().put("username",email);
       EmailMessageWithTemplate emailMessageWithTemplateSent =  restTemplateUtil.sendEmailWithTemplate(HOST_NOTIFICATION+sendMail , emailMessageWithTemplate);

        return emailMessageWithTemplateSent;
    }

    private String  generatePassword(){
        String password = util.generateRandomPassword(8);
        return password;
    }
    private int  generateCodePin(){
        int codePin = (int) (Math.random()*(9999-1003)+1002);
        return  codePin;
    }

    private Utilisateur  setRole(Utilisateur utilisateur){
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByLibelle("PME");
        roles.add(role);
        utilisateur.setRoles(roles);
        return  utilisateur;
    }
}
