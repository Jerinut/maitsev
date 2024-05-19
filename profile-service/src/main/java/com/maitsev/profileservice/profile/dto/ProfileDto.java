package com.maitsev.profileservice.profile.dto;

import java.util.List;

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
    //private String password;
    private String bio;
    private List<String> cuisines;
    private List<String> likedIngredients;
    private List<String> dislikedIngredients;
    
    public ProfileDto(String id){
        this.id = id;
    }
}
