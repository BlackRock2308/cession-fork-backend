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
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.DemandeCessionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.UtilisateurDTOTestData;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.PaiementService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaiementResourceTest extends BasicResourceTest{

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;

    static PaiementDto dto;
    static Paiement entity;

    static Paiement addedPaiement;
    static Paiement paiement;

    @Autowired @Valid
    PaiementRepository paiementRepository;
    @Autowired @Valid
    PaiementService paiementService;

    static DemandeCessionDto demandeDto;
    static DemandeCessionDto addedDemandeDto;

    static DemandeCession demandeCession;
    static DemandeCession addedDemandeCession;


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
;

        //Init for BonEngagement
        dtoBE = BonEngagementDTOTestData.defaultDTO();
        entityBE = DtoConverter.convertToEntity(dtoBE);

        statut = new Statut(1L,"CONVENTION_ACCEPTEE", "CONVENTION_ACCEPTEE");

    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");

        baseUrl = baseUrl + ":" + port + "/api/paiements";

        //Init for Pme

        pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);

        //Init for BonEngagement

        bonEngagementRepository.deleteAll();
        entityBE= bonEngagementService.save(entityBE);

        //init for DemandeAdhesion which just required the idPME
        demandeDto = DemandeCessionDTOTestData.defaultDTO();
        demandeDto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        demandeDto.setPme(DtoConverter.convertToDto(entityPme));
        demandeDto.setStatut(DtoConverter.convertToDto(statut));
        cessionRepository.deleteAll();
        demandeCession = cessionService.saveCession(DtoConverter.convertToEntity(demandeDto));

        statutRepository.save(statut);


        //Init paiement
        entity = new Paiement();
        entity.setIdPaiement(1L);
        entity.setDemandeCession(demandeCession);
        entity.setStatutCDMP(statut);
        entity.setStatutPme(statut);


    }


    @AfterEach
    void afterEach(){
        paiementRepository.deleteAll();
        cessionRepository.deleteAll();
        pmeRepository.deleteAll();
        bonEngagementRepository.deleteAll();
    }


    @Test
    @Rollback(value = true)
    void save_shouldSavePaiement() {

        PaiementDto newPaiement = restTemplate
                .postForObject(baseUrl, DtoConverter.convertToDto(entity), PaiementDto.class);

        Assertions.assertAll(
                ()-> assertThat(newPaiement.getIdPaiement()).isNotNull(),
                ()->  assertThat(status().isOk())
        );
    }

    @Test
    void findAll_shouldReturnResult() {

        paiement = paiementService.savePaiement(DtoConverter.convertToDto(entity));

        List paiementList = restTemplate.getForObject(baseUrl, List.class);

        assertThat(paiementList.size()).isEqualTo(1);
    }

    @Test
    void findById_shouldReturnExistingPaiementTest() {
        paiement = paiementService.savePaiement(DtoConverter.convertToDto(entity));

        PaiementDto existingPaiement = restTemplate
                .getForObject(baseUrl+"/"+paiement.getIdPaiement(), PaiementDto.class);

        assertNotNull(existingPaiement);
        assertEquals(paiement.getNomMarche(), existingPaiement.getNomMarche());
    }


    @Test
    void getPaiementsByPME_shouldReturnResult() {

        paiement = paiementService.savePaiement(entity);

        List paiementList = restTemplate
                .getForObject(baseUrl+"/bypme/"+entityPme.getIdPME(), List.class);

        assertThat(paiementList.size()).isEqualTo(1);
    }

    @Test
    void getAllPaiementsByPME_shouldReturnResult() {

        paiement = paiementService.savePaiement(entity);

        List paiementList = restTemplate
                .getForObject(baseUrl+"/pme/"+entityPme.getIdPME(), List.class);

        assertThat(paiementList.size()).isEqualTo(1);
    }


    @Test
    void delete_shouldDeletePaiement() {

        paiement = paiementService.savePaiement(entity);

        restTemplate.delete(baseUrl+"/"+paiement.getIdPaiement());

        int count = paiementRepository.findAll().size();

        assertEquals(0, count);
    }


    @Test
    void getPaiementAndDetailPaimentCDMP_PME_shouldReturnExistingPaiement() {
        paiement = paiementService.savePaiement(DtoConverter.convertToDto(entity));

        PaiementDto existingPaiement = restTemplate
                .getForObject(baseUrl+"/cdmp-pme/"+paiement.getIdPaiement(), PaiementDto.class);

        assertNotNull(existingPaiement);
        assertEquals(paiement.getNomMarche(), existingPaiement.getNomMarche());
    }

    @Test
    void getPaiementAndDetailPaimentSICA_CDMP_shouldReturnExistingPaiement() {
        paiement = paiementService.savePaiement(DtoConverter.convertToDto(entity));

        PaiementDto existingPaiement = restTemplate
                .getForObject(baseUrl+"/sica-cdmp/"+paiement.getIdPaiement(), PaiementDto.class);

        assertNotNull(existingPaiement);
        assertEquals(paiement.getNomMarche(), existingPaiement.getNomMarche());
    }

    @Test
    void getStatistiquePaiementByPMEByAnnee_shouldReturnStat() {

        paiement = paiementService.savePaiement(DtoConverter.convertToDto(entity));

        restTemplate.getForObject(baseUrl+"/getStatistiquePaiementByPME/"+2022+"/"+entityPme.getIdPME(), PaiementDto.class);

        int count = paiementRepository.findAll().size();

        assertEquals(1, count);
    }

    @Test
    void getStatistiqueAllPaiementPME_shouldReturnStat() {

        paiement = paiementService.savePaiement(DtoConverter.convertToDto(entity));

        restTemplate.getForObject(baseUrl+"/getStatistiqueAllPaiementPME/"+2022, PaiementDto.class);

        int count = paiementRepository.findAll().size();

        assertEquals(1, count);
    }

    @Test
    void getStatistiquePaiementCDMP_shouldReturnStat() {

        paiement = paiementService.savePaiement(DtoConverter.convertToDto(entity));

        restTemplate.getForObject(baseUrl+"/getStatistiquePaiementCDMP/"+2022, PaiementDto.class);

        int count = paiementRepository.findAll().size();

        assertEquals(1, count);
    }
}
