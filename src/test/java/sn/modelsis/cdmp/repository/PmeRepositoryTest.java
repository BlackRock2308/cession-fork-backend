package sn.modelsis.cdmp.repository;


import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;
import java.util.List;

import javax.validation.Valid;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest()
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PmeRepositoryTest extends RepositoryBaseTest {
    @Autowired
    @Valid
    PmeRepository pmeRepository;

    @Autowired
    @Valid
    PmeService pmeService;

    PmeDto dto;
    Pme entity;


    @BeforeEach
    void setUp() {
        dto = PmeDTOTestData.defaultDTO();
        entity = DtoConverter.convertToEntity(dto);
        //entity = SocieteDTOTestData.defaultEntity(entity);
        pmeRepository.deleteAll();
        entity = pmeService.savePme(entity);
    }

    @AfterEach
    public void destroyAll(){
        pmeRepository.deleteAll();
    }


    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByName_thenResult() {
        dto = PmeDTOTestData.defaultDTO();
        Optional<Pme> optional = pmeRepository.findByNinea(entity.getNinea());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_save_shouldSavePme() {
        //dto =service.save(vm);
        entity = pmeRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void givenRepository_whenFindById_thenResult(){
        Optional<Pme> optional = pmeRepository.findById(entity.getIdPME());
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindBybadId_thenResult(){
        Optional<Pme> optional = pmeRepository.findById(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).isNotEmpty();
    }

    @Test
    void givenRepository_whenFindByBadRccm_thenNotFound() {
        Optional<Pme> optional = pmeRepository.findByRccm(UUID.randomUUID().toString());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    void givenRepository_whenFindByRccm_thenResult() {
        Optional<Pme> optional = pmeRepository.findByRccm(entity.getRccm());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }


    @Test
    void givenRepository_whenFindByEmail_thenResult() {
        Optional<Pme> optional = Optional.ofNullable(pmeRepository.findByMail(entity.getEmail()));
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindByBadEmail_thenNotFound() {
        Optional<Pme> optional = Optional.ofNullable(pmeRepository.findByMail(UUID.randomUUID().toString()));
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }


    @Test
    void givenRepository_whenFindByPhone_thenResult() {
        Optional<Pme> optional = pmeRepository.findByPhone(entity.getTelephonePME());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindByBadPhone_thenNotFound() {
        Optional<Pme> optional = pmeRepository.findByPhone(UUID.randomUUID().toString());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }


    @Test
    void givenRepository_whenFindByNinea_thenResult() {
        Optional<Pme> optional = pmeRepository.findByNinea(entity.getNinea());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindByBadNinea_thenNotFound() {
        Optional<Pme> optional = pmeRepository.findByNinea(UUID.randomUUID().toString());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );

    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        pmeRepository.delete(entity);
        //entity = pmeRepository.saveAndFlush(entity);
        Optional<Pme> optional = pmeRepository.findByNinea(entity.getNinea());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

}
