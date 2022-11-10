package sn.modelsis.cdmp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.CreationComptePmeDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.security.dto.AuthentificationDto;
import sn.modelsis.cdmp.security.dto.AuthentificationResponseDto;
import sn.modelsis.cdmp.security.dto.EmailMessageWithTemplate;
import sn.modelsis.cdmp.security.service.UtilisateurDetailService;
import sn.modelsis.cdmp.security.utils.JWTUtility;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.services.UtilisateurService;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.RestTemplateUtil;
import sn.modelsis.cdmp.util.Util;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**@
 * REST controller for managing {@link Utilisateur}.
 */
@RestController
@RequestMapping("/api/utilisateur")

public class UtilisateurController {

    private final Logger log = LoggerFactory.getLogger(Utilisateur.class);

    private static final String ENTITY_NAME = "Utilisateur";

    final private RoleRepository roleRepository ;

    final   private JWTUtility jwtUtility;

    final   private Util util;

    final private UtilisateurDetailService utilisateurDetailService;

    final private PmeService  pmeService;

    final private RestTemplateUtil restTemplateUtil ;

    final   private AuthenticationManager authenticationManager;

    final  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    final private UtilisateurService utilisateurService ;

    public UtilisateurController(RoleRepository roleRepository, JWTUtility jwtUtility, Util util, UtilisateurDetailService utilisateurDetailService, PmeService pmeService, RestTemplateUtil restTemplateUtil, AuthenticationManager authenticationManager, UtilisateurService utilisateurService) {
        this.roleRepository = roleRepository;
        this.jwtUtility = jwtUtility;
        this.util = util;
        this.utilisateurDetailService = utilisateurDetailService;
        this.pmeService = pmeService;
        this.restTemplateUtil = restTemplateUtil;
        this.authenticationManager = authenticationManager;
        this.utilisateurService = utilisateurService;
    }

    /**
     * {@code GET  /utilisateurs} : get all the utilisateurs.
     *
     *  .
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of utilisateurs in body.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateurs() {
        log.debug("REST request to get  Utilisateurs");
        List<Utilisateur> utilisateurs =  utilisateurService.getAll();
        ArrayList<UtilisateurDto> utilisateurDtos = new ArrayList<>();
        utilisateurs.forEach(utilisateur ->  utilisateurDtos.add(DtoConverter.convertToDto(utilisateur)));
        return ResponseEntity.ok().body(utilisateurDtos);
    }

    /**
     * {@code GET  /utilisateurs/:id} : get the "id" utilisateur.
     *
     * @param email the id of the utilisateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the utilisateurDTO, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/{email}")
    public ResponseEntity<UtilisateurDto> getUtilisateur(@PathVariable String email) {
        log.debug("REST request to get Utilisateur : {}", email);
        Utilisateur utilisateur = utilisateurService.findByEmail(email);
        return  ResponseEntity.ok().body(DtoConverter.convertToDto(utilisateur));
    }

    /**
     * {@code PUT  /utilisateurs/:id} : Updates an existing utilisateur.
     *
     * @param utilisateurDto the utilisateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated utilisateur,
     * or with status {@code 400 (Bad Request)} if the utilisateur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the utilisateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PatchMapping("/update")
    public ResponseEntity<UtilisateurDto> updateUtilisateur(
            @Valid @RequestBody UtilisateurDto utilisateurDto
    ) throws Exception {
        log.debug("REST request to update Utilisateur : {}", utilisateurDto);
        if (utilisateurDto.getIdUtilisateur() == null)
            throw new Exception("Invalid id  " + ENTITY_NAME + "  idnull");
        Utilisateur result = utilisateurService.update(DtoConverter.convertToEntity(utilisateurDto));
            return ResponseEntity
                    .ok()
                    .body(DtoConverter.convertToDto(result));
    }
    /**
     * {@code POST  /utilisateurs} : Create a new utilisateur.
     *
     * @param utilisateurDto the utilisateur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new utilisateur, or with status {@code 400 (Bad Request)} if the utilisateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/create")
    public ResponseEntity<UtilisateurDto> createUtilisateur(@Valid @RequestBody UtilisateurDto utilisateurDto) throws Exception {
        log.debug("REST request to save Utilisateur : {}", utilisateurDto);
        if (utilisateurDto.getIdUtilisateur() != null) {
            throw new Exception("A new utilisateur cannot already have an ID  exists");
        }
        Set<Role> listeRole = utilisateurDto.getRoles( ) ;

        listeRole.forEach(role -> {
            listeRole.add(roleRepository.save(role));
        });
        utilisateurDto.setUpdatePassword(true);
        utilisateurDto.setPassword(passwordEncoder.encode(utilisateurDto.getPassword()));
        Utilisateur result = utilisateurService.save(DtoConverter.convertToEntity(utilisateurDto));

        return ResponseEntity
                .created(new URI("/api/utilisateurs/" + result.getIdUtilisateur()))
                .body(DtoConverter.convertToDto(result));
    }

    /**
     * {@code POST  /utilisateurs} : Create a new utilisateur.
     *
     * @param *utilisateurDto the utilisateur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new utilisateur, or with status {@code 400 (Bad Request)} if the utilisateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/pme/createCompte")
    public ResponseEntity<PmeDto> createComptePme(@Valid @RequestBody CreationComptePmeDto creationComptePmeDto) throws Exception {

        return ResponseEntity.ok().body(utilisateurService.createComptePme(creationComptePmeDto)) ;
    }

    /**
     * {@code DELETE  /utilisateurs/:id} : delete the "id" utilisateur.
     *
     * @param id the id of the utilisateur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        log.debug("REST request to delete Utilisateur : {}", id);
        utilisateurService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * {@code POST  /auth} : authenticate a user
     *
     * @param authentificationDto the username and the password of a user.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @PostMapping("/auth")
    public AuthentificationResponseDto authenticate(@RequestBody AuthentificationDto authentificationDto) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDto.getEmail(), authentificationDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INNVALID_CREDENTIALS", e);
        }
        UserDetails userDetails = utilisateurDetailService.loadUserByUsername(authentificationDto.getEmail());
        Utilisateur utilisateur = utilisateurService.findByEmail(authentificationDto.getEmail());
        if (utilisateur != null) {
            final String token = jwtUtility.generateToken(utilisateur ,userDetails);
            return new AuthentificationResponseDto(utilisateur, token);
        } else {
            return null;
        }
    }

    /**
     * {@code POST  /forget-password} : forget password
     *
     * @param email the email of the user.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @PostMapping("/forget-password")
    public EmailMessageWithTemplate forgetPassword(@RequestBody String email)  {
        return utilisateurService.forgetPassword(email);
    }

    /**
     * {@code POST  /signer-convention} : signer convention
     *
     * @param  codePin of the user .
     * @param  idUtilisateur of the user .
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @PostMapping("/{idUtilisateur}/signer-convention")
    public  ResponseEntity<Boolean> signerConvention(@RequestBody String codePin,@PathVariable Long idUtilisateur)  {

        log.info("codePin:{}",codePin);
        return ResponseEntity.ok().body(utilisateurService.signerConvention(idUtilisateur,codePin));
    }


}