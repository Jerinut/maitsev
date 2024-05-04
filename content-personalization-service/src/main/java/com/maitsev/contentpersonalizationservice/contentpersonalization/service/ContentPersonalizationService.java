package com.maitsev.contentpersonalizationservice.contentpersonalization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.maitsev.contentpersonalizationservice.contentpersonalization.dto.PostDto;
import com.maitsev.contentpersonalizationservice.contentpersonalization.dto.ProfileDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ContentPersonalizationService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Flux<PostDto> getPersonalizedPosts(String userId) {
        Mono<ProfileDto> profileDto = this.fetchUserProfile(userId);
        Flux<PostDto> posts = this.fetchAllPosts();

        return profileDto.flatMapMany(profile ->
            posts.filter(post -> this.isRelevantPost(post, profile))
        );
    }

    private Mono<ProfileDto> fetchUserProfile(String userId) {
        return webClientBuilder
                        .build()
                        .get()
                        .uri("http://localhost:8000/api/profiles/{id}", userId)
                        .retrieve()
                        .bodyToMono(ProfileDto.class);
    }

    private Flux<PostDto> fetchAllPosts() {
        return webClientBuilder
                        .build()
                        .get()
                        .uri("http://localhost:8001/api/posts")
                        .retrieve()
                        .bodyToFlux(PostDto.class);
    }

    private boolean isRelevantPost(PostDto post, ProfileDto profile) {
        // Check if any post tags match the profile's liked cuisines or ingredients
        boolean matchesLikes = post.getTags().stream()
        .anyMatch(tag -> profile.getLikedIngredients().contains(tag) || profile.getCuisines().contains(tag));

        // Check if any post tags match the profile's disliked ingredients
        boolean matchesDislikes = post.getTags().stream()
        .anyMatch(tag -> profile.getDislikedIngredients().contains(tag));

        // A post is relevant if it matches likes and does not match dislikes
        return matchesLikes && !matchesDislikes;
    }
}
