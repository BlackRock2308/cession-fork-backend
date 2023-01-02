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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.DemandeCessionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.*;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@Testcontainers

@Slf4j
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DetailPaiementResourceTest extends BasicResourceTest{
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;

    static DetailPaiement entityDetails;

    static DetailPaiementDto dtoDetails, dtoDetailsCdmp;

    @Valid  @Autowired
    DetailPaiementRepository detailPaiementRepository;
    @Valid  @Autowired
    DetailPaiementService detailPaiementService;

    static Paiement entity, entityForCdmp;
    static Paiement paiement, paiementForCdmp;

    @Autowired
    @Valid
    PaiementRepository paiementRepository;
    @Autowired @Valid
    PaiementService paiementService;

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
    static Statut statut, statutPaiement,statutPaiementCDMP;

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

        statut = new Statut(1L,"CONVENTION_ACCEPTEE", "CONVENTION_ACCEPTEE");
        statutPaiement = new Statut(2L,"PME_PARTIELLEMENT_PAYEE", "PME_PARTIELLEMENT_PAYEE");
        statutPaiementCDMP = new Statut(3L,"CDMP_PARTIELLEMENT_PAYEE", "CDMP_PARTIELLEMENT_PAYEE");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");

        baseUrl = baseUrl + ":" + port + "/api/detailsPaiements";

        //Init for Pme

        //pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);

        //Init for BonEngagement

        //bonEngagementRepository.deleteAll();
        entityBE= bonEngagementService.save(entityBE);

        //init for DemandeAdhesion which just required the idPME
        demandeDto = DemandeCessionDTOTestData.defaultDTO();
        demandeDto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        demandeDto.setPme(DtoConverter.convertToDto(entityPme));
        demandeDto.setStatut(DtoConverter.convertToDto(statut));
        //cessionRepository.deleteAll();
        demandeCession = cessionService.saveCession(DtoConverter.convertToEntity(demandeDto));

        statutRepository.save(statut);
        statutRepository.save(statutPaiement);
        statutRepository.save(statutPaiementCDMP);



    }

    @AfterEach
    void afterEach(){
        detailPaiementRepository.deleteAll();
//        paiementRepository.deleteAll();
        cessionRepository.deleteAll();
//        pmeRepository.deleteAll();
//        bonEngagementRepository.deleteAll();

    }

    @Test
    @Rollback(value = false)
    void save_shouldSave_DetailPaiementForPME() {

        //Init paiement
        entity = new Paiement();
        entity.setIdPaiement(1L);
        entity.setDemandeCession(demandeCession);
        entity.setStatutCDMP(statutPaiement);
        entity.setStatutPme(statutPaiementCDMP);
        entity.setSoldePME(5_000_000L);

        paiement = paiementService.addPaiementToDemandeCession(DtoConverter.convertToDto(entity));

        //Init for Details paiement
        dtoDetails = new DetailPaiementDto();
        dtoDetails.setPaiementDto(DtoConverter.convertToDto(paiement));
        dtoDetails.setDatePaiement(TestData.Default.dateDemandeCession);
        dtoDetails.setId(TestData.Default.id);
        dtoDetails.setModePaiement("CHEQUE");
        dtoDetails.setMontant(1_000_000L);
        dtoDetails.setIdDemande(demandeCession.getIdDemande());

        DetailPaiementDto newDetailPayment = restTemplate
                .postForObject(baseUrl+"/cdmp-pme", dtoDetails, DetailPaiementDto.class);

        Assertions.assertAll(
                ()-> assertThat(newDetailPayment.getId()).isNotNull(),
                ()->  assertThat(status().isOk())
        );
    }



//    @Test
//    void findAll_shouldReturnResult() {
//
//        //Init for Details paiement
//        dtoDetails = new DetailPaiementDto();
//        dtoDetails.setPaiementDto(DtoConverter.convertToDto(entity));
//        dtoDetails.setDatePaiement(TestData.Default.dateDemandeCession);
//        dtoDetails.setId(TestData.Default.id);
//        dtoDetails.setModePaiement("CHEQUE");
//        dtoDetails.setMontant(5_000_000L);
//        dtoDetails.setIdDemande(demandeCession.getIdDemande());
//
//        entityDetails = detailPaiementService.paiementPME(DtoConverter.convertToEntity(dtoDetails));
//
//        List paiementList = restTemplate.getForObject(baseUrl, List.class);
//
//        assertThat(paiementList.size()).isEqualTo(5);
//    }

    @Test
    void findById_shouldReturnExistingDetailsTest() {

        //Init paiement
        entity = new Paiement();
        entity.setIdPaiement(1L);
        entity.setDemandeCession(demandeCession);
        entity.setStatutCDMP(statutPaiement);
        entity.setStatutPme(statutPaiementCDMP);
        entity.setSoldePME(5_000_000L);

        paiement = paiementService.addPaiementToDemandeCession(DtoConverter.convertToDto(entity));

        //Init for Details paiement
        dtoDetails = new DetailPaiementDto();
        dtoDetails.setPaiementDto(DtoConverter.convertToDto(paiement));
        dtoDetails.setDatePaiement(TestData.Default.dateDemandeCession);
        dtoDetails.setId(TestData.Default.id);
        dtoDetails.setModePaiement("CHEQUE");
        dtoDetails.setMontant(1_000_000L);
        dtoDetails.setIdDemande(demandeCession.getIdDemande());

        entityDetails = detailPaiementService.paiementPME(DtoConverter.convertToEntity(dtoDetails));

        DetailPaiementDto existingPaiement = restTemplate
                .getForObject(baseUrl+"/"+entityDetails.getId(), DetailPaiementDto.class);

        assertNotNull(existingPaiement);
        assertEquals(entityDetails.getId(), existingPaiement.getId());
    }





    @Test
    void getDetailPaiementCDMP_PME_shouldReturnResultTest()  {

        //Init paiement
        entity = new Paiement();
        entity.setIdPaiement(2L);
        entity.setDemandeCession(demandeCession);
        entity.setStatutCDMP(statutPaiement);
        entity.setStatutPme(statutPaiementCDMP);
        entity.setSoldePME(5_000_000L);

        paiement = paiementService.addPaiementToDemandeCession(DtoConverter.convertToDto(entity));

        //Init for Details paiement
        dtoDetails = new DetailPaiementDto();
        dtoDetails.setPaiementDto(DtoConverter.convertToDto(paiement));
        dtoDetails.setDatePaiement(TestData.Default.dateDemandeCession);
        dtoDetails.setId(TestData.Default.id);
        dtoDetails.setModePaiement("CHEQUE");
        dtoDetails.setMontant(1_000_000L);
        dtoDetails.setIdDemande(demandeCession.getIdDemande());

        entityDetails = detailPaiementService.paiementPME(DtoConverter.convertToEntity(dtoDetails));

        List paiementList = restTemplate.getForObject(baseUrl+"/cdmp-pme/"+paiement.getIdPaiement(), List.class);

        assertThat(paiementList.size()).isEqualTo(1);
    }


    @Test
    void getDetailPaiementSICA_CDMP_shouldReturnResultTest()  {

        //paiement = paiementService.addPaiementToDemandeCession(DtoConverter.convertToDto(entity));

        //Init for Details paiement
        dtoDetails = new DetailPaiementDto();
        dtoDetails.setPaiementDto(DtoConverter.convertToDto(paiement));
        dtoDetails.setDatePaiement(TestData.Default.dateDemandeCession);
        dtoDetails.setId(TestData.Default.id);
        dtoDetails.setModePaiement("CHEQUE");
        dtoDetails.setMontant(1_000_000L);
        dtoDetails.setIdDemande(demandeCession.getIdDemande());

        //entityDetails = detailPaiementService.paiementPME(DtoConverter.convertToEntity(dtoDetails));

        List paiementList = restTemplate.getForObject(baseUrl+"/sica-cdmp/"+paiement.getIdPaiement(), List.class);

        assertThat(paiementList.size()).isEqualTo(0);
    }

//    @Test
//    void delete_shouldDeletePaiementDetail() {
//
//        //Init for Details paiement
//        dtoDetails = new DetailPaiementDto();
//        dtoDetails.setPaiementDto(DtoConverter.convertToDto(entity));
//        dtoDetails.setDatePaiement(TestData.Default.dateDemandeCession);
//        dtoDetails.setId(TestData.Default.id);
//        dtoDetails.setModePaiement("CHEQUE");
//        dtoDetails.setMontant(1_000_000L);
//        dtoDetails.setIdDemande(demandeCession.getIdDemande());
//
//        entityDetails = detailPaiementService.paiementPME(DtoConverter.convertToEntity(dtoDetails));
//
//        restTemplate.delete(baseUrl+"/"+entityDetails.getId());
//
//        int count = detailPaiementRepository.findAll().size();
//
//        assertEquals(4, count);
//    }
}
