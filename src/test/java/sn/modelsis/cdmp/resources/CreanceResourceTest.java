package sn.modelsis.cdmp.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.DemandeCessionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CreanceResourceTest extends BasicResourceTest{

    DemandeCessionDto dto;

    DemandeCession entity;

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
    static Pme pme;
    @Autowired
    @Valid
    BonEngagementRepository bonEngagementRepository;
    @Autowired
    @Valid
    BonEngagementService bonEngagementService;

    BonEngagementDto dtoBE;
    BonEngagement entityBE;

    static BonEngagement bonEngagement;

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

    @BeforeEach
    void setUp() {

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
        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));
//        dto.setStatut(DtoConverter.convertToDto(statut));
        //cessionRepository.deleteAll();
//        entity = cessionService.saveCession(DtoConverter.convertToEntity(dto));
    }

//    @Test
//    void findByNomMarche_shouldReturnResult() throws Exception {
//
//        entity = DtoConverter.convertToEntity(dto);
//
//        demandeCession = cessionService.saveCession(entity);
//
//        mockMvc.perform(
//                        get("/api/demandecession/searchByNomMarche")
//                                .content(demandeCession.getBonEngagement().getNomMarche())
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andDo(MockMvcResultHandlers.print()) //can print request details
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].idDemande").isNotEmpty());
//
//    }

//     @Test
//     void testGetUserByName() throws Exception {
//        String nomMarche = "Jack";
//        mockMvc.perform( MockMvcRequestBuilders
//                        .get("/api/demandecession/searchByNomMarche")
//                        .param("nomMarche", nomMarche)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
//
//    }

//    @Test
//    public void customer_fetch_customer_by_mobileNo_exception_success() throws Exception {
//
//        entity = DtoConverter.convertToEntity(dto);
//
//        demandeCession = cessionService.saveCession(entity);
//
//        String searchedNomMarche = "Groot Marcet";
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/demandecession/searchByNomMarche?nomMarche=" + searchedNomMarche))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }
}
