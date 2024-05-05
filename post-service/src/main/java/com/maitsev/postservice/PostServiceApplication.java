package com.maitsev.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PostServiceApplication {
	@Bean

	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

	public static void main(String[] args) {
		DataBaseInitializer.initialize("post_service_db");
		SpringApplication.run(PostServiceApplication.class, args);
	}
}
