package com.example.Training.Microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TrainingMicroserviceApplication {
	public static final Logger logger = LoggerFactory.getLogger(TrainingMicroserviceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TrainingMicroserviceApplication.class, args);
		logger.info("info testing");
		logger.warn("warn testing");
		logger.error("error testing");
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
