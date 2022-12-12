package sn.modelsis.cdmp.repository;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.CdmpApplicationTests;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {CdmpApplicationTests.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)

class RepositoryBaseTest {
    public static final int DEFAULT_SIZE = 10;
    public static final int DEFAULT_PAGE = 0;
    static final PageRequest PAGE = PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE);

    @Test
    @Before
    void basic() {
        assertThat(PAGE.getPageSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(PAGE.getPageNumber()).isEqualTo(DEFAULT_PAGE);
    }
}
