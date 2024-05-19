package com.maitsev.profileservice.profile.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiletable")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    //@GeneratedValue(generator = "UUID")
    //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String username;
    //private String password;
    private String bio;
    @ElementCollection
    private List<String> cuisines;
    @ElementCollection
    private List<String> likedIngredients;
    @ElementCollection
    private List<String> dislikedIngredients;

    // public Profile(String id) {
    //     this.id = id;
    // }

    @PrePersist
    private void ensureId() {
        this.id = (id == null) ? UUID.randomUUID().toString() : this.id;
    }

}
