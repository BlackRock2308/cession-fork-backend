package sn.modelsis.cdmp.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.modelsis.cdmp.controllers.UtilisateurController;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.UtilisateurDTOTestData;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.security.configuration.SecurityConfig;
import sn.modelsis.cdmp.security.dto.AuthentificationDto;
import sn.modelsis.cdmp.services.UtilisateurService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;
import java.net.SecureCacheResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Testcontainers
@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UtilisateurResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

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
    @Autowired @Valid
    PmeRepository pmeRepository;
    @Autowired @Valid
    DemandeCessionRepository cessionRepository;
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
    public static void init() {
        restTemplate = new RestTemplate();
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        baseUrl = baseUrl + ":" + port + "/api/utilisateur";

        pmeRepository.deleteAll();
        cessionRepository.deleteAll();
        utilisateurRepository.deleteAll();

        entity = UtilisateurDTOTestData.defaultEntity();
        entityRegistered = UtilisateurDTOTestData.registeredEntity();
    }



    @AfterEach
    void afterEach(){
//        cessionRepository.deleteAll();
//        pmeRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    @Test
    void findAll_shouldReturnUsers() throws Exception  {

        utilisateur = utilisateurService.save(entity);

        List userList = restTemplate.getForObject(baseUrl+"/getAll", List.class);

        assertThat(userList.size()).isEqualTo(1);
    }


    @Test
    @WithMockUser(roles="ADMIN")
    void findByEmail_shouldReturnUtilisateurTest()  {

        utilisateur = utilisateurService.save(entity);

        Utilisateur existingUser = restTemplate
                .getForObject(baseUrl+"/"+entity.getEmail(), Utilisateur.class);

        assertNotNull(existingUser);
        assertEquals(utilisateur.getEmail(), existingUser.getEmail());
    }


    @Test
    void findById_withBadEmail_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/utilisateur/{email}", UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @Rollback(value = false)
    void save_shouldSaveUtilisateur() {
        role = new Role(1L,"ADMIN", "Administrateur");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        entityRegistered = UtilisateurDTOTestData.registeredEntity();
        entityRegistered.setRoles(roles);
        entityDtoRegistered = DtoConverter.convertToDto(entityRegistered);

        Utilisateur newUser = restTemplate.postForObject(baseUrl+"/create", entityDtoRegistered, Utilisateur.class);
        Assertions.assertAll(
                ()->  assertThat(status().isOk()),
                ()->  assertThat(newUser.getIdUtilisateur()).isNotNull(),
                ()-> assertThat(newUser).isNotNull()
        );
    }



    @Test
    void signConvention_shouldReturnResult() throws Exception {
        entityRegistered = UtilisateurDTOTestData.registeredEntity();
        entityDtoRegistered = DtoConverter.convertToDto(entityRegistered);
        utilisateur = utilisateurService.save(entityRegistered);
        mockMvc.perform(
                        post("/api/utilisateur/{idUtilisateur}/signer-convention", utilisateur.getIdUtilisateur())
                                .content(asJsonString(utilisateur.getCodePin()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void delete_shouldDeleteUtilisateurTest() {

        utilisateur = utilisateurService.save(entity);

        restTemplate.delete(baseUrl+"/"+utilisateur.getIdUtilisateur());

        int count = utilisateurRepository.findAll().size();

        assertEquals(0, count);
    }



//    @Test
//    void auth_shouldLogUtilisateur() throws Exception {
//        utilisateur = utilisateurService.save(entity);
//        auth = UtilisateurDTOTestData.authentificationDto();
//        mockMvc.perform(
//                        post("/api/utilisateur/auth")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(auth)))
//                .andExpect(status().isCreated());
//
//    }

//    @Test
//    void forgetPassword_shouldReturnResult() throws Exception {
//        entityRegistered = UtilisateurDTOTestData.registeredEntity();
//        entityDtoRegistered = DtoConverter.convertToDto(entityRegistered);
//        utilisateur = utilisateurService.save(entityRegistered);
//         mockMvc.perform(
//                post("/api/utilisateur/forget-password")
//                        .content(asJsonString(utilisateur.getEmail()))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }


}
