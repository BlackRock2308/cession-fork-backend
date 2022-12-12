package sn.modelsis.cdmp.mapper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.modelsis.cdmp.data.DemandeAdhesionDTOTestData;
import sn.modelsis.cdmp.data.PmeDTOTestData;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.mappers.DemandeAdhesionMapper;
import sn.modelsis.cdmp.util.DtoConverter;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemandeAdhesionMapperTest extends MapperBaseTest{

    Demande entity;
    DemandeAdhesionDto dto;

    private DemandeAdhesionMapper adhesionMapper;

    @BeforeEach
    void setUp() {
        dto = DemandeAdhesionDTOTestData.defaultDTO();
        entity = adhesionMapper.asEntity(dto);
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


    @Test
    void toDTO(){

        Assertions.assertAll(
                ()-> assertThat(dto).isNotNull(),
                ()->assertThat(dto.getIdDemande()).isEqualTo(entity.getIdDemande()),
                ()->assertThat(dto.getPme()).isEqualTo(entity.getPme()),
                ()->assertThat(dto.getStatut()).isEqualTo(entity.getStatut())
        );

    }
}
