package com.maitsev.chatservice.chat.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "messagetable")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String content;
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    @JsonIgnore
    private Chat chat;
};
