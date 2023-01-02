package sn.modelsis.cdmp.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.util.DtoConverter;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@Slf4j
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonEngagementResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeAll
    static void beforeAll() {
        log.info(" before all ");
    }


    @BeforeEach
    void beforeEach() {
        baseUrl = baseUrl + ":" + port + "/api/bonEngagement";

        log.info(" before each ");
        bonEngagementRepository.deleteAll();
        entity = BonEngagementDTOTestData.defaultEntity();
        dto = BonEngagementDTOTestData.defaultDTO();
    }

    @AfterEach
    void afterEach(){
        bonEngagementRepository.deleteAll();
    }




    /* **************************** test D'intégration *****************************/
    @Test
    @Rollback(value = false)
    void save_shouldSaveBonEngagement() {
        BonEngagement newBE = restTemplate.postForObject(baseUrl, entity, BonEngagement.class);
        Assertions.assertAll(
                ()-> assertThat(newBE).isNotNull()
        );

        assertThat(newBE.getIdBonEngagement()).isNotNull();
        assertThat(status().isOk());

    }


    @Test
    void shouldFetchAllBonEngagementsTest() {

        bonEngagement = bonEngagementService.save(entity);

        List bonEngagementList = restTemplate.getForObject(baseUrl, List.class);

        assertThat(bonEngagementList.size())
                .isEqualTo(1);
    }


    @Test
    void shouldFetchOneBonEngagemenntByIdTest() {
        bonEngagement = bonEngagementService.save(entity);

        BonEngagement existingBonEngagement = restTemplate
                .getForObject(baseUrl+"/"+bonEngagement.getIdBonEngagement(), BonEngagement.class);

        assertNotNull(existingBonEngagement);
        assertEquals(bonEngagement.getNomMarche(), existingBonEngagement.getNomMarche());
    }



    @Test
    void delete_shouldDeleteBonEngagementTest() {

        bonEngagement = bonEngagementService.save(entity);

        restTemplate.delete(baseUrl+"/"+bonEngagement.getIdBonEngagement());

        int count = bonEngagementRepository.findAll().size();

        assertEquals(0, count);
    }


    @Test
    void givenUpdatedBonEngagement_whenUpdatedBe_thenReturnUpdateBonEngagementObject() throws Exception{
        // given - precondition or setup
        bonEngagement = bonEngagementService.save(entity);

        BonEngagementDto updatedBEDto = BonEngagementDTOTestData.updatedDTO();

        BonEngagement updatedBonEngagment = DtoConverter.convertToEntity(updatedBEDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                put(baseUrl+"/"+"{id}", bonEngagement.getIdBonEngagement())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedBonEngagment)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nomMarche", is(updatedBonEngagment.getNomMarche())))
                .andExpect(jsonPath("$.naturePrestation", is(updatedBonEngagment.getNaturePrestation())))
                .andExpect(jsonPath("$.reference", is(updatedBonEngagment.getReference())));
    }



    /* **************************** End test D'intégration *****************************/











    @Test
    void whenFileUploaded_thenVerifyStatus() throws Exception {
        bonEngagement = bonEngagementService.save(entity);

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(
                multipart("/api/bonEngagement/{id}/upload",bonEngagement.getIdBonEngagement()).file(file))
                .andExpect(status().is4xxClientError());
    }

}
