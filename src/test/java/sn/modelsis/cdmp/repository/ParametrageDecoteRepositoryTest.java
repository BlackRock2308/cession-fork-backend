package sn.modelsis.cdmp.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.ParametrageDecoteDTOTest;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;
import sn.modelsis.cdmp.mappers.DecodeMapper;
import sn.modelsis.cdmp.repositories.ParametrageDecoteRepository;
import sn.modelsis.cdmp.services.ParametrageDecoteService;
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
public class ParametrageDecoteRepositoryTest extends RepositoryBaseTest {
    @Autowired
    @Valid
    ParametrageDecoteRepository decoteRepository;
    @Autowired
    @Valid
    DecodeMapper decodeMapper;


//    @Autowired
//    @Valid
//    private ParametrageDecoteService decoteService;

    ParametrageDecoteDTO dto;

    ParametrageDecote entity;

    @BeforeEach
    void setUp() {
        dto = ParametrageDecoteDTOTest.defaultDTO();
        entity = decodeMapper.asEntity(dto);
        //entity = SocieteDTOTestData.defaultEntity(entity);
        decoteRepository.deleteAll();
        entity = decoteRepository.save(entity);
    }

    @AfterEach
    public void destroyAll(){
        decoteRepository.deleteAll();
    }


    @Test
    @Rollback(value = false)
    void givenRepository_whenFindById_thenResult() {
        //dto = PmeDTOTestData.defaultDTO();
        Optional<ParametrageDecote> optional = decoteRepository.findById(entity.getIdDecote());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_save_shouldSaveDecote() {
        //dto =service.save(vm);
        entity = decoteRepository.save(entity);
        assertThat(entity)
                .isNotNull();
    }


    @Test
    void givenRepository_whenFindByBadId_thenResult(){
        Optional<ParametrageDecote> optional = decoteRepository.findById(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<ParametrageDecote> decoteList = decoteRepository.findAll();
        assertThat(decoteList).isNotEmpty();
    }

    @Test
    void givenRepository_whenFindByBorneInf_thenNotFound() {
        Optional<ParametrageDecote> optional = decoteRepository.findParametrageByBorneInf(entity.getBorneInf());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindBad_ByBorneInf_thenNotFound() {
        Optional<ParametrageDecote> optional = decoteRepository.findParametrageByBorneInf(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    void givenRepository_whenFindByBorneSup_thenNotFound() {
        Optional<ParametrageDecote> optional = decoteRepository.findParametrageByBorneSup(entity.getBorneSup());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindBad_ByBorneSup_thenNotFound() {
        Optional<ParametrageDecote> optional = decoteRepository.findParametrageByBorneSup(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        decoteRepository.delete(entity);
        Optional<ParametrageDecote> optional = decoteRepository.findById(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                ()->assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );

    }

}
