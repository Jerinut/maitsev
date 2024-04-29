package com.maitsev.chatservice.chat.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chattable")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    private String id;
    private String user1Id;
    private String user2Id;
    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
    private LocalDate createdAt;
}


