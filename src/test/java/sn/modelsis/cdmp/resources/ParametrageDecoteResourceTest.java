package sn.modelsis.cdmp.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.ParametrageDecoteDTOTest;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.mappers.DecodeMapper;
import sn.modelsis.cdmp.repositories.ParametrageDecoteRepository;
import sn.modelsis.cdmp.util.DtoConverter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Testcontainers
@Slf4j
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParametrageDecoteResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

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
        restTemplate = new RestTemplate();
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        baseUrl = baseUrl + ":" + port + "/api/decote";
        decoteRepository.deleteAll();
        entity = ParametrageDecoteDTOTest.defaultEntity();
    }


//    @Test
//    @Rollback(value = false)
//    void add_shouldCreateParametrageDecoteTest() {
//        //decote = ParametrageDecoteDTOTest.defaultEntity();
//        decote = new ParametrageDecote();
//        decote.setIdDecote(TestData.Default.id);
//        decote.setBorneInf(2_000_000L);
//        decote.setBorneSup(10_000_000L);
//        decote.setDecoteValue(0.5);
//
//        ParametrageDecote newDecote = restTemplate.postForObject(baseUrl, decote, ParametrageDecote.class);
//        Assertions.assertAll(
//                ()->  assertThat(status().isOk()),
//                ()-> assertThat(newDecote.getIdDecote()).isNotNull()
//        );
//    }


    @Test
    void findAll_shouldReturnDeoctesTest() {

        decote = decoteRepository.save(entity);

        List decoteList = restTemplate.getForObject(baseUrl, List.class);

        assertThat(decoteList.size()).isEqualTo(1);
    }


    @Test
    void update_shouldReturn_change_ParametrageDecoteTest() throws Exception{
        // given - precondition or setup
        decote = decoteRepository.save(entity);

        ParametrageDecoteDTO updatedDecoteDto = ParametrageDecoteDTOTest.updateDTO();

        ParametrageDecote updatedDecote = decodeMapper.asEntity(updatedDecoteDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                put(baseUrl+"/"+"{id}", decote.getIdDecote())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedDecoteDto)));

        // then - verify the output
//        response
//                .andDo(print())
//                .andExpect(jsonPath("$.borneInf", is(updatedDecoteDto.getBorneInf())))
//                .andExpect(jsonPath("$.borneSup", is(updatedDecoteDto.getBorneSup())));
    }


}
