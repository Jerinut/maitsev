package com.maitsev.contentpersonalizationservice.contentpersonalization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maitsev.contentpersonalizationservice.contentpersonalization.dto.PostDto;
import com.maitsev.contentpersonalizationservice.contentpersonalization.dto.RecipeDto;
import com.maitsev.contentpersonalizationservice.contentpersonalization.service.ContentPersonalizationService;

import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class ContentPersonalizationController {

    @Autowired
    private ContentPersonalizationService contentpersonalizationservice;

    @GetMapping("/for-you/posts/{userId}")
    public Flux<PostDto> getForYouPagePosts(@PathVariable String userId) {
        return contentpersonalizationservice.getPersonalizedPosts(userId);
    }
    
    @GetMapping("/for-you/recipes/{userId}")
    public Flux<RecipeDto> getForYouPageRecipes(@PathVariable String userId) {
        return contentpersonalizationservice.getPersonalizedRecipes(userId);
    }
}
