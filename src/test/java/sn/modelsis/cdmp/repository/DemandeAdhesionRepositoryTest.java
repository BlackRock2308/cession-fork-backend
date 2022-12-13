package sn.modelsis.cdmp.repository;


import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.DemandeAdhesionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.mappers.DemandeAdhesionMapper;
import sn.modelsis.cdmp.repositories.DemandeAdhesionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemandeAdhesionRepositoryTest extends RepositoryBaseTest{

    @Autowired
    @Valid
    DemandeAdhesionRepository adhesionRepository;
    @Autowired
    @Valid
    DemandeAdhesionService adhesionService;

    DemandeAdhesionDto dto;
    DemandeAdhesion entity;

    @Autowired
    @Valid
    PmeRepository pmeRepository;

    @Autowired
    @Valid
    PmeService pmeService;

    PmeDto dtoPme;
    Pme entityPme;

    private DemandeAdhesionMapper adhesionMapper;



    @BeforeEach
    void setUp() {
        adhesionRepository.deleteAll();
        pmeRepository.deleteAll();

        //Init for PME
        dtoPme = PmeDTOTestData.defaultDTO();
        entityPme = DtoConverter.convertToEntity(dtoPme);
        entityPme = pmeService.savePme(entityPme);

        //init for DemandeAdhesion which just required the idPME
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(entityPme.getIdPME());
        entity = adhesionService.saveAdhesion(dto);
    }

    @AfterEach
    public void destroyAll(){
        adhesionRepository.deleteAll();
        pmeRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindById_thenResult() {
        Optional<DemandeAdhesion> optional = adhesionRepository.findById(entity.getIdDemande());
        Assertions.assertAll(
                ()-> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindBybadId_thenResult(){
        Optional<DemandeAdhesion> optional = adhesionRepository.findById(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<DemandeAdhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).isNotEmpty();
    }


}
