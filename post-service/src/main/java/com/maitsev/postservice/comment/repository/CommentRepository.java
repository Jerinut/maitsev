package com.maitsev.postservice.comment.repository;

import com.maitsev.postservice.post.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, String> {
    
}
