package com.maitsev.postservice.post.repository;

import com.maitsev.postservice.post.model.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, String> {

}
