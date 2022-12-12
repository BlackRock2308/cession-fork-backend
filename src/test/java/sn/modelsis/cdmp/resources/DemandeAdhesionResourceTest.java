package sn.modelsis.cdmp.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.DemandeAdhesionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.repositories.DemandeAdhesionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DemandeAdhesionResourceTest extends BasicResourceTest{

    private static Pme entityPme;
    private static Pme pme;

    private static DemandeAdhesion entity;

    private static DemandeAdhesionDto dto;

    private static DemandeAdhesionDto updatedDto;


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
    static void beforeAll() {
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        pmeRepository.deleteAll();
        adhesionRepository.deleteAll();
        entityPme = PmeDTOTestData.defaultEntity();
//        dto = DemandeAdhesionDTOTestData.defaultDTO();
//        dto.setIdPME(entityPme.getIdPME());
//        entity = DtoConverter.convertToEntity(dto);
//        entity.setPme(entityPme);

        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());
        demandeAdhesion = adhesionService.saveAdhesion(dto);

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

        mockMvc.perform(
                get("/api/demandeadhesion/{id}", demandeAdhesion.getIdDemande())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").value(demandeAdhesion.getIdDemande()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").isNotEmpty());

    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/demandeadhesion/{id}", UUID.randomUUID().getMostSignificantBits())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof NoSuchElementException) );
    }

    @Test
    @Transactional
    void add_shouldCreateDemandeAdhesion() throws Exception {
//        pme = pmeService.savePme(entityPme);
//        dto = DemandeAdhesionDTOTestData.defaultDTO();
//        dto.setIdPME(pme.getIdPME());
//        demandeAdhesion = adhesionService.saveAdhesion(dto);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/demandeadhesion")
                        .content(asJsonString(demandeAdhesion))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").exists())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(jsonPath("$.idDemande", is(demandeAdhesion.getIdDemande())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").value(demandeAdhesion.getIdDemande()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").isNotEmpty());
    }

    @Test
    void update_shouldUpdateDemande() throws Exception {
//        pme = pmeService.savePme(entityPme);
//        dto = DemandeAdhesionDTOTestData.defaultDTO();
//        dto.setIdPME(pme.getIdPME());
//        demandeAdhesion = adhesionService.saveAdhesion(dto);


        demandeAdhesion.setNumeroDemande(TestData.Update.numeroDemande);
        updatedDto = DtoConverter.convertToDto(demandeAdhesion);
        entity = adhesionService.saveAdhesion(updatedDto);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/demandeadhesion/{id}", pme.getIdPME())
                        .content(asJsonString(entity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(jsonPath("$.idDemande", is(demandeAdhesion.getIdDemande())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").value(demandeAdhesion.getIdDemande()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDemande").isNotEmpty());
    }

    @Test
    void delete_shouldDeletePme() throws Exception {
        mockMvc.perform(
                delete("/api/demandeadhesion/{id}", demandeAdhesion.getIdDemande())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void delete_withBadId_shouldReturnNotFound() throws Exception {
//        pme = pmeService.savePme(entityPme);
//        dto = DemandeAdhesionDTOTestData.defaultDTO();
//        dto.setIdPME(pme.getIdPME());
//        demandeAdhesion = adhesionService.saveAdhesion(dto);
        mockMvc.perform(get("/api/demandeadhesion/{id}", UUID.randomUUID().getMostSignificantBits())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
