package com.maitsev.recipeservice.recipe.controller;

import com.maitsev.recipeservice.recipe.dto.RecipeDto;
import com.maitsev.recipeservice.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes")
    public List<RecipeDto> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/recipes/{id}")
    public Optional<RecipeDto> getRecipe(@PathVariable String id) {
        return recipeService.getRecipe(id);
    }

    @GetMapping("/profiles/{id}/recipes")
    public List<RecipeDto> getProfileRecipes(@PathVariable String id) {
        return recipeService.getProfileAllRecipes(id);
    }

    @GetMapping("/profiles/{id}/recipes/{recipesId}")
    public Optional<RecipeDto> getSpecificProfileRecipe(@PathVariable String id, @PathVariable String recipesId) {
        return recipeService.getSpecificProfileRecipe(id, recipesId);
    }

    @PostMapping("/recipes")
    public void addRecipe(@RequestBody RecipeDto recipeDto) {
        recipeService.addRecipe(recipeDto);
    }

    @PutMapping("/recipes/{id}")
    public void updateRecipe(@RequestBody RecipeDto recipeDto, @PathVariable String id) {
        recipeService.updateRecipe(id, recipeDto);
    }

    @DeleteMapping("/recipes/{id}")
    public void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }
}
