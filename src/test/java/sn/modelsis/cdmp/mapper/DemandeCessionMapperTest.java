package sn.modelsis.cdmp.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.DemandeAdhesionDTOTestData;
import sn.modelsis.cdmp.data.DemandeCessionDTOTestData;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.mappers.DemandeCessionMapper;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemandeCessionMapperTest extends MapperBaseTest{

    DemandeCessionDto dto;

    DemandeCession entity;

    @Autowired
    DemandeCessionMapper cessionMapper;

    @BeforeEach
    void setUp() {
        dto = DemandeCessionDTOTestData.defaultDTO();
        entity = cessionMapper.asEntity(dto);
    }

    @Test
    void toEntity(){

        Assertions.assertAll(
                ()-> assertThat(entity).isNotNull(),
                ()->assertThat(entity.getIdDemande()).isEqualTo(dto.getIdDemande()),
                ()->assertThat(entity.getPme()).isEqualTo(dto.getPme()),
                ()->assertThat(entity.getStatut()).isEqualTo(dto.getStatut())
        );

    }
}
