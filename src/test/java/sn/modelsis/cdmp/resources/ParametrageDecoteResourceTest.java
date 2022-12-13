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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.ParametrageDecoteDTOTest;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;
import sn.modelsis.cdmp.mappers.DecodeMapper;
import sn.modelsis.cdmp.repositories.ParametrageDecoteRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ParametrageDecoteResourceTest extends BasicResourceTest{

    private static ParametrageDecote entity;
    private static ParametrageDecote decote;

    @Autowired
    ParametrageDecoteRepository decoteRepository;

    @Autowired
    DecodeMapper decodeMapper;
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
        decoteRepository.deleteAll();
        entity = ParametrageDecoteDTOTest.defaultEntity();
    }

    @Test
    @Transactional
    void add_shouldCreateParametrageDecote() throws Exception
    {
        decote = ParametrageDecoteDTOTest.defaultEntity();

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/decote/")
                        .content(asJsonString(decote))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDecote").exists())
                .andExpect(jsonPath("$.borneSup", is(decote.getBorneSup())))
                .andExpect(jsonPath("$.borneInf", is(decote.getBorneInf())));
//                .andExpect(jsonPath("$.val").value(decote.getReference()));
    }

    @Test
    void findAll_shouldReturnDeoctes() throws Exception {
        decote = decoteRepository.save(entity);
        mockMvc.perform(
                        get("/api/decote").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(jsonPath("$.[0].idDecote").exists())
                .andExpect(jsonPath("$.[0].borneInf", is(decote.getBorneInf())))
                .andExpect(jsonPath("$.[0].borneSup", is(decote.getBorneSup())));
//                .andExpect(jsonPath("$.[0].decoteValue", is(decote.getDecoteValue())));
    }

    @Test
    void findAll_shouldReturn_listOf_ParametrageDecote() throws Exception {
        decote = decoteRepository.save(entity);
        mockMvc.perform(
                get("/api/decote")
                 .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(jsonPath("$.[0].idDecote").exists())
                .andExpect(jsonPath("$.[0].borneInf", is(decote.getBorneInf())))
                .andExpect(jsonPath("$.[0].borneSup", is(decote.getBorneSup())));
    }

    @Test
    void update_shouldReturn_change_ParametrageDecote() throws Exception {
        ParametrageDecoteDTO updatedDecoteDto = ParametrageDecoteDTOTest.updateDTO();
        ParametrageDecote updatedDecote = decodeMapper.asEntity(updatedDecoteDto);
        decote = decoteRepository.save(updatedDecote);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/decote/{id}", decote.getIdDecote())
                        .content(asJsonString(updatedDecote))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(jsonPath("$.idDecote").exists())
                .andExpect(jsonPath("$.borneInf", is(decote.getBorneInf())))
                .andExpect(jsonPath("$.borneSup", is(decote.getBorneSup())));
    }
}
