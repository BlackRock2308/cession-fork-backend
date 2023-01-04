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
import sn.modelsis.cdmp.data.*;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Testcontainers

@Slf4j
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemandeCessionResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;
    static DemandeCessionDto dto;
    static DemandeCession entity;
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

    static Statut statut;

    static StatutDto statutDto;


    @Autowired
    @Valid
    StatutRepository statutRepository;

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

        statut = new Statut(1L,"REJETEE", "REJETEE");


        //init for Demande Cession which just required the idPME
        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));




    }

    @BeforeEach
    void setUp() {

        baseUrl = baseUrl + ":" + port + "/api/demandecession";

        //pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);


        //bonEngagementRepository.deleteAll();
        entityBE= bonEngagementService.save(entityBE);

        //statut = new Statut(1L,"REJETEE", "REJETEE");
        statutRepository.save(statut);

        entity = DtoConverter.convertToEntity(dto);

    }


    @AfterEach
    void afterEach(){
//        pmeRepository.deleteAll();
//        statutRepository.deleteAll();
        cessionRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void add_shouldCreateDemandeCessionTest() throws Exception {

        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));

        DemandeCession newCession = restTemplate
                .postForObject(baseUrl, dto ,DemandeCession.class);

        assertThat(newCession)
                .isNotNull();
    }


    @Test
    void findAll_shouldReturnDemandeCessionTest() throws Exception {

        demandeCession = cessionService.saveCession(entity);

        mockMvc.perform(
                        get("/api/demandeadhesion").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].idDemande").isNotEmpty());

    }

    @Test
    void findById_shouldReturnOneDemandeCessionTest() throws Exception {


        DemandeCession existingCession = restTemplate
                .getForObject(baseUrl+"/"+demandeCession.getIdDemande(), DemandeCession.class);

        assertNotNull(existingCession);
        assertEquals(existingCession.getNumeroDemande(), existingCession.getNumeroDemande());
    }


    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {

//        entity = DtoConverter.convertToEntity(dto);
//
//        demandeCession = cessionService.saveCession(entity);

        mockMvc.perform(get("/api/demandecession/{id}", UUID.randomUUID().getMostSignificantBits())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof CustomException) );
    }



    @Test
    void rejectedDemandeCession_shouldRejectDemandeCession() throws Exception{
        // given - precondition or setup

        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));

//        entity = DtoConverter.convertToEntity(dto);
//        demandeCession = cessionService.saveCession(entity);


        DemandeAdhesionDto rejectedAdhesionDto = DemandeAdhesionDTOTestData.updatedDTO();

        DemandeAdhesion rejectedAdhesion = DtoConverter.convertToEntity(rejectedAdhesionDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                patch(baseUrl+"/"+"{id}"+"/rejeterRecevabilite", demandeCession.getIdDemande())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rejectedAdhesion)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pme.telephonePME", is(demandeCession.getPme().getTelephonePME())))
                .andExpect(jsonPath("$.pme.ninea", is(demandeCession.getPme().getNinea())))
                .andExpect(jsonPath("$.pme.adressePME", is(demandeCession.getPme().getAdressePME())));
    }


    @Test
    void validateAnalyseDemandeCession_shouldValidateAnalyseDemandeCession() throws Exception{
        // given - precondition or setup

        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));

//        entity = DtoConverter.convertToEntity(dto);
//        demandeCession = cessionService.saveCession(entity);


        DemandeAdhesionDto rejectedAdhesionDto = DemandeAdhesionDTOTestData.updatedDTO();

        DemandeAdhesion rejectedAdhesion = DtoConverter.convertToEntity(rejectedAdhesionDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                patch(baseUrl+"/"+"{id}"+"/validateAnalyse", demandeCession.getIdDemande())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rejectedAdhesion)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pme.telephonePME", is(demandeCession.getPme().getTelephonePME())))
                .andExpect(jsonPath("$.pme.ninea", is(demandeCession.getPme().getNinea())))
                .andExpect(jsonPath("$.pme.adressePME", is(demandeCession.getPme().getAdressePME())));
    }


    @Test
    void rejectedAnalyseDemandeCession_shouldRejectedAnalyseDemandeCession() throws Exception{
        // given - precondition or setup

        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));

//        entity = DtoConverter.convertToEntity(dto);
//        demandeCession = cessionService.saveCession(entity);


        DemandeAdhesionDto rejectedAdhesionDto = DemandeAdhesionDTOTestData.updatedDTO();

        DemandeAdhesion rejectedAdhesion = DtoConverter.convertToEntity(rejectedAdhesionDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                patch(baseUrl+"/"+"{id}"+"/rejectedAnalyse", demandeCession.getIdDemande())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rejectedAdhesion)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pme.telephonePME", is(demandeCession.getPme().getTelephonePME())))
                .andExpect(jsonPath("$.pme.ninea", is(demandeCession.getPme().getNinea())))
                .andExpect(jsonPath("$.pme.adressePME", is(demandeCession.getPme().getAdressePME())));
    }


    @Test
    void completeAnalyseDemandeCession_shouldCompleteAnalyseDemandeCession() throws Exception{
        // given - precondition or setup

        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));



        DemandeAdhesionDto rejectedAdhesionDto = DemandeAdhesionDTOTestData.updatedDTO();

        DemandeAdhesion rejectedAdhesion = DtoConverter.convertToEntity(rejectedAdhesionDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                patch(baseUrl+"/"+"{id}"+"/complementAnalyse", demandeCession.getIdDemande())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rejectedAdhesion)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pme.telephonePME", is(demandeCession.getPme().getTelephonePME())))
                .andExpect(jsonPath("$.pme.ninea", is(demandeCession.getPme().getNinea())))
                .andExpect(jsonPath("$.pme.adressePME", is(demandeCession.getPme().getAdressePME())));
    }

    @Test
    void getAllPMEDemandeCession_shouldReturnResultTest() {

        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));


        demandeCession = cessionService.saveCession(DtoConverter.convertToEntity(dto));


        List pmelist = restTemplate.getForObject(baseUrl+"/"+"pme/"+demandeCession.getPme().getIdPME(), List.class);

        assertThat(pmelist.size()).isEqualTo(1);
    }

//    @Test
//    void getAllRejectedDemandeCession_shouldReturnResultTest() {
//
//        //Statut selectedStatut = statutRepository.findByCode("REJETEE");
//
//
//        dto = DemandeCessionDTOTestData.defaultDTO();
//        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
//        dto.setPme(DtoConverter.convertToDto(entityPme));
//        dto.setStatut(DtoConverter.convertToDto(statut));
//
////        entity = new DemandeCession();
////        entity.setIdDemande(TestData.Default.id);
////        entity.setBonEngagement(entityBE);
////        entity.setPme(entityPme);
////        entity.setStatut(statut);
//
////        if (!selectedStatut.getCode().isEmpty()){
////            dto.setStatut(DtoConverter.convertToDto(selectedStatut));
////        }
//       //log.info("statut : {}", selectedStatut.getLibelle());
//
//        entity = DtoConverter.convertToEntity(dto);
//        demandeCession = cessionService.saveCession(entity);
//
//        List pmelist = restTemplate.getForObject(baseUrl+"/"+"rejected", List.class);
//
//        assertThat(pmelist.size()).isEqualTo(1);
//    }



//
//    @Test
//    void validateDemandeCession_shouldValidateDemandeCession() throws Exception{
//        // given - precondition or setup
//
//        dto = DemandeCessionDTOTestData.defaultDTO();
//        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
//        dto.setPme(DtoConverter.convertToDto(entityPme));
//
//        DemandeAdhesionDto rejectedAdhesionDto = DemandeAdhesionDTOTestData.updatedDTO();
//
//        DemandeAdhesion rejectedAdhesion = DtoConverter.convertToEntity(rejectedAdhesionDto);
//
//        // when -  action or the behaviour that we are going test
//        ResultActions response = mockMvc.perform(
//                patch(baseUrl+"/"+"{id}"+"/validerRecevabilite", demandeCession.getIdDemande())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(rejectedAdhesion)));
//
//        // then - verify the output
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.pme.telephonePME", is(demandeCession.getPme().getTelephonePME())))
//                .andExpect(jsonPath("$.pme.ninea", is(demandeCession.getPme().getNinea())))
//                .andExpect(jsonPath("$.pme.adressePME", is(demandeCession.getPme().getAdressePME())));
//    }
}