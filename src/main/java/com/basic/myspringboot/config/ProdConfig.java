package com.basic.myspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {
    @Bean
    public CustomerVO customerVO() {
        return CustomerVO.builder()  //CustomerVOBuilder
                .mode("운영환경")
                .rate(1.5)
                .build();

    }
}
