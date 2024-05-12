package com.maitsev.notificationservice.controller;


import com.maitsev.notificationservice.dto.NotificationDto;
import com.maitsev.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotification();
    }

    @GetMapping("/notifications/user/{userId}")
    public List<NotificationDto> getNotificationsForUser(@PathVariable String userId) {
        return notificationService.getNotificationsForUser(userId);
    }


    @GetMapping("/notifications/{id}")
    public Optional<NotificationDto> getNotification(@PathVariable String id) {
        return notificationService.getNotification(id);
    }

    @PostMapping("/notifications")
    public void addChat(@RequestBody NotificationDto notificationDto) {
        notificationService.addNotififcation(notificationDto);
    }

    @PutMapping("/notifications/{id}")
    public void updateChat(@RequestBody NotificationDto chatDto, @PathVariable String id) {
        notificationService.updateNotification(id, chatDto);
    }

    @DeleteMapping("/notifications/{id}")
    public void deleteChat(@PathVariable String id) {
        notificationService.deleteNotification(id);
    }

}
