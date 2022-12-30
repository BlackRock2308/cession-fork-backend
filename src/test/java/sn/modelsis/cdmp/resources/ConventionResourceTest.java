package sn.modelsis.cdmp.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.DemandeCessionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;
import sn.modelsis.cdmp.mappers.ConventionMapper;
import sn.modelsis.cdmp.mappers.DemandeCessionMapper;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.*;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Slf4j
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConventionResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;
    private static TestRestTemplate testRestTemplate;

    private static ConventionDto dto;

    private static Convention entity;

    private static Convention convention;
    @Autowired
    @Valid
    ConventionMapper conventionMapper;


    @Valid @Autowired
    ConventionRepository conventionRepository;
    @Valid @Autowired
    ConventionService conventionService;

    static DemandeCession demandeCession;

    static DemandeCessionDto demandeDto;
    static DemandeCession demandeEntity;
    static DemandeCessionDto addedDemandeDto;



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

    static PmeDto dtoPme;
    static Pme entityPme;
    @Autowired
    @Valid
    BonEngagementRepository bonEngagementRepository;

    @Valid @Autowired
    BonEngagementService bonEngagementService;

    static BonEngagementDto dtoBE;
    static BonEngagement entityBE;
    static Statut statut;

    @Valid @Autowired
    StatutRepository statutRepository;
    @Valid @Autowired
    ParametrageDecoteRepository decoteRepository;
    @Valid @Autowired
    ParametrageDecoteService decoteService;

    static Optional<ParametrageDecote> decote;


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
    static void beforeAll(){
        log.info(" before all");

        restTemplate = new RestTemplate();

        //Init for PME
        dtoPme = PmeDTOTestData.defaultDTO();
        entityPme = DtoConverter.convertToEntity(dtoPme);

        //Init for BonEngagement
        dtoBE = BonEngagementDTOTestData.defaultDTO();
        entityBE = DtoConverter.convertToEntity(dtoBE);

        statut = new Statut(1L,"SOUMISE", "SOUMISE");

        //Init for Decote
//        decote = new ParametrageDecote();
//        decote.setIdDecote(1L);
//        decote.setBorneInf(1_000_000L);
//        decote.setBorneSup(5_000_000L);
//        decote.setDecoteValue(0.1);
    }



    @BeforeEach
    void beforeEach() {
        log.info(" before each ");

        baseUrl = baseUrl + ":" + port + "/api/conventions";

        //Init for Pme
        //pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);

        //Init for BonEngagementdd
        //bonEngagementRepository.deleteAll();
        entityBE= bonEngagementService.save(entityBE);


        statut = statutRepository.findByCode("NON_RISQUEE");

        //init for DemandeAdhesion which just required the idPME
        demandeDto = DemandeCessionDTOTestData.defaultDTO();
        demandeDto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        demandeDto.setPme(DtoConverter.convertToDto(entityPme));
        demandeDto.setStatut(DtoConverter.convertToDto(statut));

        demandeEntity = new DemandeCession();
        demandeEntity.setIdDemande(TestData.Default.id);
        demandeEntity.setStatut(statut);
        demandeEntity.setPme(entityPme);
        demandeEntity.setBonEngagement(entityBE);

        decote = decoteService.findIntervalDecote(entityBE.getMontantCreance());


        List<Statut> statuList = statutRepository.findAll();

        log.info("statut list : {}", statuList);


        //cessionRepository.deleteAll();
        //demandeCession = cessionService.saveCession(DtoConverter.convertToEntity(demandeDto));
        demandeCession = cessionService.saveCession(demandeEntity);



        //Init convention
        entity = new Convention();
        entity.setIdConvention(TestData.Default.id);
        entity.setPme(entityPme);
        entity.setDemandeCession(demandeCession);
        //entity.setDecote(decote.get());

//        dto = new ConventionDto();
//        dto.setIdConvention(TestData.Default.id);
//        dto.setPmeDto(dtoPme);
//        dto.setIdDemande(demandeCession.getIdDemande());

        //decoteRepository.saveAndFlush(decote);
    }

    @AfterEach
    void afterEach(){
        bonEngagementRepository.deleteAll();
        conventionRepository.deleteAll();
//        cessionRepository.deleteAll();
//        pmeRepository.deleteAll();

    }



//    @Test
//    @Rollback(value = false)
//    void add_shouldCreateNewConventionTest() {
//
//        ConventionDto newConvention = restTemplate
//                .postForObject(baseUrl,DtoConverter.convertToDto(entity), ConventionDto.class);
//
//        Assertions.assertAll(
//                ()-> assertThat(newConvention.getIdConvention()).isNotNull(),
//                ()->  assertThat(status().isOk())
//        );
//    }


//    @Test
//    void findAll_shouldReturnResult() {
//
//        Convention newConvention = restTemplate
//                .postForObject(baseUrl,DtoConverter.convertToDto(entity), Convention.class);
//
//        List conventionList = restTemplate.getForObject(baseUrl, List.class);
//
//        assertThat(conventionList.size()).isEqualTo(1);
//    }


//     @Test
//    void findAll_shouldReturnConventionListTest() throws Exception {
//
//
//        mockMvc.perform(
//                        get("/api/conventions").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andDo(MockMvcResultHandlers.print()) //can print request details
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
//
//    }


}
