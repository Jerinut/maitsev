package com.maitsev.chatservice.chat.dto;

import java.time.LocalDate;
import java.util.List;

import com.maitsev.chatservice.chat.model.Message;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ChatDto {
  @Id
  private String id;
  private String user1Id;
  private String user2Id;
  @OneToMany(mappedBy = "chat")
  private List<Message> messages;
  private LocalDate createdAt;
}


