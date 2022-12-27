package com.expertpeople;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableAutoConfiguration
@EnableBatchProcessing
@Configuration
public class TestJobConfiguration {

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtilsd() { // (2)
        return new JobLauncherTestUtils();
    }
    @Bean
    public JobRepositoryTestUtils jobRepositoryTestUtils(){
        return new JobRepositoryTestUtils();
    }

}
