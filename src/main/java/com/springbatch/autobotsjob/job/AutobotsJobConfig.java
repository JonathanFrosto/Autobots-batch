package com.springbatch.autobotsjob.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class AutobotsJobConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    Step autobotStep;

    @Bean
    public Job autobotJob() {
        return jobBuilderFactory
                .get("autobotJob")
                .start(autobotStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
