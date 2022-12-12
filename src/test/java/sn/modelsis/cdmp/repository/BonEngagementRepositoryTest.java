package sn.modelsis.cdmp.repository;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.services.BonEngagementService;
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
public class BonEngagementRepositoryTest extends RepositoryBaseTest{

    @Autowired
    @Valid
    BonEngagementRepository bonEngagementRepository;

    @Autowired
    @Valid
    BonEngagementService bonEngagementService;

    BonEngagementDto dto;

    BonEngagement entity;


    @BeforeEach
    void setUp() {
        dto = BonEngagementDTOTestData.defaultDTO();
        entity = DtoConverter.convertToEntity(dto);
        //entity = SocieteDTOTestData.defaultEntity(entity);
        bonEngagementRepository.deleteAll();
        entity = bonEngagementService.save(entity);
    }

    @AfterEach
    public void destroyAll(){
        bonEngagementRepository.deleteAll();
    }


    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByNomMarche_thenResult() {
        dto = BonEngagementDTOTestData.defaultDTO();
        Optional<BonEngagement> optional = bonEngagementRepository.findByNomMarche(entity.getNomMarche());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_save_shouldSaveBonEngagement() {

        entity = bonEngagementRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void givenRepository_whenFindById_thenResult(){
        Optional<BonEngagement> optional = bonEngagementRepository.findById(entity.getIdBonEngagement());
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindBybadId_thenResult(){
        Optional<BonEngagement> optional = bonEngagementRepository.findById(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<BonEngagement> beList = bonEngagementRepository.findAll();
        assertThat(beList).isNotEmpty();
    }

    @Test
    void givenRepository_whenFindByBadNomMarche_thenNotFound() {
        Optional<BonEngagement> optional = bonEngagementRepository.findByNomMarche(UUID.randomUUID().toString());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    void givenRepository_whenFindByMontantCreance_thenResult() {
        Optional<BonEngagement> optional = bonEngagementRepository.findByMontantCreance(entity.getMontantCreance());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindBy_BadMontantCreance_thenResult() {
        Optional<BonEngagement> optional = bonEngagementRepository.findByMontantCreance(RandomUtils.nextDouble());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        bonEngagementRepository.delete(entity);
        Optional<BonEngagement> optional = bonEngagementRepository.findById(entity.getIdBonEngagement());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }




}
