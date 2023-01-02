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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.modelsis.cdmp.data.*;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;
import sn.modelsis.cdmp.mappers.ObservationMapper;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.*;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@Testcontainers

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

    static Observation entity;
    static ObservationDto dto;
    @Valid @Autowired
    ObservationRepository observationRepository;
    @Valid @Autowired
    ObservationService observationService;

    static DemandeCessionDto demandeDto;
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

    static PmeDto dtoPme;
    static Pme entityPme;
    @Autowired
    @Valid
    BonEngagementRepository bonEngagementRepository;
    @Autowired
    @Valid
    BonEngagementService bonEngagementService;

    static BonEngagementDto dtoBE;
    static BonEngagement entityBE;

    private Statut statut;

    private StatutDto statutDto;


    @Autowired
    @Valid
    StatutRepository statutRepository;
    @Autowired @Valid
    ObservationMapper observationMapper;

    private static Utilisateur utilisateur;
    private static Utilisateur userEntity;
    @Autowired @Valid
    UtilisateurService utilisateurService;
    @Autowired @Valid
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
    static void beforeAll(){
        log.info(" before all");

        restTemplate = new RestTemplate();

        //Init for PME
        dtoPme = new PmeDto();
        dtoPme.setIdPME(TestData.Default.id);
        dtoPme.setTelephonePME("770000000");
        dtoPme.setNinea(TestData.Default.ninea);
        dtoPme.setRccm(TestData.Default.rccm);
        dtoPme.setCniRepresentant(TestData.Default.cniRepresentant);
        dtoPme.setEmail("testntegration@gmail.sn");
        dtoPme.setAdressePME(TestData.Default.adressePME);
        dtoPme.setDateImmatriculation(TestData.Default.dateImmatriculation);
        dtoPme.setNomRepresentant(TestData.Default.nomRepresentant);
        dtoPme.setPrenomRepresentant(TestData.Default.prenomRepresentant);
        dtoPme.setActivitePrincipale(TestData.Default.activitePrincipale);
        entityPme = DtoConverter.convertToEntity(dtoPme);

        //Init for BonEngagement
        dtoBE = BonEngagementDTOTestData.defaultDTO();
        entityBE = DtoConverter.convertToEntity(dtoBE);

        //Init User
        userEntity = UtilisateurDTOTestData.defaultEntity();
        userEntity.setEmail("user-email@gmail.sn");
    }



    @BeforeEach
    void beforeEach() {
        log.info(" before each ");

        baseUrl = baseUrl + ":" + port + "/api/observations";

        //Init for Statut
        statut = new Statut(1L,"REJETEE", "REJETEE");
        statutRepository.save(statut);


        //pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);

        //bonEngagementRepository.deleteAll();
        entityBE= bonEngagementService.save(entityBE);

        //init for DemandeAdhesion which just required the idPME
        demandeDto = DemandeCessionDTOTestData.defaultDTO();
        demandeDto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        demandeDto.setPme(DtoConverter.convertToDto(entityPme));
        demandeDto.setStatut(DtoConverter.convertToDto(statut));
        //cessionRepository.deleteAll();
        demandeCession = cessionService.saveCession(DtoConverter.convertToEntity(demandeDto));

        //Init Obervation
        dto = new ObservationDto();
        dto.setId(1L);
        dto.setUtilisateurid(userEntity.getIdUtilisateur());
        dto.setDemandeid(demandeCession.getIdDemande());
        dto.setStatut(DtoConverter.convertToDto(statut));
        dto.setLibelle("Test Observation");

        utilisateur = utilisateurService.save(userEntity);

    }

    @AfterEach
    void afterEach(){
        observationRepository.deleteAll();
//        pmeRepository.deleteAll();
        cessionRepository.deleteAll();
//        utilisateurRepository.deleteAll();
    }


//    @Test
//    void save_shouldSaveNewObservationTest() throws Exception {
//
//        mockMvc.perform(
//                        post("/api/observations")
//                                .content(asJsonString(dto))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//    }


//    @Test
//    void findAll_shouldReturnObservations() throws Exception {
//
//        ObservationDto observationDto =  observationService.saveNewObservation(dto);
//
//        mockMvc.perform(
//                        get("/api/observations").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andDo(MockMvcResultHandlers.print()) //can print request details
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
//    }
//
//    @Test
//    void shouldFetchAllObservationsTest() {
//
//        ObservationDto observationDto =  observationService.saveNewObservation(dto);
//
//        List observationList = restTemplate.getForObject(baseUrl, List.class);
//
//        assertThat(observationList.size()).isEqualTo(1);
//    }
//
//    @Test
//    void findById_shouldReturnExistingObservationTest() {
//        //observationRepository.deleteAll();
//        ObservationDto observationDto =  observationService.saveNewObservation(dto);
//
//        ObservationDto existingObservation = restTemplate
//                .getForObject(baseUrl+"/"+observationDto.getId(), ObservationDto.class);
//
//        assertNotNull(existingObservation);
//        //assertEquals(observationDto.getLibelle(), existingObservation.getLibelle());
//    }
//
//
//
//    @Test
//    void findLastObservation_shouldReturnLastObservation() throws Exception {
//
//        ObservationDto observationDto =  observationService.saveNewObservation(dto);
//
//        mockMvc.perform(
//                        get("/api/observations/last-observation/{id}",observationDto.getDemandeid()).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andDo(MockMvcResultHandlers.print()) //can print request details
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.libelle").value(observationDto.getLibelle()));
//    }
//
//
//
////    @Test
////    void delete_shouldDeleteObservation() throws Exception {
////        // set up test data
////        ObservationDto observationDto =  observationService.saveNewObservation(dto);
////
////        // perform the DELETE request
////        mockMvc.perform(
////                        MockMvcRequestBuilders.delete("/api/observations/{id}",observationDto.getId()))
////                .andExpect(status().isNoContent());
////
////    }
//
//
//    @Test
//    void findLastObservation_shouldReturnLastObservationTest() {
//
//        ObservationDto observationDto =  observationService.saveNewObservation(dto);
//
//        ObservationDto existingObservation = restTemplate
//                .getForObject(baseUrl+"/"+"last-observation/"+observationDto.getDemandeid(), ObservationDto.class);
//
//        assertNotNull(existingObservation);
////        assertEquals(entityDetails.getId(), existingPaiement.getId());
//    }




    //    @Test
//    void delete_shouldDeleteObservation() {
//
//        //ObservationDto observationDto =  observationService.saveNewObservation(dto);
//
//        int count_1 = observationRepository.findAll().size();
//
//        restTemplate.delete(baseUrl+"/"+observationService.saveNewObservation(dto).getId());
//
//        int count = observationRepository.findAll().size();
//
//        assertEquals(0, count-1);
//    }

}
