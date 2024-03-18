package com.maitsev.profileservice.profile.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    @Id
    private String id;
    private String username;
    private String password;

    public ProfileDto(String id){
        this.id = id;
    }
}
