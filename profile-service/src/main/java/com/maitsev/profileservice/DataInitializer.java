package com.maitsev.profileservice;

import com.maitsev.profileservice.profile.model.Profile;
import com.maitsev.profileservice.profile.repository.ProfileRepository;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner loadProductsData(ProfileRepository profileRepository) {
        return args -> {
            Profile profile1 = new Profile();
            profile1.setId("01");
            profile1.setPassword("1234");
            profile1.setUsername("jerin");
            profile1.setBio("Bio for jerin");
            profile1.setCuisines(Arrays.asList("Italian", "Mexican"));
            profile1.setLikedIngredients(Arrays.asList("Cheese", "Tomato"));
            profile1.setDislikedIngredients(Arrays.asList("Onion", "Garlic"));

            
            Profile profile2 = new Profile();
            profile2.setId("02");
            profile2.setPassword("1234");
            profile2.setUsername("kasper");
            profile2.setBio("Bio for kasper");
            profile2.setCuisines(Arrays.asList("Chinese", "Indian"));
            profile2.setLikedIngredients(Arrays.asList("Chicken", "Rice"));
            profile2.setDislikedIngredients(Arrays.asList("Fish", "Egg"));
            
            profileRepository.save(profile1);
            profileRepository.save(profile2);

        };
    }
}
