package com.maitsev.postservice.comment.repository;

import com.maitsev.postservice.comment.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, String> {
    
}
