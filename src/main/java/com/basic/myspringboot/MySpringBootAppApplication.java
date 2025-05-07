package com.basic.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootAppApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MySpringBootAppApplication.class, args);
		SpringApplication application = new SpringApplication(MySpringBootAppApplication.class);
		//기본적으로 WebApplicationType 은 웹어플리케이션 이다.
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);
	}

	@Bean
	public String hello() {
		return "hello springboot";
	}

}
