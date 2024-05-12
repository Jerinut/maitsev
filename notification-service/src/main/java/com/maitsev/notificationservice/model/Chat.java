package com.maitsev.notificationservice.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.GeneratedValue;

@Entity
@Table(name = "chattable")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String user1Id;
    private String user2Id;
    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
    private LocalDate createdAt;
}