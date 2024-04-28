package com.maitsev.reviewservice.Review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}
