package sn.modelsis.cdmp.IT;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.runner.RunWith;
import sn.modelsis.cdmp.CdmpApplication;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.BonEngagementDto;
import sn.modelsis.cdmp.services.*;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.TestUtil;
import sn.modelsis.cdmp.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CdmpApplication.class)
@SpringBootTest
@AutoConfigureMockMvc

public class ConventionApiApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void cleanUp() {

    }

    @Test
    public void getAll() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        JSONArray jsonResults = new JSONArray(result.getResponse().getContentAsString());
        /*List<BonEngagement> results = new ArrayList<>();
        for (int i = 0; i < jsonResults.length(); i++) {
            results.add((BonEngagement) Util.convertJsonStringToEntity(jsonResults.getString(i), BonEngagementDto.class));
        }
        Set<BonEngagement> asfs =
                results.stream().map(DtoConverter::convertToEntity).collect(Collectors.toSet());

        assertThat(asfs).isNotEmpty();

        assertThat(asfs.size()).isNotZero();
        final Long pId1 = asf1.getId();
        assertThat(asfs.stream().anyMatch(r -> r.getId().equals(pId1))).isTrue();
        final Long pId2 = asf2.getId();
        assertThat(asfs.stream().anyMatch(r -> r.getId().equals(pId2))).isTrue();*/

    }

    @Test
    public void getAllById() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        JSONArray jsonResults = new JSONArray(result.getResponse().getContentAsString());
        /*List<BonEngagement> results = new ArrayList<>();
        for (int i = 0; i < jsonResults.length(); i++) {
            results.add((BonEngagement) Util.convertJsonStringToEntity(jsonResults.getString(i), BonEngagementDto.class));
        }
        Set<BonEngagement> asfs =
                results.stream().map(DtoConverter::convertToEntity).collect(Collectors.toSet());

        assertThat(asfs).isNotEmpty();

        assertThat(asfs.size()).isNotZero();
        final Long pId1 = asf1.getId();
        assertThat(asfs.stream().anyMatch(r -> r.getId().equals(pId1))).isTrue();
        final Long pId2 = asf2.getId();
        assertThat(asfs.stream().anyMatch(r -> r.getId().equals(pId2))).isTrue();*/

    }

    @Test
    public void add() throws Exception {

        /*MvcResult result = mockMvc
                .perform(post("/api/").contentType(MediaType.APPLICATION_JSON)
                        .content(Util.convertEntityToJsonString(asf)))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.montant").value(asf.getMontant())).andDo(print())
                .andExpect(status().isCreated()).andReturn();
        DTO savedDto = (DTO) Util
                .convertJsonStringToEntity(result.getResponse().getContentAsString(), DTO.class);
         saved = DtoConverter.convertToEntity(savedDto);

        Service.delete(saved.getId());*/

    }

    @Test
    public void update() throws Exception {
       /* asf1.setMontant(300.0);
        DTO debourseDto = DtoConverter.convertToDto(asf1);
        MvcResult result = mockMvc
                .perform(put("/api/").contentType(MediaType.APPLICATION_JSON)
                        .content(Util.convertEntityToJsonString(debourseDto)))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        DTO updatedDto = (DTO) Util
                .convertJsonStringToEntity(result.getResponse().getContentAsString(), DTO.class);

        assertThat(updatedDto.getId()).isNotNull();
        assertThat(updatedDto.getMontant()).isEqualTo(asf1.getMontant());*/


    }

    @Test
    public void delete() throws Exception {
       /* asf3 = Service.save(asf3).get();
        mockMvc
                .perform(delete("/api//{id}", asf3.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent()).andReturn();*/

    }

}
