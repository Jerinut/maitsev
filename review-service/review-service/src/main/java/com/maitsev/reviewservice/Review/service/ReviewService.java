package com.maitsev.reviewservice.Review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;

import com.maitsev.reviewservice.Review.dto.ReviewDto;
import com.maitsev.reviewservice.Review.model.Review;
import com.maitsev.reviewservice.Review.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        reviewRepository.findAll().forEach(reviews::add);
        return reviews.stream().map(this::mapToReviewDto).toList();
    }

    private ReviewDto mapToReviewDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .recipeId(review.getRecipeId())
                .createdAt(review.getCreatedAt())
                .postedBy(review.getPostedBy())
                .score(review.getScore())
                .build();
    }

    public void addReview(ReviewDto reviewDto) {
        Review review = Review.builder()
                .id(reviewDto.getId())
                .recipeId(reviewDto.getRecipeId())
                .createdAt(reviewDto.getCreatedAt())
                .postedBy(reviewDto.getPostedBy())
                .score(reviewDto.getScore())
                .build();
        reviewRepository.save(review);
        log.info("Review {} is added to the Database", review.getId());
    }

    public Optional<ReviewDto> getReview(String id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.map(this::mapToReviewDto);
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
        log.info("A Review has been deleted");
    }

    public void updateReview(String id, ReviewDto reviewDto) {
        Review review = Review.builder()
                .id(reviewDto.getId())
                .recipeId(reviewDto.getRecipeId())
                .createdAt(reviewDto.getCreatedAt())
                .postedBy(reviewDto.getPostedBy())
                .score(reviewDto.getScore())
                .build();
        reviewRepository.save(review);
        log.info("Post {} is updated", review.getId());
    }

    public List<ReviewDto> getRecipeAllReviews(String id) {
        List<ReviewDto> allReviews = webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8002/api/reviews")
                .retrieve()
                .bodyToFlux(ReviewDto.class)
                .collectList()
                .block();

        return allReviews.stream()
                .filter(review -> id.equals(review.getRecipeId()))
                .collect(Collectors.toList());
    }

    public Optional<ReviewDto> getSpecificRecipeReview(String id, String reviewId) {
        List<ReviewDto> allReviews = getRecipeAllReviews(id);
        return allReviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst();
    }

    public List<ReviewDto> getProfileAllReviews(String id) {
        List<ReviewDto> allReviews = webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8002/api/reviews")
                .retrieve()
                .bodyToFlux(ReviewDto.class)
                .collectList()
                .block();

        return allReviews.stream()
                .filter(review -> id.equals(review.getPostedBy()))
                .collect(Collectors.toList());
    }

    public Optional<ReviewDto> getSpecificProfileReview(String id, String reviewId) {
        List<ReviewDto> allReviews = getProfileAllReviews(id);
        return allReviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst();
    }
}
