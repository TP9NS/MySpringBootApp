package com.basic.myspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    @Bean
    public CustomerVO customerVO() {
        return CustomerVO.builder()  //CustomerVOBuilder
                .mode("테스트환경")
                .rate(0.5)
                .build();

    }
}
