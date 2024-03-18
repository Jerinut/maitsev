package com.maitsev.profileservice;

import com.maitsev.profileservice.profile.model.Profile;
import com.maitsev.profileservice.profile.repository.ProfileRepository;
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

            Profile profile2 = new Profile();
            profile2.setId("02");
            profile2.setPassword("1234");
            profile2.setUsername("kasper");
            profileRepository.save(profile1);
            profileRepository.save(profile2);

        };
    }
}
