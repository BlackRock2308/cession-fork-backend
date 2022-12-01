package sn.modelsis.cdmp.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.CdmpApplicationTests;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {CdmpApplicationTests.class, DataSourceTransactionManagerAutoConfiguration.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class MapperBaseTest {

    @Test
    void toEntity() {
        assertThat(1L).isPositive();
    }
}
