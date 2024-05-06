package com.maitsev.profileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProfileServiceApplication {

	@Bean
	// WebClient.Builder is mutable builder for creating a WebClient.
	// WebClient is an interface representing the main entry point for performing
	// web requests.
	// It is a part of Spring Web Reactive module and will be replacing the classic
	// RestTemplate.
	// Unlike RestTemplate (synchronous), WebClient is a reactive (asynchronous),
	// non-blocking solution that works over the HTTP/1.1 protocol.
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

	public static void main(String[] args) {
		DataBaseInitializer.initialize("profile_service_db");
		SpringApplication.run(ProfileServiceApplication.class, args);
	}

}
