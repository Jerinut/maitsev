package com.maitsev.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {

		DatabaseInitializer.initialize("notification_service_db");
		SpringApplication.run(
				NotificationServiceApplication.class, args);
	}

}
