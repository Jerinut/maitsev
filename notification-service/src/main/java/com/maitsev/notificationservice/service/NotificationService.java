package com.maitsev.notificationservice.service;
import com.maitsev.notificationservice.dto.NotificationDto;
import com.maitsev.notificationservice.model.Notification;
import com.maitsev.notificationservice.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private WebClient.Builder webClientBuilder;

  public List<NotificationDto> getAllNotification() {
    List<Notification> notifications = new ArrayList<>();
    notificationRepository.findAll().forEach(notifications::add);
    return notifications.stream().map(this::mapToNOtificationDto).toList();
  }
  private NotificationDto mapToNOtificationDto(Notification notification) {
    return NotificationDto.builder()

            .build();
  }

  public void addNotififcation(NotificationDto notififcationDto) {
    Notification notification = Notification.builder()
            .id(notififcationDto.getId())
            .status(notififcationDto.getStatus())
            .message(notififcationDto.getMessage())
            .build();
    notificationRepository.save(notification);
  }

  public Optional <NotificationDto> getNotification(String id) {
    Optional <Notification> notification = notificationRepository.findById(id);
    return notification.map(this::mapToNOtificationDto);
  }

  public void deleteNotification(String id) {
    // Retrieve the chat from the database
    Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("notification not found with id " + id));


    notificationRepository.delete(notification);

    log.info("Chat with id {} and its messages have been deleted", id);
  }

  public void updateNotification(String id, NotificationDto notificationDto) {
    Notification notification = Notification.builder()
            .id(notificationDto.getId())
            .status(notificationDto.getStatus())
            .message(notificationDto.getMessage())
            .build();
    notificationRepository.save(notification);
    log.info("Notification is updated", notification.getId());
  }



}
