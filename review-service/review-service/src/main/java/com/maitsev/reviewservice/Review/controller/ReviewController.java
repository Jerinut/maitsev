package com.maitsev.reviewservice.Review.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.maitsev.reviewservice.Review.dto.ReviewDto;
import com.maitsev.reviewservice.Review.service.ReviewService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/reviews/{id}")
    public Optional<ReviewDto> getReview(@PathVariable String id) {
        return reviewService.getReview(id);
    }

    @PostMapping("/reviews")
    public void addReview(@RequestBody ReviewDto reviewDto) {
        reviewService.addReview(reviewDto);
    }

    @PutMapping("/reviews/{id}")
    public void updateReview(@RequestBody ReviewDto reviewDto, @PathVariable String id) {
        reviewService.updateReview(id, reviewDto);
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/recipes/{id}/reviews")
    public List<ReviewDto> getProfilePosts(@PathVariable String id) {
        return reviewService.getRecipeAllReviews(id);
    }

    @GetMapping("/recipes/{id}/reviews/{reviewId}")
    public Optional<ReviewDto> getSpecificProfilePost(@PathVariable String id, @PathVariable String reviewId) {
        return reviewService.getSpecificRecipeReview(id, reviewId);
    }

    @GetMapping("/profiles/{id}/reviews")
    public List<ReviewDto> getProfileAllReviews(@PathVariable String id) {
        return reviewService.getProfileAllReviews(id);
    }

    @GetMapping("/profiles/{id}/reviews/{reviewId}")
    public Optional<ReviewDto> getSpecificProfileReview(@PathVariable String id, @PathVariable String reviewId) {
        return reviewService.getSpecificProfileReview(id, reviewId);
    }
}
