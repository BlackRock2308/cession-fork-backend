package sn.modelsis.cdmp.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.ParametrageDecoteDTOTest;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.mappers.DecodeMapper;
import sn.modelsis.cdmp.repositories.ParametrageDecoteRepository;
import sn.modelsis.cdmp.services.ParametrageDecoteService;

import sn.modelsis.cdmp.util.DtoConverter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Transactional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParametrageDecoteServiceTest extends ServiceBaseTest{

    @Autowired
    ParametrageDecoteRepository decoteRepository;

//    @Autowired
//    ParametrageDecoteService decoteService;

    ParametrageDecote decote;
    ParametrageDecote vm;
    static ParametrageDecoteDTO dto;
    @Autowired
    DecodeMapper decodeMapper;


    @BeforeEach
    void beforeAll(){
        log.info(" before all");
        vm = ParametrageDecoteDTOTest.defaultEntity();
    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
    }

    @AfterEach
    void afterEach(){
        decoteRepository.deleteAll();
    }


    @Test
    @Rollback(value = false)
    void save_shouldSaveParametrageDecote() {
        decote = decoteRepository.save(vm);
        assertThat(decote)
                .isNotNull();
    }


//    @Test
//    @Rollback(value = false)
//    void update_shouldUpdateParametrageDecote() {
//        ParametrageDecoteDTO updatedValue = ParametrageDecoteDTOTest.updateDTO();
//        decote = decoteRepository.save(vm);
//
//        Optional<ParametrageDecote> optional = decoteRepository.findById(decote.getIdDecote());
//
//        assertThat(decote)
//                .isNotNull();
//    }

    @Test
    void findById_shouldReturnResult() {
        decote = decoteRepository.save(vm);
        dto = decodeMapper.asDTO(decote);
        final Optional<ParametrageDecote> optional = decoteRepository.findById(decote.getIdDecote());
        assertThat(optional)
                .isNotNull()
                .isPresent();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<ParametrageDecote> optional = decoteRepository.findById(UUID.randomUUID().getMostSignificantBits());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteParametrageDecote() {
        decote = decoteRepository.save(vm);
        long oldCount = decoteRepository.findAll().size();
        decoteRepository.deleteById(decote.getIdDecote());
        long newCount = decoteRepository.findAll().size();
        assertThat(oldCount).isEqualTo(newCount+1);
    }

    @Test
    void delete_withBadId_ShouldThrowException() {
        decote = decoteRepository.save(vm);
        assertThrows(
                CustomException.class,
                () ->decoteRepository.deleteById(UUID.randomUUID().getMostSignificantBits())
        );
    }

    @Test
    void findAll_shouldReturnResult(){

        decote = decoteRepository.save(vm);

        List<ParametrageDecote> decoteList = decoteRepository.findAll();
        assertThat((long) decoteList.size()).isPositive();
    }

}
