package sn.modelsis.cdmp.services;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.DemandeCessionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.UtilisateurDTOTestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;
import java.util.List;
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

    @Autowired
    @Valid
    DemandeCessionService cessionService;

    static DemandeCession demandeCession;
    DemandeCessionDto dtoDemande;

    DemandeCession entityDemande;
    BonEngagement entityBE;

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    UtilisateurService utilisateurService;

    Utilisateur utilisateur;
    static UtilisateurDto dtoUser;
    Utilisateur vmUser;

    @BeforeEach
    void beforeAll(){
        log.info(" before all");
        vm = PmeDTOTestData.defaultEntity();

        utilisateurRepository.deleteAll();
        vmUser = UtilisateurDTOTestData.defaultEntity();
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
    }


    @Test
    void findById_shouldReturnResult() {
        pme = pmeRepository.save(vm);
        dto = DtoConverter.convertToDto(pme);
        final Optional<Pme> optional = pmeService.getPme(pme.getIdPME());
        assertThat(optional)
                .isNotNull()
                .isPresent();
    }


    @Test
    void findById_withBadId_ShouldReturnNotFound() {
        final Optional<Pme> optional = pmeService.getPme(UUID.randomUUID().getMostSignificantBits());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeletePme() {
        pme = pmeRepository.save(vm);
        long oldCount = pmeService.findAllPme().size();
        pmeService.deletePme(pme.getIdPME());
        long newCount = pmeService.findAllPme().size();
        assertThat(oldCount).isEqualTo(newCount+1);
    }


    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                CustomException.class,
                () ->pmeService.deletePme(UUID.randomUUID().getMostSignificantBits())
        );
    }

    @Test
    void findAll_shouldReturnResult(){
        vm = PmeDTOTestData.defaultEntity();

        pme = pmeService.savePme(vm);

        List<Pme> pmes = pmeService.findAllPme();
        assertThat((long) pmes.size()).isPositive();
    }

    @Test
    void findByUtilisateurId_shouldReturnResult() {

        utilisateur = utilisateurRepository.save(vmUser);
        dtoUser = DtoConverter.convertToDto(utilisateur);

        vm = PmeDTOTestData.defaultEntity();
        vm.setUtilisateurid(dtoUser.getIdUtilisateur());
        pme = pmeRepository.save(vm);
        dto = DtoConverter.convertToDto(pme);

        final Optional<Pme> optional = pmeService.getPmeByUtilisateur(pme.getUtilisateur().getIdUtilisateur());
        assertThat(optional)
                .isNotNull()
                .isPresent();
    }


}
