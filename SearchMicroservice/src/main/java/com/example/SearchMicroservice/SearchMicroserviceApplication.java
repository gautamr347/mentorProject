package com.example.SearchMicroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SearchMicroserviceApplication {
	public static final Logger logger = LoggerFactory.getLogger(SearchMicroserviceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SearchMicroserviceApplication.class, args);
	}


	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
