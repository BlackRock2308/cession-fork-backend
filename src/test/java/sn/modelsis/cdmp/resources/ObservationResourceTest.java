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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import sn.modelsis.cdmp.data.*;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;
import sn.modelsis.cdmp.mappers.ObservationMapper;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.*;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObservationResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    Observation entity;
    ObservationDto dto;
    static Observation observation;
    @Valid @Autowired
    ObservationRepository observationRepository;
    @Valid @Autowired
    ObservationService observationService;

    DemandeCessionDto demandeDto;
    static DemandeCession demandeCession;

    @Autowired
    @Valid
    DemandeCessionRepository cessionRepository;
    @Autowired
    @Valid
    DemandeCessionService cessionService;

    @Autowired
    @Valid
    PmeRepository pmeRepository;
    @Autowired
    @Valid
    PmeService pmeService;

    PmeDto dtoPme;
    Pme entityPme;
    @Autowired
    @Valid
    BonEngagementRepository bonEngagementRepository;
    @Autowired
    @Valid
    BonEngagementService bonEngagementService;

    BonEngagementDto dtoBE;
    BonEngagement entityBE;

    private Statut statut;

    private StatutDto statutDto;


    @Autowired
    @Valid
    StatutRepository statutRepository;
    @Autowired @Valid
    ObservationMapper observationMapper;

    private static Utilisateur utilisateur;
    private static Utilisateur userEntity;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    UtilisateurRepository utilisateurRepository;



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
       // restTemplate = new RestTemplate();
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");

        restTemplate = new RestTemplate();

        baseUrl = baseUrl + ":" + port + "/api/observations";
        //Init for PME
        dtoPme = PmeDTOTestData.defaultDTO();
        entityPme = DtoConverter.convertToEntity(dtoPme);
        pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);

        //Init for BonEngagement
        dtoBE = BonEngagementDTOTestData.defaultDTO();
        entityBE = DtoConverter.convertToEntity(dtoBE);
        bonEngagementRepository.deleteAll();
        entityBE= bonEngagementService.save(entityBE);

        //init for DemandeAdhesion which just required the idPME
        demandeDto = DemandeCessionDTOTestData.defaultDTO();
        demandeDto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        demandeDto.setPme(DtoConverter.convertToDto(entityPme));

        demandeCession = cessionService.saveCession(DtoConverter.convertToEntity(demandeDto));

        statut = new Statut(1L,"REJETEE", "REJETEE");
        statutRepository.save(statut);

        //Init User
        userEntity = UtilisateurDTOTestData.defaultEntity();
        utilisateur = utilisateurService.save(userEntity);


        //Init Obervation
        dto = new ObservationDto();
        dto.setId(1L);
        dto.setUtilisateurid(utilisateur.getIdUtilisateur());
        dto.setDemandeid(demandeCession.getIdDemande());
        dto.setStatut(DtoConverter.convertToDto(statut));
        dto.setLibelle("Test Observation");

    }

    @AfterEach
    void afterEach(){
        observationRepository.deleteAll();
        pmeRepository.deleteAll();
        cessionRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }


    @Test
   // @Order(1)
    void save_shouldSaveNewObservationTest() throws Exception {

        mockMvc.perform(
                        post("/api/observations")
                                .content(asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    //@Order(2)
    void findAll_shouldReturnObservations() throws Exception {

        ObservationDto observationDto =  observationService.saveNewObservation(dto);
        mockMvc.perform(
                        get("/api/observations").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].libelle").value(observationDto.getLibelle()));
    }

    @Test
    void shouldFetchAllObservationsTest() {

        ObservationDto observationDto =  observationService.saveNewObservation(dto);

        List observationList = restTemplate.getForObject(baseUrl, List.class);

        assertThat(observationList.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldReturnExistingObservationTest() {
        observationRepository.deleteAll();
        ObservationDto observationDto =  observationService.saveNewObservation(dto);

        ObservationDto existingObservation = restTemplate
                .getForObject(baseUrl+"/"+observationDto.getId(), ObservationDto.class);

        assertNotNull(existingObservation);
        //assertEquals(observationDto.getLibelle(), existingObservation.getLibelle());
    }


    @Test
    void findLastObservation_shouldReturnLastObservationTest() {

        ObservationDto observationDto =  observationService.saveNewObservation(dto);

        ObservationDto existingObservation = restTemplate
                .getForObject(baseUrl+"/"+"last-observation/"+observationDto.getId(), ObservationDto.class);

        assertNotNull(existingObservation);
        //assertEquals(observationDto.getLibelle(), existingObservation.getLibelle());
    }



    @Test
    void delete_shouldDeleteObservation() {

        ObservationDto observationDto =  observationService.saveNewObservation(dto);

        int count_1 = observationRepository.findAll().size();

        log.info("result initial : {}", count_1);

         restTemplate.delete(baseUrl+"/"+observationDto.getId());

        int count = observationRepository.findAll().size();

        log.info("result final : {}", count);

        assertEquals(0, count-1);
    }

}
