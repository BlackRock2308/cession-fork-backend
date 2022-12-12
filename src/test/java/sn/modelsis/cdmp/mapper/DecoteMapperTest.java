package sn.modelsis.cdmp.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.ParametrageDecoteDTOTest;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
public class DecoteMapperTest extends MapperBaseTest{

    ParametrageDecote entity;

    ParametrageDecoteDTO dto;

    @BeforeEach
    void setUp() {
        dto = ParametrageDecoteDTOTest.defaultDTO();
        entity = ParametrageDecoteDTOTest.defaultEntity();
    }

    @Test
    void toEntity(){
        assertThat(entity).isNotNull();
        assertThat(entity.getIdDecote()).isEqualTo(dto.getIdDecote());
        assertThat(entity.getDecoteValue()).isEqualTo(dto.getDecoteValue());
        assertThat(entity.getBorneInf()).isEqualTo(dto.getBorneInf());
        assertThat(entity.getBorneSup()).isEqualTo(dto.getBorneSup());

    }

    @Test
    void toDTO(){
        assertThat(dto).isNotNull();
        assertThat(dto.getIdDecote()).isEqualTo(entity.getIdDecote());
        assertThat(dto.getDecoteValue()).isEqualTo(entity.getDecoteValue());
        assertThat(dto.getBorneInf()).isEqualTo(entity.getBorneInf());
        assertThat(dto.getBorneSup()).isEqualTo(entity.getBorneSup());

    }
}
