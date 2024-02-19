package fr.oli.pres.demo;



import org.junit.jupiter.api.Test;
import org.quickperf.junit5.QuickPerfTest;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.quickperf.sql.config.QuickPerfSqlDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;



@Import(QuickPerfSqlConfig.class)
@SpringBootTest(
        classes = {DemoApplication.class}
        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Utiliser la vraie base de données
public class HibernateQueryCountTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @Sql(scripts = { "/schema.sql", "/data.sql" })
    @ExpectSelect(1)
    public void testQueryCount() {
        // GIVEN
        String url = "http://localhost:" + port + "/api/authors/author";



        // WHEN
        ParameterizedTypeReference<String> paramType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> playersResponseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null, paramType);

        // THEN
        assertThat(playersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);


        String players = playersResponseEntity.getBody();
        assertThat(players).isEqualTo("terminé");
    }


}
