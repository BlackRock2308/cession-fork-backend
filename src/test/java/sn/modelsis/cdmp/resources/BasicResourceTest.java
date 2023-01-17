package sn.modelsis.cdmp.resources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.net.MediaType;
import sn.modelsis.cdmp.CdmpApplicationTests;

import javax.sql.DataSource;
import java.net.http.HttpHeaders;
import java.util.Collections;

//@Testcontainers
//@ActiveProfiles("test")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
//@SpringBootTest(
//        classes = {CdmpApplicationTests.class},
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        properties = {
//            "spring.datasource.url=jdbc:tc:postgresql:14-alpine//testcontainers/hello"
//        }
//)
@Testcontainers
public class BasicResourceTest {


    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

//    protected RequestSpecification requestSpecification;
//
//
//    @BeforeEach
//    public void setupMyIntegrationtest(){
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//        requestSpecification = new RequestSpecBuilder()
//                .setPort(port)
//                .build();
//    }

//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Test
//    void basic() {
//        Assertions.assertThat(mockMvc).isNotNull();
//    }


    // Containers declared as static fields will be shared between test methods.
    @Container
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:13.7")
                    .withDatabaseName("product_db")
                    .withUsername("user")
                    .withPassword("password")
                    // Container can have tmpfs mounts for storing data in host memory.
                    // This is useful if you want to speed up your database tests.
                    .withTmpFs(Collections.singletonMap("/var/lib/postgresql/data", "rw"));

    private static DataSource datasource;



    @BeforeAll
    static void init() {
        var config = new HikariConfig();
        config.setJdbcUrl(container.getJdbcUrl());
        config.setUsername(container.getUsername());
        config.setPassword(container.getPassword());
        config.setDriverClassName(container.getDriverClassName());
        datasource = new HikariDataSource(config);
    }

    @AfterAll
    static void close() {
        container.close();
    }
}
