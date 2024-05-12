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
            profile1.setId("e3cf4b4b-a57b-41ba-a19e-355b75f90a2e");
            profile1.setPassword("1234");
            profile1.setUsername("jerin");
            profile1.setBio("Bio for jerin");
            profile1.setCuisines(Arrays.asList("Italian", "Mexican", "Vegan"));
            profile1.setLikedIngredients(Arrays.asList("Cheese", "Tomato"));
            profile1.setDislikedIngredients(Arrays.asList("Onion", "Garlic"));

            Profile profile2 = new Profile();
            profile2.setId("01");
            profile2.setPassword("1234");
            profile2.setUsername("kasper");
            profile2.setBio("Bio for kasper");
            profile2.setCuisines(Arrays.asList("Chinese", "Indian"));
            profile2.setLikedIngredients(Arrays.asList("Chicken", "Rice"));
            profile2.setDislikedIngredients(Arrays.asList("Fish", "Egg"));

            Profile profile3 = new Profile();
            profile2.setId("02");
            profile2.setPassword("12345");
            profile2.setUsername("fidan");
            profile2.setBio("Bio for fidan");
            profile2.setCuisines(Arrays.asList("Chinese", "Indian"));
            profile2.setLikedIngredients(Arrays.asList("Potato", "Rice"));
            profile2.setDislikedIngredients(Arrays.asList("Fish", "Chicken"));

            Profile profile4 = new Profile();
            profile2.setId("03");
            profile2.setPassword("123455");
            profile2.setUsername("henri");
            profile2.setBio("Bio for henri");
            profile2.setCuisines(Arrays.asList("Chinese", "Mexican"));
            profile2.setLikedIngredients(Arrays.asList("Rocca", "Cheese", "Tomato"));
            profile2.setDislikedIngredients(Arrays.asList("Egg", "Onion"));
            
            profileRepository.save(profile1);
            profileRepository.save(profile2);
            profileRepository.save(profile3);

        };
    }
}
