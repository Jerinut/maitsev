package com.maitsev.reviewservice.Review.repository;

import org.springframework.data.repository.CrudRepository;
import com.maitsev.reviewservice.Review.model.Review;

public interface ReviewRepository extends CrudRepository<Review, String> {

}
