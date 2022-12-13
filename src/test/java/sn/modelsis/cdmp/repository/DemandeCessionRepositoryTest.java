package sn.modelsis.cdmp.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.CdmpApplicationTests;
import sn.modelsis.cdmp.data.*;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.entitiesDtos.StatutDto;
import sn.modelsis.cdmp.mappers.DemandeAdhesionMapper;
import sn.modelsis.cdmp.mappers.DemandeCessionMapper;
import sn.modelsis.cdmp.mappers.PmeMapper;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.services.StatutService;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest()
public class DemandeCessionRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    DemandeCessionRepository cessionRepository;

    @Autowired
    @Valid
    DemandeCessionService cessionService;
    DemandeCessionDto dto;
    DemandeCession entity;
    private DemandeCessionMapper cessionMapper;

    @Autowired
    @Valid
    PmeRepository pmeRepository;

    @Autowired
    @Valid
    PmeService pmeService;
//    @Autowired
//    @Valid
//    PmeMapper pmeMapper;

    PmeDto dtoPme;
    Pme entityPme;

    @Autowired
    @Valid
    BonEngagementRepository bonEngagementRepository;

    @Autowired
    @Valid
    BonEngagementService bonEngagementService;

    BonEngagementDto dtoBE;
    BonEngagement entityBE;
    @Autowired
    @Valid
    StatutRepository statutRepository;
    @Autowired
    @Valid
    StatutService statutService;

    Statut entityStatut;

    StatutDto dtoStatut;

    @BeforeEach
    void setUp() {
        //Init for Statut
        dtoStatut = StatutDTOTestData.defaultDTO();
        entityStatut = DtoConverter.convertToEntity(dtoStatut);
        statutRepository.deleteAll();
        entityStatut = statutService.save(entityStatut);

        Statut statut = statutRepository.findByLibelle("SOUMISE");

        //Init for PME
        dtoPme = PmeDTOTestData.defaultDTO();
        entityPme = DtoConverter.convertToEntity(dtoPme);
        pmeRepository.deleteAll();
        entityPme = pmeService.savePme(entityPme);

        //Init for BonEngagement
        dtoBE = BonEngagementDTOTestData.defaultDTO();
        entityBE = DtoConverter.convertToEntity(dtoBE);
        bonEngagementRepository.deleteAll();
        entityBE= bonEngagementService.save(entityBE);

        //init for DemandeAdhesion which just required the idPME
        dto = DemandeCessionDTOTestData.defaultDTO();
        dto.setBonEngagement(DtoConverter.convertToDto(entityBE));
        dto.setPme(DtoConverter.convertToDto(entityPme));
        dto.setStatut(DtoConverter.convertToDto(statut));
        //cessionRepository.deleteAll();
        entity = cessionService.saveCession(DtoConverter.convertToEntity(dto));
    }

    @AfterEach
    public void destroyAll(){
        bonEngagementRepository.deleteAll();
        pmeRepository.deleteAll();
        statutRepository.deleteAll();
        cessionRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindById_thenResult() {
        Optional<DemandeCession> optional = cessionRepository.findById(entity.getIdDemande());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_whenFindBybadId_thenResult(){
        Optional<DemandeCession> optional = cessionRepository.findById(UUID.randomUUID().getMostSignificantBits());

        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }


    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByDemandeId_thenResult() {
        Optional<DemandeCession> optional = Optional.ofNullable(cessionRepository.findByDemandeId(entity.getIdDemande()));
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByBadDemandeId_thenResult() {
        Optional<DemandeCession> optional = Optional.ofNullable(cessionRepository.findByDemandeId(UUID.randomUUID().getMostSignificantBits()));
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<DemandeCession> cessionList = cessionRepository.findAll();
        assertThat(cessionList).isNotEmpty();
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByNomMarche_thenResult() {
        List<DemandeCession> optionalList = cessionRepository.searchCreanceByNomMarche(entity.getBonEngagement().getNomMarche());
        Assertions.assertAll(
                ()->  assertThat(optionalList).isNotNull(),
                ()-> assertThat(optionalList).hasSizeGreaterThan(0)
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByBadNomMarche_thenResult() {
        List<DemandeCession> optionalList = cessionRepository.searchCreanceByNomMarche(UUID.randomUUID().toString());
        Assertions.assertAll(
                ()->  assertThat(optionalList).isNotNull(),
                ()-> assertThat(optionalList).size().isZero()
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByLibelleStatut_thenResult() {
        List<DemandeCession> optionalList = cessionRepository
                .findByLibelleStatutDemande(
                        "SOUMISE"
                );
        Assertions.assertAll(
                ()->  assertThat(optionalList).isNotNull()
//                 ()-> assertThat(optionalList).size().isNotZero()
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByRaisonSociale_thenResult() {
        List<DemandeCession> optionalList = cessionRepository
                .searchCreanceByRaisonSocial(entity.getPme().getRaisonSocial());
        Assertions.assertAll(
                ()->  assertThat(optionalList).isNotNull(),
               ()-> assertThat(optionalList).size().isNotZero()
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByBadRaisonSociale_thenResult() {
        List<DemandeCession> optionalList = cessionRepository
                .searchCreanceByRaisonSocial(UUID.randomUUID().toString());
        Assertions.assertAll(
                ()->  assertThat(optionalList).isNotNull(),
                ()-> assertThat(optionalList).size().isZero()
        );
    }




    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByNo_thenResult() {
        List<DemandeCession> optionalList = cessionRepository
                .findDemandeCessionByMultiParams(entity.getBonEngagement().getReference(),
                                                entity.getNumeroDemande(),
                                                entity.getBonEngagement().getNomMarche(),
                                                "SOUMISE",
                                                entity.getDateDemandeCession(),
                                                entity.getDateDemandeCession()
                        );
        Assertions.assertAll(
                ()->  assertThat(optionalList).isNotNull(),
                ()-> assertThat(optionalList).hasSizeGreaterThan(0)
        );
    }

}
