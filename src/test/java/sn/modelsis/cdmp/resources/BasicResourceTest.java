package sn.modelsis.cdmp.resources;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import sn.modelsis.cdmp.CdmpApplicationTests;

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
