package sn.modelsis.cdmp.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.ItemNotFoundException;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.util.DtoConverter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@SpringBootTest()
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BonEngagementServiceTest extends ServiceBaseTest{

    @Autowired
    BonEngagementRepository bonEngagementRepository;
    @Autowired
    BonEngagementService bonEngagementService;

    BonEngagement bonEngagement;

    BonEngagement vm;

    static BonEngagementDto dto;

    @BeforeEach
    void beforeAll(){
        log.info(" before all");
        vm = BonEngagementDTOTestData.defaultEntity();
    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
    }

    @AfterEach
    void afterEach(){
        bonEngagementRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void save_shouldSaveBonEngagement() {
        bonEngagement = bonEngagementService.save(vm);
        assertThat(bonEngagement)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_shouldReturnResult() {
        bonEngagement = bonEngagementRepository.save(vm);
        dto = DtoConverter.convertToDto(bonEngagement);
        final Optional<BonEngagement> optional = bonEngagementService.getBonEngagement(bonEngagement.getIdBonEngagement());
        assertThat(optional)
                .isNotNull()
                .isPresent();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void delete_shouldDeleteBonEngagement() {
        bonEngagement = bonEngagementRepository.save(vm);
        long oldCount = bonEngagementService.findAll().size();
        bonEngagementService.delete(bonEngagement.getIdBonEngagement());
        long newCount = bonEngagementService.findAll().size();
        assertThat(oldCount).isEqualTo(newCount+1);
    }

    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                ItemNotFoundException.class,
                () ->bonEngagementService.delete(UUID.randomUUID().getMostSignificantBits())
        );
    }

    @Test
    void findAll_shouldReturnResult(){
        vm = BonEngagementDTOTestData.defaultEntity();

        bonEngagement = bonEngagementService.save(vm);

        List<BonEngagement> bes = bonEngagementService.findAll();
        assertThat((long) bes.size()).isPositive();
    }
}
