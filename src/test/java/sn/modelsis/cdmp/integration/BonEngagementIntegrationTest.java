package sn.modelsis.cdmp.integration;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import sn.modelsis.cdmp.data.BonEngagementDTOTestData;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonEngagementIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    private static BonEngagementDto dto;

    private static BonEngagement entity;

    private static BonEngagement bonEngagement;
    @Autowired
    private BonEngagementRepository bonEngagementRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }



    @BeforeEach
    public void beforeSetup() {
        baseUrl = baseUrl + ":" + port + "/api/bonEngagement";

        entity = BonEngagementDTOTestData.defaultEntity();
        dto = BonEngagementDTOTestData.defaultDTO();

        bonEngagement = bonEngagementRepository.save(entity);
    }

    @AfterEach
    void afterEach(){
        bonEngagementRepository.deleteAll();
    }


    @Test
    void shouldCreateBonEngagementTest() {

         BonEngagement newBE = restTemplate.postForObject(baseUrl, entity, BonEngagement.class);

        assertNotNull(newBE);
        assertThat(newBE.getIdBonEngagement()).isNotNull();
    }

    @Test
    void shouldFetchAllBonEngagementsTest() {

        List<BonEngagement> bonEngagementList = restTemplate.getForObject(baseUrl, List.class);

        assertThat(bonEngagementList.size()).isEqualTo(1);
    }

    @Test
    void shouldFetchOneBonEngagemenntByIdTest() {

        BonEngagement existingBonEngagement = restTemplate
                .getForObject(baseUrl+"/"+bonEngagement.getIdBonEngagement(), BonEngagement.class);

        assertNotNull(existingBonEngagement);
        assertEquals(bonEngagement.getNomMarche(), existingBonEngagement.getNomMarche());
    }



    @Test
    void shouldDeleteBonEngagementTest() {

        restTemplate.delete(baseUrl+"/"+bonEngagement.getIdBonEngagement());

        int count = bonEngagementRepository.findAll().size();

        assertEquals(0, count);
    }

}
