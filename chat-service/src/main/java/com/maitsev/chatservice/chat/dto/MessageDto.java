package com.maitsev.chatservice.chat.dto;

import java.time.LocalDate;
import java.util.List;

import com.maitsev.chatservice.chat.model.Chat;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MessageDto {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String content;
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;
}


