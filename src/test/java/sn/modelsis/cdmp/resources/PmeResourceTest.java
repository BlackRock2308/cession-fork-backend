package sn.modelsis.cdmp.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.Pme;

import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;

//@Testcontainers

@Slf4j
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PmeResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    private static Pme entity;
    private static Pme pme;

    @Autowired
    private PmeService pmeService;
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
    public static void init() {
        restTemplate = new RestTemplate();
        log.info(" before all ");
    }


    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        baseUrl = baseUrl + ":" + port + "/api/pme";
        pmeRepository.deleteAll();
        entity = PmeDTOTestData.defaultEntity();
    }

    @AfterEach
    void afterEach(){
        pmeRepository.deleteAll();
    }


    @Test
    @Rollback(value = false)
    void save_shouldSavePme() {
        Pme newPme = restTemplate.postForObject(baseUrl, entity, Pme.class);
        Assertions.assertAll(
                ()-> assertThat(newPme.getIdPME()).isNotNull(),
                ()->  assertThat(status().isOk())
        );

    }


    @Test
    void shouldFetchAllPmesTest() {

        pme = pmeService.savePme(entity);

        List pmelist = restTemplate.getForObject(baseUrl, List.class);

        assertThat(pmelist.size()).isEqualTo(1);
    }


    @Test
    void findById_shouldReturnExistingPmeTest() {
        pme = pmeService.savePme(entity);

        Pme existingPme = restTemplate
                .getForObject(baseUrl+"/"+pme.getIdPME(), Pme.class);

        assertNotNull(existingPme);
        assertEquals(pme.getEmail(), existingPme.getEmail());
    }



    @Test
    void givenUpdatedPmeupdate_shouldUpdatePme() throws Exception{
        // given - precondition or setup
        pme = pmeService.savePme(entity);


        PmeDto updatedPmeDto = PmeDTOTestData.updatedDTO();

        Pme updatedpme = DtoConverter.convertToEntity(updatedPmeDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                put(baseUrl+"/"+"{id}", pme.getIdPME())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedpme)));

        // then - verify the output
        response.andExpect(status().isAccepted())
                .andDo(print())
                .andExpect(jsonPath("$.rccm", is(updatedpme.getRccm())))
                .andExpect(jsonPath("$.ninea", is(updatedpme.getNinea())));
    }



    @Test
    void delete_shouldDeletePme() {

        pme = pmeService.savePme(entity);

        restTemplate.delete(baseUrl+"/"+pme.getIdPME());

        int count = pmeRepository.findAll().size();

        assertEquals(0, count);
    }



    @Test
    public void find_withBadId_shouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/pme/{id}", UUID.randomUUID().getMostSignificantBits())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(result -> assertFalse(result.getResolvedException() instanceof CustomException));
    }







//    @Test
//    public void getAllPmeTest() {
//
//        UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
//                .build();
//        HttpEntity<String> requestEntity = new HttpEntity<>(null, null);
//        ResponseEntity<String> response = testRestTemplate.exchange(builder.toString(), HttpMethod.GET, requestEntity,
//                String.class);
//        System.out.println(response.getBody());
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    }

}
