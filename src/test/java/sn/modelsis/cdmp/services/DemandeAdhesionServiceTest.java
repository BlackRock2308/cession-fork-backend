package sn.modelsis.cdmp.services;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.DemandeAdhesionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.repositories.DemandeAdhesionRepository;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemandeAdhesionServiceTest extends ServiceBaseTest{

    DemandeAdhesionDto dto;
    DemandeAdhesion entity;
    private static DemandeAdhesion demandeAdhesion;


    @Autowired
    @Valid
    DemandeAdhesionRepository adhesionRepository;
    @Autowired
    @Valid
    DemandeAdhesionService adhesionService;
    @Autowired
    @Valid
    PmeRepository pmeRepository;
    @Autowired
    @Valid
    PmeService pmeService;

    PmeDto dtoPme;
    Pme entityPme;
    private static Pme pme;



    @BeforeEach
    void beforeAll(){
        //Init for PME
        dtoPme = PmeDTOTestData.defaultDTO();
        entityPme = DtoConverter.convertToEntity(dtoPme);
        pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);

        //init for DemandeAdhesion which just required the idPME
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(entityPme.getIdPME());

        adhesionRepository.deleteAll();
        entity = adhesionService.saveAdhesion(dto);
    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
    }

    @AfterEach
    void afterEach(){
        pmeRepository.deleteAll();
        adhesionRepository.deleteAll();
    }


    @Test
    @Rollback(value = false)
    void save_shouldSaveDemandeAdhesion() {
        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());
        demandeAdhesion = adhesionService.saveAdhesion(dto);
        assertThat(demandeAdhesion)
                .isNotNull();
    }

    @Test
    void findById_shouldReturnResult() {
        pme = pmeService.savePme(entityPme);
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        dto.setIdPME(pme.getIdPME());
        demandeAdhesion = adhesionService.saveAdhesion(dto);
        final Optional<DemandeAdhesionDto> optional = adhesionService.findById(demandeAdhesion.getIdDemande());
        assertThat(optional)
                .isNotNull()
                .isPresent();
        //.hasNoNullFieldsOrProperties();
    }

}
