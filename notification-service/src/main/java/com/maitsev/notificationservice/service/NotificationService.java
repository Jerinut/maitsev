package com.maitsev.notificationservice.service;

import com.maitsev.notificationservice.dto.NotificationDto;
import com.maitsev.notificationservice.model.Chat;
import com.maitsev.notificationservice.model.Message;
import com.maitsev.notificationservice.model.Notification;
import com.maitsev.notificationservice.model.Review;
import com.maitsev.notificationservice.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    return notifications.stream().map(this::mapToNotificationDto).toList();
  }

  private NotificationDto mapToNotificationDto(Notification notification) {
    return NotificationDto.builder()
        .id(notification.getId())
        .message(notification.getMessage())
        .status(notification.getStatus())
        .userId(notification.getUserId())
        .build();
  }

  public void addNotififcation(NotificationDto notififcationDto) {
    Notification notification = Notification.builder()
        .id(notififcationDto.getId())
        .status(notififcationDto.getStatus())
        .message(notififcationDto.getMessage())
        .userId(notififcationDto.getUserId())
        .build();
    notificationRepository.save(notification);
  }

  public Optional<NotificationDto> getNotification(String id) {
    Optional<Notification> notification = notificationRepository.findById(id);
    return notification.map(this::mapToNotificationDto);
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
        .userId(notificationDto.getUserId())
        .message(notificationDto.getMessage())
        .build();
    notificationRepository.save(notification);
    log.info("Notification is updated", notification.getId());
  }

  @KafkaListener(topics = "chatTopicJson", groupId = "chatEventGroupForChat")
  public void consumes(Chat chat) {
    log.info("Log message recieved from chat topic: {} ", chat.toString());
    String user1Id = chat.getUser1Id();
    String user2Id = chat.getUser2Id();

    // Create notifications for both users
    Notification notificationUser1 = Notification.builder()
        .id(UUID.randomUUID().toString())
        .status("NEW CHAT")
        .userId(user1Id)
        .message("You have a new chat. Yuppi!")
        .build();

    Notification notificationUser2 = Notification.builder()
        .id(UUID.randomUUID().toString())
        .status("NEW CHAT")
        .userId(user2Id)
        .message("You have a new chat. Yuppi!")
        .build();

    // Save notifications to the repository
    notificationRepository.save(notificationUser1);
    notificationRepository.save(notificationUser2);
  }

  @KafkaListener(topics = "messageTopicJson", groupId = "chatEventGroupForChat")
  public void consumes(Message message) {
    log.info("Log message recieved from message topic: {} ", message.toString());
    String receiverId = message.getReceiver();

    // Create notifications for receiver
    Notification notificationUser1 = Notification.builder()
        .id(UUID.randomUUID().toString())
        .status("NEW MESSAGE")
        .userId(receiverId)
        .message("just new message")
        .build();

    // Save notification to the repository
    notificationRepository.save(notificationUser1);
  }

  @KafkaListener(topics = "reviewTopicJson", groupId = "chatEventGroupForChat")
  public void consumes(Review review) {
    log.info("Log message recieved from review topic: {} ", review.toString());
     String postedBy = review.getPostedBy();

    // Create notifications for receiver

     Notification notificationUser1 = Notification.builder()
      .id(UUID.randomUUID().toString())
      .status("NEW REVIEW")
      .userId(postedBy)
      .message("just new review")
      .build();

    // Save notification to the repository
     notificationRepository.save(notificationUser1);
  }

  public List<NotificationDto> getNotificationsForUser(String userId) {
    List<Notification> notifications = notificationRepository.findByUserId(userId);
    return notifications.stream()
        .map(this::mapToNotificationDto)
        .collect(Collectors.toList());
  }
}
