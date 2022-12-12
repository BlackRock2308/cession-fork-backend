package sn.modelsis.cdmp.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.DemandeDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.util.DtoConverter;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Transactional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemandeServiceTest extends ServiceBaseTest{

    @Autowired
    DemandeRepository demandeRepository;
    @Autowired
    DemandeService demandeService;

    Demande demande;

    Demande vm;

    static DemandeDto dto;

    @BeforeEach
    void beforeAll(){
        log.info(" before all");
        dto = DemandeDTOTestData.defaultDTO();
        vm = DtoConverter.convertToEntity(dto);

    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
    }

    @AfterEach
    void afterEach(){
        demandeRepository.deleteAll();
    }



    @Test
    void findByBadId_shouldReturnNotFound() {
        final Optional<Demande> optional = demandeService.getDemande(UUID.randomUUID().getMostSignificantBits());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                EmptyResultDataAccessException.class,
                () ->demandeService.delete(UUID.randomUUID().getMostSignificantBits())
        );
    }
}
