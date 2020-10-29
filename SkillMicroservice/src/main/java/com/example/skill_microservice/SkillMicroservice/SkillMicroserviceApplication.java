package com.example.skill_microservice.SkillMicroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SkillMicroserviceApplication {
	public static final Logger logger = LoggerFactory.getLogger(SkillMicroserviceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SkillMicroserviceApplication.class, args);

	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
