package sn.modelsis.cdmp.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.core.util.Json;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.entities.Pme;

import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.PmeService;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PmeResourceTest extends BasicResourceTest{

    private static Pme entity;
    private static Pme pme;

    @Autowired
    private PmeService pmeService;
    @Autowired
    private PmeRepository pmeRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
        entity = PmeDTOTestData.defaultEntity();
    }


    @Test
    void findAll_shouldReturnPmes() throws Exception {
        pme = pmeService.savePme(entity);
        mockMvc.perform(
                get("/api/pme").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                //.andExpect(jsonPath("$.Body", hasSize(1)))
                .andExpect(jsonPath("$.[0].idPME").exists())
                .andExpect(jsonPath("$.[0].isactive", is(false)))
                .andExpect(jsonPath("$.[0].hasninea", is(false)))
                .andExpect(jsonPath("$.[0].nantissement").value(false))
                .andExpect(jsonPath("$.[0].ninea", is(pme.getNinea())))
                .andExpect(jsonPath("$.[0].prenomRepresentant").value(pme.getPrenomRepresentant()))
                .andExpect(jsonPath("$.[0].email").value(pme.getEmail()))
                .andExpect(jsonPath("$.[0].nomRepresentant").value(pme.getNomRepresentant()))
                .andExpect(jsonPath("$.[0].rccm").value(pme.getRccm()))
                .andExpect( jsonPath("$.[0].telephonePME").value(pme.getTelephonePME()))
                .andExpect(jsonPath("$.[0].codePin").value(pme.getCodePin()));
    }



    @Test
    void findById_shouldReturnPme() throws Exception {
        pme = pmeService.savePme(entity);
        mockMvc.perform(get("/api/pme/{id}", pme.getIdPME())
                 .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPME").exists())
                .andExpect(jsonPath("$.idPME").value(pme.getIdPME()))
                .andExpect(jsonPath("$.isactive", is(false)))
                .andExpect(jsonPath("$.hasninea", is(false)))
                .andExpect(jsonPath("$.nantissement").value(false))
                .andExpect(jsonPath("$.ninea", is(pme.getNinea())))
                .andExpect(jsonPath("$.prenomRepresentant").value(pme.getPrenomRepresentant()))
                .andExpect(jsonPath("$.email").value(pme.getEmail()))
                .andExpect(jsonPath("$.nomRepresentant").value(pme.getNomRepresentant()))
                .andExpect(jsonPath("$.rccm").value(pme.getRccm()))
                .andExpect( jsonPath("$.telephonePME").value(pme.getTelephonePME()))
                .andExpect(jsonPath("$.codePin").value(pme.getCodePin()));
    }


    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/pme/{id}", UUID.randomUUID().getMostSignificantBits())
                 .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    @Transactional
    void add_shouldCreatePme() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/pme")
                        .content(asJsonString(entity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idPME").exists())
                .andExpect(jsonPath("$.isactive", is(false)))
                .andExpect(jsonPath("$.hasninea", is(false)))
                .andExpect(jsonPath("$.nantissement").value(false))
                .andExpect(jsonPath("$.ninea", is(entity.getNinea())))
                .andExpect(jsonPath("$.prenomRepresentant").value(entity.getPrenomRepresentant()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.nomRepresentant").value(entity.getNomRepresentant()))
                .andExpect(jsonPath("$.rccm").value(entity.getRccm()))
                .andExpect( jsonPath("$.telephonePME").value(entity.getTelephonePME()))
                .andExpect(jsonPath("$.codePin").value(entity.getCodePin()));
    }


    @Test
    void update_shouldUpdatePme() throws Exception {
        entity.setAdressePME(TestData.Update.adressePME);
        entity.setTelephonePME(TestData.Update.telephonePME);
        entity.setEmail(TestData.Update.email);
        entity.setCodePin(TestData.Update.codepin);
        pme = pmeService.savePme(entity);
        mockMvc.perform( MockMvcRequestBuilders
                .put("/api/pme/{id}", pme.getIdPME())
                .content(asJsonString(entity))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idPME").exists())
                .andExpect(jsonPath("$.isactive", is(false)))
                .andExpect(jsonPath("$.hasninea", is(false)))
                .andExpect(jsonPath("$.nantissement").value(false))
                .andExpect(jsonPath("$.ninea", is(entity.getNinea())))
                .andExpect(jsonPath("$.prenomRepresentant").value(entity.getPrenomRepresentant()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.nomRepresentant").value(entity.getNomRepresentant()))
                .andExpect(jsonPath("$.rccm").value(entity.getRccm()))
                .andExpect( jsonPath("$.telephonePME").value(entity.getTelephonePME()))
                .andExpect(jsonPath("$.codePin").value(entity.getCodePin()));
    }

    @Test
    void delete_shouldDeletePme() throws Exception {
        pme = pmeService.savePme(entity);
        mockMvc.perform(
                delete("/api/pme/{id}", pme.getIdPME())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }



    @Test
    public void delete_withBadId_shouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/pme/{id}", UUID.randomUUID().getMostSignificantBits())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(result -> assertFalse(result.getResolvedException() instanceof CustomException));
    }




}
