package com.springbatch.autobotsjob.step;

import com.springbatch.autobotsjob.dominio.Autobot;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutobotStepConfig {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    ItemReader<Autobot> itemReader;

    @Autowired
    ItemWriter<Autobot> itemWriter;

    @Bean
    public Step autobotStep() {
        return stepBuilderFactory
                .get("autobotStep")
                .<Autobot, Autobot>chunk(1)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }
}
