package sn.modelsis.cdmp.resources;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.DemandeAdhesionDTOTestData;
import sn.modelsis.cdmp.data.DemandeCessionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Statut;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DemandeCessionResourceTest extends BasicResourceTest{

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


    @Test
    void findAll_shouldReturnDemandeCession() throws Exception {

//        pme = pmeService.savePme(entityPme);
//        dto = DemandeCessionDTOTestData.defaultDTO();
//        dto.setPme(pme);

        entity = DtoConverter.convertToEntity(dto);

        demandeCession = cessionService.saveCession(entity);

        mockMvc.perform(
                        get("/api/demandecession").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].idDemande").isNotEmpty());

    }

    @Test
    void findById_shouldReturnOneDemandeCession() throws Exception {
        entity = DtoConverter.convertToEntity(dto);

        demandeCession = cessionService.saveCession(entity);

        mockMvc.perform(
                        get("/api/demandecession/{id}", demandeCession.getIdDemande())
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").value(demandeCession.getIdDemande()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").isNotEmpty());

    }
}
