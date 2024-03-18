package com.maitsev.profileservice.profile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="profiletable")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    private String id;
    private String username;
    private String password;

    public Profile(String id) {
        this.id = id;
    }

}

