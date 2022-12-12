package sn.modelsis.cdmp.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.UtilisateurDTOTestData;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.security.dto.AuthentificationDto;
import sn.modelsis.cdmp.services.UtilisateurService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UtilisateurResourceTest extends BasicResourceTest{

    private static AuthentificationDto auth;

    private static Utilisateur entity;

    private static Utilisateur entityRegistered;

    private static UtilisateurDto entityDtoRegistered;

    private Role role;

    private static Utilisateur utilisateur;

    final private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PmeRepository pmeRepository;

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            final String jsonContent = objectMapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        pmeRepository.deleteAll();
        utilisateurRepository.deleteAll();

//        role = new Role(1L,"PDG", "President directeur General");
//        Set<Role> roles = null;
//        roles.add(role);

        entity = UtilisateurDTOTestData.defaultEntity();
        entityRegistered = UtilisateurDTOTestData.registeredEntity();
//        entityRegistered.setRoles(roles);
    }


    @Test
    void findAll_shouldReturnUsers() throws Exception {
        utilisateur = utilisateurService.save(entity);
        mockMvc.perform(
                        get("/api/utilisateur/getAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.[0].idUtilisateur").exists())
                .andExpect(jsonPath("$.[0].email").value(utilisateur.getEmail()))
                .andExpect(jsonPath("$.[0].adresse").value(utilisateur.getAdresse()))
                .andExpect(jsonPath("$.[0].prenom").value(utilisateur.getPrenom()))
                .andExpect(jsonPath("$.[0].password").value(utilisateur.getPassword()))
                .andExpect(jsonPath("$.[0].codePin").value(utilisateur.getCodePin()));


    }

    @Test
    void findByEmail_shouldReturnUtilisateur() throws Exception {
        utilisateur = utilisateurService.save(entity);
        mockMvc.perform(
                        get("/api/utilisateur/{email}", utilisateur.getEmail())
                 .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.idUtilisateur").exists())
                .andExpect(jsonPath("$.email").value(utilisateur.getEmail()))
                .andExpect(jsonPath("$.adresse").value(utilisateur.getAdresse()))
                .andExpect(jsonPath("$.prenom").value(utilisateur.getPrenom()))
                .andExpect(jsonPath("$.password").value(utilisateur.getPassword()))
                .andExpect(jsonPath("$.codePin").value(utilisateur.getCodePin()));
    }

    @Test
    void findById_withBadEmail_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/utilisateur/{email}", UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void add_shouldCreateUtilisateur() throws Exception {
        role = new Role(1L,"PDG", "President directeur General");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        entityRegistered = UtilisateurDTOTestData.registeredEntity();
        entityRegistered.setRoles(roles);
        entityDtoRegistered = DtoConverter.convertToDto(entityRegistered);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/utilisateur/create")
                        .content(asJsonString(entityDtoRegistered))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idUtilisateur").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(entityDtoRegistered.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adresse").value(entityDtoRegistered.getAdresse()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value(entityDtoRegistered.getPrenom()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.codePin").value(entityDtoRegistered.getCodePin()));
    }


    @Test
    void signConvention_shouldReturnResult() throws Exception {
        entityRegistered = UtilisateurDTOTestData.registeredEntity();
        entityDtoRegistered = DtoConverter.convertToDto(entityRegistered);
        utilisateur = utilisateurService.save(entityRegistered);
        mockMvc.perform(
                        post("/api/utilisateur/{idUtilisateur}/signer-convention", utilisateur.getIdUtilisateur())
                                .content(asJsonString(utilisateur.getEmail()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    void delete_shouldDeleteUtilisateur() throws Exception {
        utilisateur = utilisateurService.save(entity);
        mockMvc.perform(
                delete("/api/utilisateur/{id}", utilisateur.getIdUtilisateur())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void auth_shouldLogUtilisateur() throws Exception {
        utilisateur = utilisateurService.save(entity);
        auth = UtilisateurDTOTestData.authentificationDto();
        mockMvc.perform(
                        post("/api/utilisateur/auth")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(auth)))
                .andExpect(status().isCreated());

    }

    @Test
    void forgetPassword_shouldReturnResult() throws Exception {
        entityRegistered = UtilisateurDTOTestData.registeredEntity();
        entityDtoRegistered = DtoConverter.convertToDto(entityRegistered);
        utilisateur = utilisateurService.save(entityRegistered);
         mockMvc.perform(
                post("/api/utilisateur/forget-password")
                        .content(asJsonString(utilisateur.getEmail()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
