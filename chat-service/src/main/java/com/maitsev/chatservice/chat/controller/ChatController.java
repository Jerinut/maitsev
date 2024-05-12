package com.maitsev.chatservice.chat.controller;

import java.util.List;
import java.util.Optional;

import com.maitsev.chatservice.chat.dto.MessageDto;
import com.maitsev.chatservice.chat.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maitsev.chatservice.chat.dto.ChatDto;
import com.maitsev.chatservice.chat.service.ChatService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ChatController {

  @Autowired
  private ChatService chatService;

  @GetMapping("/chats")
  public Iterable<Chat> getAllChats() {
    return chatService.getAllChats();
  }

  @GetMapping("/chats/user/{id}")
  public Iterable<ChatDto> getChatsForUser(@PathVariable String id) {
    return chatService.getChatsForUser(id);
  }

  @GetMapping("/chats/{id}")
  public Optional<ChatDto> getChat(@PathVariable String id) {
    return chatService.getChat(id);
  }

  @PostMapping("/chats")
  public ResponseEntity<Chat> addChat(@RequestBody ChatDto chatDto){
    Chat savedChat = chatService.addChat(chatDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedChat);
  }
  @PutMapping("/chats/{id}")
  public void updateChat(@RequestBody ChatDto chatDto, @PathVariable String id) {
    chatService.updateChat(id, chatDto);
  }

  @DeleteMapping("/chats/{id}")
  public void deleteChat(@PathVariable String id) {
    chatService.deleteChat(id);
  }

  @PostMapping("/chats/{chatId}/messages")
  public void addMessageToChat(@PathVariable String chatId, @RequestBody MessageDto messageDto) {
    chatService.addMessageToChat(chatId, messageDto);
  }

  @GetMapping("/chats/{chatId}/messages")
  public List<MessageDto> getAllMessagesInChat(@PathVariable String chatId) {
    return chatService.getAllMessagesInChat(chatId);
  }

  @GetMapping("/chats/{chatId}/messages/{messageId}")
  public Optional<MessageDto> getMessageInChat(@PathVariable String chatId, @PathVariable Long messageId) {
    return chatService.getMessageInChat(chatId, messageId);
  }

  @PutMapping("/chats/{chatId}/messages/{messageId}")
  public void updateMessageInChat(@PathVariable String chatId, @PathVariable Long messageId, @RequestBody MessageDto messageDto) {
    chatService.updateMessageInChat(chatId, messageId, messageDto);
  }

  @DeleteMapping("/chats/{chatId}/messages/{messageId}")
  public void deleteMessageInChat(@PathVariable String chatId, @PathVariable Long messageId) {
    chatService.deleteMessageInChat(chatId, messageId);
  }

}
