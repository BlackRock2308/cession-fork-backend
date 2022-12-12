package sn.modelsis.cdmp.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.data.RoleDTOTestData;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.repositories.RoleRepository;
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
public class RoleRepositoryTest extends RepositoryBaseTest{

    @Autowired
    @Valid
    RoleRepository roleRepository;


    Role entity;

    Role role;


    @BeforeEach
    void setUp() {
        role = RoleDTOTestData.defaultEntity();
        //entity = SocieteDTOTestData.defaultEntity(entity);
        roleRepository.deleteAll();
        entity = roleRepository.save(role);
    }

    @AfterEach
    public void destroyAll(){
        roleRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindById_thenResult() {
        Optional<Role> optional = roleRepository.findById(entity.getId());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByBadId_thenNotFound() {
        Optional<Role> optional = roleRepository.findById(UUID.randomUUID().getMostSignificantBits());
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByLibelle_thenResult() {
        Optional<Role> optional = Optional.ofNullable(roleRepository.findByLibelle(entity.getLibelle()));
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByBadLibelle_thenResult() {
        Optional<Role> optional = Optional.ofNullable(roleRepository.findByLibelle(UUID.randomUUID().toString()));
        Assertions.assertAll(
                ()->  assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isPresent()
        );
    }


    @Test
    void givenRepository_save_shouldSaveRole() {
        entity = roleRepository.save(role);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).isNotEmpty();
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        roleRepository.delete(entity);
        Optional<Role> optional = Optional.ofNullable(roleRepository.findByLibelle(UUID.randomUUID().toString()));
        Assertions.assertAll(
                () -> assertThat(optional).isNotNull(),
                ()-> assertThat(optional).isNotPresent()
        );
    }
}
