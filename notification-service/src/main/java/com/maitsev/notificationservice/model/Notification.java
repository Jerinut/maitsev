package com.maitsev.notificationservice.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="notificationtable")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private String id;
    private String status;

    private String message;
}
