package com.maitsev.postservice.comment.repository;

import com.maitsev.postservice.comment.model.Comment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, String> {
    List<Comment> findAllByPostId(String postId);
    Optional<Comment> findByIdAndPostId(String id, String postId);
}
