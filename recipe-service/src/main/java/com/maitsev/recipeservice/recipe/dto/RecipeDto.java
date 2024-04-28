package com.maitsev.recipeservice.recipe.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    @Id
    private String id;
    private String title;

    private List<String> ingredients;
    private String servingSize;
    private String cookingTime;
    private String cuisineType;
    private String steps;

    private String postedById;

    public RecipeDto(String id) {
        this.id = id;
    }
}
