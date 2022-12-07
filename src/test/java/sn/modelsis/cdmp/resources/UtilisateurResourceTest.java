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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.UtilisateurDTOTestData;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.services.UtilisateurService;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UtilisateurResourceTest extends BasicResourceTest{

    private static Utilisateur entity;

    private static Utilisateur utilisateur;

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

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
        entity = UtilisateurDTOTestData.defaultEntity();
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
                        get("/api/utilisateur/{email}", utilisateur.getEmail()).accept(MediaType.APPLICATION_JSON))
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
}
