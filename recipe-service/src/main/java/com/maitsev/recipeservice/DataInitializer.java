package com.maitsev.recipeservice;
import com.maitsev.recipeservice.recipe.model.Recipe;
import com.maitsev.recipeservice.recipe.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner loadRecipeData(RecipeRepository recipeRepository) {
        return args -> {
            Recipe recipe1 = new Recipe();
            List<String> ingredients = new ArrayList<>();
               ingredients.add("pasta");
               ingredients.add("cheese");
               ingredients.add("milk");
               ingredients.add("tomato sauce");

             recipe1.setId("01");
             recipe1.setTitle("Pasta Bolognese");
            recipe1.setIngredients(ingredients);
            recipe1.setTitle("Pasta");
            recipe1.setCuisineType("Italian");
            recipe1.setSteps("4");
            recipe1.setCookingTime("20mins");
            recipe1.setServingSize("2");
            recipeRepository.save(recipe1);


            Recipe recipe2 = new Recipe();
            List<String> ingredients2 = new ArrayList<>();
            ingredients.add("pasta");
            ingredients.add("cheese");
            ingredients.add("milk");
            ingredients.add("tomato sauce");

            recipe2.setId("02");
            recipe2.setTitle("Pasta Bolognese");
            recipe2.setIngredients(ingredients);
            recipe2.setTitle("Pasta");
            recipe2.setCuisineType("Italian");
            recipe2.setSteps("4");
            recipe2.setCookingTime("20mins");
            recipe2.setServingSize("2");
            recipeRepository.save(recipe2);
//            profile1.setId("01");
//            profile1.setPassword("1234");
//            profile1.setUsername("jerin");
//
//            Profile profile2 = new Profile();
//            profile2.setId("02");
//            profile2.setPassword("1234");
//            profile2.setUsername("kasper");
//            profileRepository.save(profile1);
//            profileRepository.save(profile2);
        };
    }
}
