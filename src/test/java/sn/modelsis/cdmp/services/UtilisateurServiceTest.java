package sn.modelsis.cdmp.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.data.TestData;
import sn.modelsis.cdmp.data.UtilisateurDTOTestData;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.CreationComptePmeDto;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;
import sn.modelsis.cdmp.entitiesDtos.email.EmailMessageWithTemplate;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.util.DtoConverter;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@Transactional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtilisateurServiceTest {

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    PmeRepository pmeRepository;
    Utilisateur utilisateur;

    Utilisateur updatedUtilisateur;

    Utilisateur vm;

    Pme pme;

    static UtilisateurDto dto;

    @BeforeEach
    void beforeAll(){
        log.info(" before all");
        utilisateurRepository.deleteAll();
        vm = UtilisateurDTOTestData.defaultEntity();
    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
    }

    @AfterEach
    void afterEach(){
        pmeRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void save_shouldSaveUtilisateur() {
        utilisateur = utilisateurService.save(vm);
        assertThat(utilisateur)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }


    @Test
    void findById_shouldReturnResult() {
        //given
        utilisateur = utilisateurRepository.save(vm);
        dto = DtoConverter.convertToDto(utilisateur);
        //when
        final Optional<Utilisateur> optional = Optional.ofNullable(utilisateurService.findById(utilisateur.getIdUtilisateur()));
        //Then
        assertThat(optional)
                .isNotNull()
                .isPresent();
    }



    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<Utilisateur> optional = Optional.ofNullable(utilisateurService.findById(UUID.randomUUID().getMostSignificantBits()));
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteUtilisateur() {
        pmeRepository.deleteAll();
        utilisateur = utilisateurRepository.save(vm);
        long oldCount = utilisateurService.getAll().size();
        utilisateurService.delete(utilisateur.getIdUtilisateur());
        long newCount = utilisateurService.getAll().size();
        assertThat(oldCount).isEqualTo(newCount+1);
    }

    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                EmptyResultDataAccessException.class,
                () ->utilisateurService.delete(UUID.randomUUID().getMostSignificantBits())
        );
    }

    @DisplayName("JUnit test for getAll method")
    @Test
    void findAll_shouldReturnResult(){
        vm = UtilisateurDTOTestData.defaultEntity();

        utilisateur = utilisateurService.save(vm);

        List<Utilisateur> users = utilisateurService.getAll();
        assertThat((long) users.size()).isPositive();
    }

    @Test
    @Rollback(value = false)
    void signerConvention_shouldSignConvention() {
        utilisateur = utilisateurService.save(vm);
        utilisateurService.signerConvention(utilisateur.getIdUtilisateur(), utilisateur.getCodePin());
        assertThat(utilisateur)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }


    @Test
    @Rollback(value = false)
    void updatePassword_Pme_shouldReturnResult() {
        utilisateur = utilisateurService.save(vm);
        updatedUtilisateur = utilisateurService.updatePassword(utilisateur);
        Assertions.assertAll(
                ()-> assertThat(utilisateur).isNotNull(),
                ()-> assertThat(!utilisateur.isUpdatePassword())

        );
    }
    @Test
    @Rollback(value = false)
    void updateCodePin_Pme_shouldReturnResult() {
        utilisateur = utilisateurService.save(vm);
        utilisateur.setCodePin(TestData.Update.codePin);
        updatedUtilisateur = utilisateurService.updateCodePin(utilisateur);
        Assertions.assertAll(
                ()-> assertThat(utilisateur).isNotNull(),
                ()-> assertThat(!utilisateur.isUpdateCodePin())

        );
    }

//    @Test
//    @Rollback(value = false)
//    void setRole_Pme_shouldReturnResult() {
//        utilisateur = utilisateurService.save(vm);
//        utilisateur.setCodePin(TestData.Update.codePin);
//       utilisateurService.forgetPassword(utilisateur.getEmail());
//        Assertions.assertAll(
//                ()-> assertThat(utilisateur).isNotNull()
//
//        );
//    }


//    @Test
//    @Rollback(value = false)
//    void updatedRole_Pme_shouldReturnResult() {
//        utilisateur = utilisateurService.save(vm);
//        utilisateurService.updateRoles(utilisateur);
//        Assertions.assertAll(
//                ()-> assertThat(utilisateur).isNotNull(),
//                ()-> assertThat(utilisateur.getRoles()).isNotEmpty()
//
//        );
//    }


}
