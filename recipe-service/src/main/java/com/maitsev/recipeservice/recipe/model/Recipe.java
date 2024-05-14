package com.maitsev.recipeservice.recipe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;
import java.util.UUID;

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

        private String postedById;

        public Recipe(String id) {
                this.id = id;
        }


        @PrePersist
        private void ensureId() {
                this.id = (id == null) ? UUID.randomUUID().toString() : this.id;
        }

}
