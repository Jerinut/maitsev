package com.maitsev.notificationservice;

import com.maitsev.notificationservice.model.Notification;
import com.maitsev.notificationservice.repository.NotificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initializeNotificationData(NotificationRepository notificationRepository) {
        return args -> {
            Notification notification1 = new Notification();
            notification1.setId("01");
            notification1.setMessage("hello");
            notification1.setUserId("01");

            // Save the Chat entity, which will cascade the save operation to the associated messages
            notificationRepository.save(notification1);

            Notification notification2 = new Notification();
            notification2.setId("02");
            notification2.setMessage("hello");
            notification1.setStatus("NEW MESSAGE");
            notification1.setUserId("02");



            // Save the Chat entity, which will cascade the save operation to the associated messages
            notificationRepository.save(notification2);

        };
    }
}
