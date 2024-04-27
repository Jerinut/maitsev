package com.maitsev.recipeservice.recipe.service;

import com.maitsev.recipeservice.recipe.dto.RecipeDto;
import com.maitsev.recipeservice.recipe.model.Recipe;
import com.maitsev.recipeservice.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes.stream().map(this::mapToRecipeDto).toList();
    }

    private RecipeDto mapToRecipeDto(Recipe recipe) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .ingredients(recipe.getIngredients())
                .servingSize(recipe.getServingSize())
                .cookingTime(recipe.getCookingTime())
                .build();
    }

    public void addRecipe(RecipeDto recipeDto) {
        Recipe recipe = Recipe.builder()
                        .id(recipeDto.getId())
                                .build();
        recipeRepository.save(recipe);

    }

    public Optional<RecipeDto> getRecipe(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.map(this::mapToRecipeDto);
    }

    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);

    }

    public void updateRecipe(String id, RecipeDto recipeDto) {
        Recipe recipe = Recipe.builder()
                .id(recipeDto.getId())
                .build();
        recipeRepository.save(recipe);
    }

//    public List<RecipeDto> getProfileAllPosts(String id) {
//        List<RecipeDto> allPosts = webClientBuilder
//                .build()
//                .get()
//                .uri("http://localhost:8002/api/recipes")
//                .retrieve()
//                .bodyToFlux(RecipeDto.class)
//                .collectList()
//                .block();
//
//        return allPosts.stream()
//                .filter(post -> id.equals(p.getPostedById()))
//                .collect(Collectors.toList());
//    }

//    public Optional<RecipeDto> getSpecificRecipe(String id, String postId) {
//        List<RecipeDto> allPosts = getAllRecipes();
//        return allPosts.stream()
//                .filter(post -> post.getId().equals(postId))
//                .findFirst();
//    }


}
