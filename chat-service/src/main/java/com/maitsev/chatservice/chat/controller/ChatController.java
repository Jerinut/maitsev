package com.maitsev.chatservice.chat.controller;

import java.util.List;
import java.util.Optional;

import com.maitsev.chatservice.chat.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.maitsev.chatservice.chat.dto.ChatDto;
import com.maitsev.chatservice.chat.service.ChatService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class ChatController {

  @Autowired
  private ChatService chatService;

  @GetMapping("/chats")
  public List<ChatDto> getAllChats() {
    return chatService.getAllChats();
  }

  @GetMapping("/chats/{id}")
  public Optional<ChatDto> getChat(@PathVariable String id) {
    return chatService.getChat(id);
  }

  @PostMapping("/chats")
  public void addChat(@RequestBody ChatDto chatDto) {
    chatService.addChat(chatDto);
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
