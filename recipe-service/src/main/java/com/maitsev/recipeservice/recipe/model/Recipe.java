package com.maitsev.recipeservice.recipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Entity
@Table(name="recipetable")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
        @Id
        private String id;
        private String title;

        private List<String> ingredients;
        private String servingSize;
        private String cookingTime;
        private String cuisineType;
        private String steps;

        public Recipe(String id) {
                this.id = id;
        }
}
