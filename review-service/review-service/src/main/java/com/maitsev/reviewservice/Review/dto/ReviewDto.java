package com.maitsev.reviewservice.Review.dto;

import java.time.LocalDate;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    @Id
    private String id;
    private String recipeId;
    private LocalDate createdAt;
    private String postedBy;
    private Double score;
}