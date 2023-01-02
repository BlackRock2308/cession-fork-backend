package sn.modelsis.cdmp.resources;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.modelsis.cdmp.CdmpApplicationTests;
@Testcontainers
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(classes = {CdmpApplicationTests.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BasicResourceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void basic() {
        Assertions.assertThat(mockMvc).isNotNull();
    }
}
