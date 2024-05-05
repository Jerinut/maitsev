package com.maitsev.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {

		DatabaseInitializer.initialize("notification_service_db");
		SpringApplication.run(
				NotificationServiceApplication.class, args);
	}

}
