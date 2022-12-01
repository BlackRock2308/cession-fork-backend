package sn.modelsis.cdmp.services;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.ItemExistsException;
import sn.modelsis.cdmp.exceptions.ItemNotFoundException;
import sn.modelsis.cdmp.exceptions.NotFoundException;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.util.DtoConverter;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@SpringBootTest
@Transactional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PmeServiceTest extends ServiceBaseTest{

    @Autowired
    PmeRepository pmeRepository;

    @Autowired
    PmeService pmeService;

    Pme pme;
    Pme vm;
    static PmeDto dto;

    @BeforeEach
    void beforeAll(){
        log.info(" before all");
        vm = PmeDTOTestData.defaultEntity();
    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
    }

    @AfterEach
    void afterEach(){
        pmeRepository.deleteAll();
    }


    @Test
    @Rollback(value = false)
    void save_shouldSavePme() {
        pme = pmeService.savePme(vm);
        assertThat(pme)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }



    @Test
    void findById_shouldReturnResult() {
        pme = pmeRepository.save(vm);
        dto = DtoConverter.convertToDto(pme);
        final Optional<Pme> optional = pmeService.getPme(pme.getIdPME());
        assertThat(optional)
                .isNotNull()
                .isPresent();
        //.hasNoNullFieldsOrProperties();
    }


    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<Pme> optional = pmeService.getPme(UUID.randomUUID().getMostSignificantBits());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeletePme() {
        pme = pmeRepository.save(vm);
        long oldCount = pmeService.findAllPme().stream().count();
        pmeService.deletePme(pme.getIdPME());
        long newCount = pmeService.findAllPme().stream().count();
        assertThat(oldCount).isEqualTo(newCount+1);
    }


    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                CustomException.class,
                () ->pmeService.deletePme(UUID.randomUUID().getMostSignificantBits())
        );
    }


}
