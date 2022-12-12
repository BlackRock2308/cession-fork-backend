package sn.modelsis.cdmp.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.DemandeDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.DemandeService;
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
public class DemandeRepositoryTest extends RepositoryBaseTest{

    @Autowired
    @Valid
    DemandeRepository demandeRepository;

    @Autowired
    @Valid
    DemandeService demandeService;
    DemandeDto dto;
    Demande entity;

    @Autowired
    @Valid
    PmeRepository pmeRepository;

    @Autowired
    @Valid
    PmeService pmeService;

    PmeDto dtoPme;
    Pme entityPme;


    @BeforeEach
    void setUp() {
        //Init for PME
        dtoPme = PmeDTOTestData.defaultDTO();
        entityPme = DtoConverter.convertToEntity(dtoPme);
        pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);

        dto = DemandeDTOTestData.defaultDTO();
        //dto.setPme(dtoPme);
        entity = DtoConverter.convertToEntity(dto);
        entity.setPme(entityPme);
        demandeRepository.deleteAll();
        entity = demandeService.save(entity);
    }

    @AfterEach
    public void destroyAll(){
        demandeRepository.deleteAll();
    }

//    @Test
//    @Rollback(value = false)
//    void givenRepository_whenFindById_thenResult() {
//        dto = DemandeDTOTestData.defaultDTO();
//        entity = DtoConverter.convertToEntity(dto);
//        entity = demandeService.save(entity);
//        Optional<Demande> optional = demandeRepository.findById(entity.getIdDemande());
//        Assertions.assertAll(
//                ()->  assertThat(option al).isNotNull(),
//                ()-> assertThat(optional).isPresent()
//        );
//    }

    @Test
    void givenRepository_whenFindBybadId_thenResult(){
        Optional<Demande> optional = demandeRepository.findById(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }


    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).isNotEmpty();
    }
}
