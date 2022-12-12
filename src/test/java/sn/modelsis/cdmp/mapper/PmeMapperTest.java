package sn.modelsis.cdmp.mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.util.DtoConverter;


import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
class PmeMapperTest  extends MapperBaseTest{

    Pme entity;
    PmeDto dto;


    @BeforeEach
    void setUp() {
        dto = PmeDTOTestData.defaultDTO();
        entity = PmeDTOTestData.defaultEntity();
    }

    @Test
    void toEntity(){
        assertThat(entity).isNotNull();
        assertThat(entity.getIdPME()).isEqualTo(dto.getIdPME());
        assertThat(entity.getNomRepresentant()).isEqualTo(dto.getNomRepresentant());
        assertThat(entity.getPrenomRepresentant()).isEqualTo(dto.getPrenomRepresentant());
        assertThat(entity.getAdressePME()).isEqualTo(dto.getAdressePME());
        assertThat(entity.getActivitePrincipale()).isEqualTo(dto.getActivitePrincipale());
        assertThat(entity.getCniRepresentant()).isEqualTo(dto.getCniRepresentant());
        assertThat(entity.getEmail()).isEqualTo(dto.getEmail());
        assertThat(entity.getCodePin()).isEqualTo(dto.getCodePin());
        assertThat(entity.getNinea()).isEqualTo(dto.getNinea());
        assertThat(entity.getActivitePrincipale()).isEqualTo(dto.getActivitePrincipale());
        assertThat(entity.getRaisonSocial()).isEqualTo(dto.getRaisonSocial());
        assertThat(entity.isAtd()).isEqualTo(dto.isAtd());
        assertThat(entity.getIsactive()).isEqualTo(dto.isIsactive());
        assertThat(entity.isNantissement()).isEqualTo(dto.isNantissement());

    }


    @Test
    void toDTO(){
        assertThat(dto).isNotNull();
        assertThat(dto.getIdPME()).isEqualTo(entity.getIdPME());
        assertThat(dto.getNomRepresentant()).isEqualTo(entity.getNomRepresentant());
        assertThat(dto.getPrenomRepresentant()).isEqualTo(entity.getPrenomRepresentant());
        assertThat(dto.getAdressePME()).isEqualTo(entity.getAdressePME());
        assertThat(dto.getActivitePrincipale()).isEqualTo(entity.getActivitePrincipale());
        assertThat(dto.getCniRepresentant()).isEqualTo(entity.getCniRepresentant());
        assertThat(dto.getEmail()).isEqualTo(entity.getEmail());
        assertThat(dto.getCodePin()).isEqualTo(entity.getCodePin());
        assertThat(dto.getNinea()).isEqualTo(entity.getNinea());
        assertThat(dto.getActivitePrincipale()).isEqualTo(entity.getActivitePrincipale());
        assertThat(dto.getRaisonSocial()).isEqualTo(entity.getRaisonSocial());
        assertThat(dto.isAtd()).isEqualTo(entity.isAtd());
        assertThat(dto.isIsactive()).isEqualTo(entity.getIsactive());
        assertThat(dto.isNantissement()).isEqualTo(entity.isNantissement());

    }


}
