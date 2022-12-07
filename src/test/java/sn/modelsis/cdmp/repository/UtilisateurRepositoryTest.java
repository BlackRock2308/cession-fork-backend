package sn.modelsis.cdmp.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.UtilisateurDTOTestData;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.services.UtilisateurService;
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
public class UtilisateurRepositoryTest extends RepositoryBaseTest{
    @Autowired
    @Valid
    UtilisateurRepository utilisateurRepository;

    @Autowired
    @Valid
    PmeRepository pmeRepository;

    @Autowired
    @Valid
    UtilisateurService utilisateurService;

    UtilisateurDto dto;

    Utilisateur entity;

    @BeforeEach
    void setUp() {
        pmeRepository.deleteAll();
        dto = UtilisateurDTOTestData.defaultDTO();
        entity = DtoConverter.convertToEntity(dto);
        //entity = SocieteDTOTestData.defaultEntity(entity);
        utilisateurRepository.deleteAll();
        entity = utilisateurService.save(entity);
    }

    @AfterEach
    public void destroyAll(){
        pmeRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }


    @Test
    @Rollback(value = false)
    void givenRepository_whenFindById_thenResult() {
        dto = UtilisateurDTOTestData.defaultDTO();
        Optional<Utilisateur> optional = utilisateurRepository.findById(entity.getIdUtilisateur());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByEmail_thenResult() {
        dto = UtilisateurDTOTestData.defaultDTO();
        Optional<Utilisateur> optional = Optional.ofNullable(utilisateurRepository.findUtilisateurByEmail(entity.getEmail()));
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    void givenRepository_save_shouldSaveUtilisateur() {
        //dto =service.save(vm);
        entity = utilisateurRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void givenRepository_whenFindBybadId_thenResult(){
        Optional<Utilisateur> optional = utilisateurRepository.findById(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<Utilisateur> userList = utilisateurRepository.findAll();
        assertThat(userList).isNotEmpty();
    }

    @Test
    void givenRepository_whenFindByBadEmail_thenNotFound() {
        Optional<Utilisateur> optional = Optional.ofNullable(utilisateurRepository.findUtilisateurByEmail(UUID.randomUUID().toString()));
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        utilisateurRepository.delete(entity);
        Optional<Utilisateur> optional = utilisateurRepository.findById(entity.getIdUtilisateur());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );

    }


}
