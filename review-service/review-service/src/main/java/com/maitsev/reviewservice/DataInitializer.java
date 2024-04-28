package com.maitsev.reviewservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maitsev.reviewservice.Review.model.Review;
import com.maitsev.reviewservice.Review.repository.ReviewRepository;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner loadProductsData(ReviewRepository reviewRepository) {
        return args -> {
            Review review1 = new Review();
            review1.setId("01");
            review1.setRecipeId("01");
            review1.setPostedBy("01");
            review1.setScore(5.5);

            reviewRepository.save(review1);

        };
    }
}
