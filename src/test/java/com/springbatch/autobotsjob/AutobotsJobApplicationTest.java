package com.springbatch.autobotsjob;

import com.springbatch.autobotsjob.dominio.Autobot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AutobotsJobApplication.class})
@TestPropertySource("classpath:application-test.properties")
@SpringBatchTest
class AutobotsJobApplicationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    @Qualifier("appDataSource")
    private DataSource dataSource;

    private JdbcOperations jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    void test() {
        jdbcTemplate.execute("CREATE TABLE autobots ( name varchar(100) NOT NULL, car varchar(255) NOT NULL, PRIMARY KEY (name) )");
        jdbcTemplate.update("insert into autobots (name, car) values ('optimus', 'caminhão')");
        jdbcTemplate.update("insert into autobots (name, car) values ('bambobie', 'fusca amarelo')");

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("autobotStep");
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        List<Autobot> autobots = jdbcTemplate.query("select * from autobots",
                (rs, rowNum) -> {
                    Autobot autobot = new Autobot();
                    autobot.setName(rs.getString("name"));
                    autobot.setCar(rs.getString("car"));
                    return autobot;
                });

        assertEquals(2, autobots.size());
    }
}
