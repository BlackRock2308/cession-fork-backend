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
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.services.BonEngagementService;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BonEngagementResourceTest extends BasicResourceTest{

    private static BonEngagementDto vm;

    private static BonEngagementDto dto;

    private static BonEngagement entity;

    private static BonEngagement bonEngagement;

    @Autowired
    private BonEngagementService bonEngagementService;

    @Autowired
    private BonEngagementRepository bonEngagementRepository;


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
        bonEngagementRepository.deleteAll();
        entity = BonEngagementDTOTestData.defaultEntity();
        dto = BonEngagementDTOTestData.defaultDTO();
    }

    @Test
    void findAll_shouldReturnBonEngagement() throws Exception {
        bonEngagement = bonEngagementService.save(entity);
        mockMvc.perform(
                        get("/api/bonEngagement").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(jsonPath("$.[0].natureDepense", is(bonEngagement.getNatureDepense())))
                .andExpect(jsonPath("$.[0].nomMarche", is(bonEngagement.getNomMarche())))
                .andExpect(jsonPath("$.[0].reference").value(bonEngagement.getReference()));
    }


    @Test
    void findById_shouldReturnBonEngagement() throws Exception {
        bonEngagement = bonEngagementService.save(entity);
        mockMvc.perform(get("/api/bonEngagement/{id}", bonEngagement.getIdBonEngagement())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBonEngagement").exists())
                .andExpect(jsonPath("$.idBonEngagement").value(bonEngagement.getIdBonEngagement()))
                .andExpect(jsonPath("$.naturePrestation", is(bonEngagement.getNaturePrestation())))
                .andExpect(jsonPath("$.nomMarche", is(bonEngagement.getNomMarche())))
                .andExpect(jsonPath("$.reference").value(bonEngagement.getReference()));
    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/bonEngagement/{id}", UUID.randomUUID().getMostSignificantBits())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

//    @Test
//    @Transactional
//    void add_shouldCreateBonEngagement() throws Exception
//    {
//        mockMvc.perform( MockMvcRequestBuilders
//                        .post("/api/bonEngagement/")
//                        .content(asJsonString(dto))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.idBonEngagement").exists())
//                .andExpect(jsonPath("$.idBonEngagement").value(bonEngagement.getIdBonEngagement()))
//                .andExpect(jsonPath("$.naturePrestation", is(bonEngagement.getNaturePrestation())))
//                .andExpect(jsonPath("$.nomMarche", is(bonEngagement.getNomMarche())))
//                .andExpect(jsonPath("$.reference").value(bonEngagement.getReference()));
//    }

}
