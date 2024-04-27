package com.maitsev.recipeservice.recipe.repository;

import com.maitsev.recipeservice.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,String> {

}
