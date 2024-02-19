package fr.oli.pres.demo.repo;

import fr.oli.pres.demo.entity.Author;
import org.junit.jupiter.api.Test;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QuickPerfSqlConfig.class)
@DataJpaTest

public class AuthorRepoTest {


    @Autowired
    private AuthorRepository authorRepository;

    @ExpectSelect(1)
    @Test
    @Sql(scripts = { "/data.sql" })
    public void should_find_all_Author() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(2);
    }

    @ExpectSelect(1)
    @Test
    @Sql(scripts = { "/data.sql" })
    public void should_get_all_Author() {
        List<Author> authors = authorRepository.getAllAuthors();
        assertThat(authors).hasSize(10);
    }

}
