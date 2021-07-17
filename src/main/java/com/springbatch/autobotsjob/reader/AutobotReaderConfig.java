package com.springbatch.autobotsjob.reader;

import com.springbatch.autobotsjob.dominio.Autobot;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AutobotReaderConfig {

    private static final String QUERY = "select * from autobots";

    @Bean
    public JdbcCursorItemReader<Autobot> autobotReader(
            @Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Autobot>()
                .name("autobotReader")
                .dataSource(dataSource)
                .sql(QUERY)
                .rowMapper(((resultSet, i) -> {
                    Autobot autobot = new Autobot();
                    autobot.setName(resultSet.getString("name"));
                    autobot.setCar(resultSet.getString("car"));
                    return autobot;
                }))
                .build();
    }
}
