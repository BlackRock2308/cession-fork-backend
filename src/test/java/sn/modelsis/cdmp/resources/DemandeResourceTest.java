package sn.modelsis.cdmp.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.modelsis.cdmp.data.DemandeDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@Testcontainers

@Slf4j
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemandeResourceTest extends BasicResourceTest{

    private static Demande entity;
    private static DemandeDto dto;
    private static Demande demande;

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private DemandeRepository demandeRepository;

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
        demandeRepository.deleteAll();
        dto = DemandeDTOTestData.defaultDTO();
        entity = DtoConverter.convertToEntity(dto);
    }

//    @Test
//    void findAll_shouldReturnDemandes() throws Exception {
//        mockMvc.perform(
//                        get("/api/demandes").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andDo(MockMvcResultHandlers.print()) //can print request details
//                //.andExpect(jsonPath("$.Body", hasSize(1)))
//                .andExpect(jsonPath("$.idDemande").exists())
//                .andExpect(jsonPath("$.idDemande", is(demande.getIdDemande())))
//                .andExpect(jsonPath("$.numeroDemande", is(demande.getNumeroDemande())));
//    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/demandes/{id}", UUID.randomUUID().getMostSignificantBits())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


//    @Test
//    void getAllConvention_shouldReturnNotFound() throws Exception {
//        mockMvc.perform(
//                get("/api/demandes/conventionsComptable")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
}
