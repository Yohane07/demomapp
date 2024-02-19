package fr.oli.pres.demo.repo;

import fr.oli.pres.demo.entity.Passport;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;


import java.util.List;
@Import(QuickPerfSqlConfig.class)
@DataJpaTest

public class PassportRepoTest {
    @Autowired
    private PassportRepository passportRepo;
    @ExpectSelect(1)
    @Test
    @Sql(scripts = {"/data.sql"})
    public void should_find_all_Passport() {
        List<Passport> passports = passportRepo.findAll();
        assertThat(passports).hasSize(0);
    }

}
