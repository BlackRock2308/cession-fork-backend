package sn.modelsis.cdmp.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
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
import sn.modelsis.cdmp.data.DemandeAdhesionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.repositories.DemandeAdhesionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemandeAdhesionResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;
    private static Pme entityPme;
    private static Pme pme;

    private static DemandeAdhesionDto dto;
    private static DemandeAdhesion demandeAdhesion;

    @Autowired
    private PmeService pmeService;
    @Autowired
    private PmeRepository pmeRepository;
    @Autowired
    private DemandeAdhesionRepository adhesionRepository;
    @Autowired
    private DemandeAdhesionService adhesionService;



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
        baseUrl = baseUrl + ":" + port + "/api/demandeadhesion";

        pmeRepository.deleteAll();
        adhesionRepository.deleteAll();
        entityPme = PmeDTOTestData.defaultEntity();

        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());

    }


    @AfterEach
    void afterEach(){
        pmeRepository.deleteAll();
        adhesionRepository.deleteAll();
    }



    @Test
    @Rollback(value = false)
    void add_shouldCreateDemandeAdhesion() throws Exception {
        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());

        DemandeAdhesion newAdhesion = restTemplate
                .postForObject(baseUrl, dto ,DemandeAdhesion.class);

        assertThat(newAdhesion)
                .isNotNull();
    }

    @Test
    void findAll_shouldReturnDemandeAdhesion() throws Exception {

        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());


        demandeAdhesion = adhesionService.saveAdhesion(dto);
        mockMvc.perform(
                        get("/api/demandeadhesion").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].idDemande").isNotEmpty());

    }


    @Test
    void findById_shouldReturnOneDemandeAdhesion() throws Exception {
        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());
        demandeAdhesion = adhesionService.saveAdhesion(dto);

        DemandeAdhesion existingdemande = restTemplate
                .getForObject(baseUrl+"/"+demandeAdhesion.getIdDemande(), DemandeAdhesion.class);

        assertNotNull(existingdemande);
        assertEquals(demandeAdhesion.getNumeroDemande(), existingdemande.getNumeroDemande());
    }



    @Test
    void rejectedDemandeAdhesion_shouldRejectDemande() throws Exception{
        // given - precondition or setup
        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());
        demandeAdhesion = adhesionService.saveAdhesion(dto);


        DemandeAdhesionDto rejectedAdhesionDto = DemandeAdhesionDTOTestData.updatedDTO();

        DemandeAdhesion rejectedAdhesion = DtoConverter.convertToEntity(rejectedAdhesionDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                patch(baseUrl+"/"+"{id}"+"/rejectadhesion", demandeAdhesion.getIdDemande())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rejectedAdhesion)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pme.telephonePME", is(demandeAdhesion.getPme().getTelephonePME())))
                .andExpect(jsonPath("$.pme.ninea", is(demandeAdhesion.getPme().getNinea())))
                .andExpect(jsonPath("$.pme.adressePME", is(demandeAdhesion.getPme().getAdressePME())));
    }


    @Test
    void accpetedDemandeAdhesion_shouldAcceptDemande() throws Exception{
        // given - precondition or setup
        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());
        demandeAdhesion = adhesionService.saveAdhesion(dto);


        DemandeAdhesionDto acceptedDemandeDto = DemandeAdhesionDTOTestData.updatedDTO();

        DemandeAdhesion acceptedDemande = DtoConverter.convertToEntity(acceptedDemandeDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                patch(baseUrl+"/"+"{id}"+"/acceptadhesion", demandeAdhesion.getIdDemande())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(acceptedDemande)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pme.telephonePME", is(demandeAdhesion.getPme().getTelephonePME())))
                .andExpect(jsonPath("$.pme.ninea", is(demandeAdhesion.getPme().getNinea())))
                .andExpect(jsonPath("$.pme.adressePME", is(demandeAdhesion.getPme().getAdressePME())));
    }






}
