package com.maitsev.notificationservice.service;

import com.maitsev.chatservice.chat.dto.ChatDto;
import com.maitsev.chatservice.chat.dto.MessageDto;
import com.maitsev.chatservice.chat.model.Chat;
import com.maitsev.chatservice.chat.model.Message;
import com.maitsev.chatservice.chat.repository.ChatRepository;
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

  public List<NotificationDto> getAllChats() {
    List<Notification> chats = new ArrayList<>();
    notificationRepository.findAll().forEach(chats::add);
    return chats.stream().map(this::mapToChatDto).toList();
  }
  private NotificationDto mapToNOtificationDto(Notification notification) {
    return NotificationDto.builder()

            .build();
  }

  public void addChat(NotificationDto chatDto) {
    Chat chat = Chat.builder()
            .id(chatDto.getId())
            .user1Id(chatDto.getUser1Id())
            .user2Id(chatDto.getUser2Id())
            .messages(chatDto.getMessages())
            .createdAt(chatDto.getCreatedAt())
            .build();
    chatRepository.save(chat);
    log.info("Chat {} is added to the Database", chat.getId());
  }

  public Optional <ChatDto> getChat(String id) {
    Optional <Chat> chat = chatRepository.findById(id);
    return chat.map(this::mapToChatDto);
  }

  public void deleteChat(String id) {
    // Retrieve the chat from the database
    Chat chat = chatRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + id));

    chat.getMessages().clear();

    chatRepository.delete(chat);

    log.info("Chat with id {} and its messages have been deleted", id);
  }

  public void updateChat(String id, ChatDto chatDto) {
    Chat chat = Chat.builder()
            .id(chatDto.getId())
            .user1Id(chatDto.getUser1Id())
            .user2Id(chatDto.getUser2Id())
            .messages(chatDto.getMessages())
            .createdAt(chatDto.getCreatedAt())
            .build();
    chatRepository.save(chat);
    log.info("Chat {} is updated", chat.getId());
  }

  public void addMessageToChat(String chatId, MessageDto messageDto) {
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + chatId));

    Message message = new Message();
    message.setContent(messageDto.getContent());
    message.setChat(chat); // Set the Chat entity to the Message entity's chat field

    // Add the message to the chat's list of messages
    chat.getMessages().add(message);

    // Save the chat, which will cascade the save operation to the associated messages
    chatRepository.save(chat);
  }

  private MessageDto mapToMessageDto(Message message) {
    return MessageDto.builder()
            .id(message.getId())
            .content(message.getContent())
            .sender(message.getSender())
            .receiver(message.getReceiver())
            .createdAt(message.getCreatedAt())
            .build();
  }

  public List<MessageDto> getAllMessagesInChat(String chatId) {
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + chatId));

    // Map messages to DTOs
    return chat.getMessages().stream()
            .map(this::mapToMessageDto)
            .collect(Collectors.toList());
  }

  public Optional<MessageDto> getMessageInChat(String chatId, Long messageId) {
    // Retrieve the chat from the database
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + chatId));

    // Find the message by its ID
    Optional<Message> message = chat.getMessages().stream()
            .filter(msg -> msg.getId().equals(messageId))
            .findFirst();

    // Map message to DTO
    return message.map(this::mapToMessageDto);
  }

  public void updateMessageInChat(String chatId, Long messageId, MessageDto messageDto) {
    // Retrieve the chat from the database
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + chatId));

    // Find the message by its ID
    Optional<Message> message = chat.getMessages().stream()
            .filter(msg -> msg.getId().equals(messageId))
            .findFirst();

    // If message is found, update its content
    message.ifPresent(msg -> {
      msg.setContent(messageDto.getContent());
      // Update other message properties as needed
    });

    // Save the updated chat to update messages in the database
    chatRepository.save(chat);
  }

  public void deleteMessageInChat(String chatId, Long messageId) {
    // Retrieve the chat from the database
    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + chatId));

    chat.getMessages().removeIf(msg -> msg.getId().equals(messageId));

    chatRepository.save(chat);
  }


}
