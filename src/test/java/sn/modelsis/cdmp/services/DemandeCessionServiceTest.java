package sn.modelsis.cdmp.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.*;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.util.DtoConverter;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest()
public class DemandeCessionServiceTest extends ServiceBaseTest{

    DemandeCessionDto dto;

    DemandeCession entity;

    static DemandeCession demandeCession;

    @Autowired
    @Valid
    DemandeCessionRepository cessionRepository;
    @Autowired
    @Valid
    DemandeCessionService cessionService;

    @Autowired
    @Valid
    PmeRepository pmeRepository;
    @Autowired
    @Valid
    PmeService pmeService;

    PmeDto dtoPme;
    Pme entityPme;
    static Pme pme;
    @Autowired
    @Valid
    BonEngagementRepository bonEngagementRepository;
    @Autowired
    @Valid
    BonEngagementService bonEngagementService;

    BonEngagementDto dtoBE;
    BonEngagement entityBE;

    static BonEngagement bonEngagement;

    @Autowired
    @Valid
    StatutRepository statutRepository;


    @BeforeEach
    void setUp() {

        statutRepository.save(new Statut(1L,"REJETEE","REJETEE"));

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
//        dto.setStatut(DtoConverter.convertToDto(statut));
        //cessionRepository.deleteAll();
//        entity = cessionService.saveCession(DtoConverter.convertToEntity(dto));
    }


    @AfterEach
    public void destroyAll(){
        cessionRepository.deleteAll();
        bonEngagementRepository.deleteAll();
        pmeRepository.deleteAll();
        statutRepository.deleteAll();

    }

    @Test
    @Rollback(value = false)
    void save_shouldSaveDemandeCession() {
//        pme = pmeService.savePme(entityPme);
//        dto = DemandeCessionDTOTestData.defaultDTO();
//        dto.setPme(DtoConverter.convertToDto(pme));
//        dtoBE = BonEngagementDTOTestData.defaultDTO();
//        //entityBE = DtoConverter.convertToEntity(dtoBE);
//        dto.setBonEngagement(dtoBE);

        entity = DtoConverter.convertToEntity(dto);

        demandeCession = cessionService.saveCession(entity);
        assertThat(demandeCession)
                .isNotNull();
    }

    @Test
    void findByIdDemande_shouldReturnResult() {

        entity = DtoConverter.convertToEntity(dto);

        demandeCession = cessionService.saveCession(entity);
        final Optional<DemandeCession> optional = cessionService.findByIdDemande(demandeCession.getIdDemande());
        assertThat(optional)
                .isNotNull()
                .isPresent();
    }

    @Test
    void findByBadIdDemande_shouldReturnResult() {

        entity = DtoConverter.convertToEntity(dto);

        demandeCession = cessionService.saveCession(entity);
        final Optional<DemandeCession> optional = cessionService.findByIdDemande(UUID.randomUUID().getMostSignificantBits());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void findByDemandeId_shouldReturnResult() {
        entity = DtoConverter.convertToEntity(dto);

        demandeCession = cessionService.saveCession(entity);
        final Optional<DemandeCessionDto> optional = cessionService.getDemandeCession(demandeCession.getIdDemande());
        assertThat(optional)
                .isNotNull()
                .isPresent();
    }

    @Test
    void findByBadDemandeId_shouldReturnResult() {
        entity = DtoConverter.convertToEntity(dto);

        demandeCession = cessionService.saveCession(entity);
        final Optional<DemandeCessionDto> optional = cessionService.getDemandeCession(UUID.randomUUID().getMostSignificantBits());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void rejeterRecevabilite_shouldReturnResult() {
        entity = DtoConverter.convertToEntity(dto);
        demandeCession = cessionService.saveCession(entity);

        demandeCession = cessionService.rejeterRecevabilite(demandeCession.getIdDemande());

        final Optional<DemandeCessionDto> optional = cessionService.getDemandeCession(demandeCession.getIdDemande());
        assertThat(optional)
                .isNotNull()
                .isPresent();
//        assertThat(optional.get().getStatut().getLibelle()).isEqualTo("REJETEE");

    }

    @Test
    void validerRecevabilite_shouldReturnResult() {
        entity = DtoConverter.convertToEntity(dto);
        demandeCession = cessionService.saveCession(entity);

        demandeCession = cessionService.validerRecevabilite(demandeCession.getIdDemande());

        final Optional<DemandeCessionDto> optional = cessionService.getDemandeCession(demandeCession.getIdDemande());
        assertThat(optional)
                .isNotNull()
                .isPresent();
//        assertThat(optional.get().getStatut().getLibelle()).isEqualTo("REJETEE");

    }

    @Test
    void analyseDemandeCessionRisque_shouldReturnResult() {
        entity = DtoConverter.convertToEntity(dto);
        demandeCession = cessionService.saveCession(entity);

        demandeCession = cessionService.analyseDemandeCessionRisque(demandeCession.getIdDemande());

        final Optional<DemandeCessionDto> optional = cessionService.getDemandeCession(demandeCession.getIdDemande());
        assertThat(optional)
                .isNotNull()
                .isPresent();
//        assertThat(optional.get().getStatut().getLibelle()).isEqualTo("REJETEE");

    }

    @Test
    void analyseDemandeCessionNonRisque_shouldReturnResult() {
        entity = DtoConverter.convertToEntity(dto);
        demandeCession = cessionService.saveCession(entity);

        demandeCession = cessionService.analyseDemandeCessionNonRisque(demandeCession.getIdDemande());

        final Optional<DemandeCessionDto> optional = cessionService.getDemandeCession(demandeCession.getIdDemande());
        assertThat(optional)
                .isNotNull()
                .isPresent();
//        assertThat(optional.get().getStatut().getLibelle()).isEqualTo("REJETEE");

    }
//
//    @Test
//    void findAllDemandeRejetee_shouldReturnResult() {
//        entity = DtoConverter.convertToEntity(dto);
//        Statut statut = statutRepository.findByLibelle("REJETEE");
//        entity.setStatut(statut);
//        demandeCession = cessionService.saveCession(entity);
//
//        List<DemandeCession> demandeCessionList = cessionService.findAllDemandeRejetee();
//
//        assertThat(demandeCessionList)
//                .isNotNull()
//                .size().isZero();
//    }


    @Test
    void findAllDemandeAcceptee_shouldReturnResult() {
        statutRepository.save(new Statut(2L,"RECEVABLE","RECEVABLE"));

        entity = DtoConverter.convertToEntity(dto);
        Statut statut = statutRepository.findByLibelle("RECEVABLE");
        entity.setStatut(statut);
        demandeCession = cessionService.saveCession(entity);

        List<DemandeCession> demandeCessionList = cessionService.findAllDemandeAcceptee();

        assertThat(demandeCessionList)
                .isNotNull()
                .size().isNotZero();
    }





    @Test
    void findAll_shouldReturnResult(){
        entity = DtoConverter.convertToEntity(dto);
        demandeCession = cessionService.saveCession(entity);

        List<DemandeCession> demandeCessionList = cessionRepository.findAll();
        assertThat((long) demandeCessionList.size()).isPositive();
    }

    @Test
    void delete_shouldDeleteDemandeCession() {
        entity = DtoConverter.convertToEntity(dto);
        demandeCession = cessionService.saveCession(entity);

        long oldCount = cessionRepository.findAll().size();
        cessionRepository.deleteById(demandeCession.getIdDemande());
        long newCount = cessionRepository.findAll().size();
        assertThat(oldCount).isEqualTo(newCount+1);
    }

    @Test
    void findAllPmeDemandes_shouldReturnResult(){
        entity = DtoConverter.convertToEntity(dto);
        demandeCession = cessionService.saveCession(entity);

        List<DemandeCession> demandeCessionList = cessionService.findAllPMEDemandes(demandeCession.getPme().getIdPME());
        assertThat((long) demandeCessionList.size()).isPositive();
    }

    @Test
    void signerConventionPME_shouldReturnResult(){
        statutRepository.save(new Statut(3L,"CONVENTION_SIGNEE_PAR_PME","CONVENTION_SIGNEE_PAR_PME"));

        entity = DtoConverter.convertToEntity(dto);
        demandeCession = cessionService.saveCession(entity);

        cessionService.signerConventionPME(demandeCession.getIdDemande());
        assertThat(demandeCession.getStatut()).isNotNull();
    }



}
