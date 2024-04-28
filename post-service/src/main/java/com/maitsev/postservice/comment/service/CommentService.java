package com.maitsev.postservice.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maitsev.postservice.comment.dto.CommentDto;
import com.maitsev.postservice.comment.model.Comment;
import com.maitsev.postservice.comment.repository.CommentRepository;
import com.maitsev.postservice.post.model.Post;
import com.maitsev.postservice.post.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public List<CommentDto> getAllCommentsByPostId(String postId) {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAllByPostId(postId).forEach(comments::add);
        return comments.stream().map(this::mapToCommentDto).toList();
    }

    private CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
            .id(comment.getId())
            .text(comment.getText())
            .createdAt(comment.getCreatedAt())
            .postedById(comment.getPostedById())
            .build();
    }


    public void addComment(CommentDto commentDto, String postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
        Comment comment = Comment.builder()
            .id(commentDto.getId())
            .text(commentDto.getText())
            .createdAt(commentDto.getCreatedAt())
            .postedById(commentDto.getPostedById())
            .post(post)
            .build();
        commentRepository.save(comment);
        log.info("Comment {} is added to the Database", comment.getId());
    }

    public Optional<CommentDto> getComment(String postId,String commentId) {
        Optional<Comment> comment = commentRepository.findByIdAndPostId(commentId, postId);
        return comment.map(this::mapToCommentDto);
    }

    public void deleteComment(String postId, String commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
        .orElseThrow(() -> new RuntimeException("Comment not found with id " + commentId + " for post " + postId));
        commentRepository.delete(comment);
        log.info("A Comment has been deleted");
    }

    public void updateComment(String postId, String commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
            .orElseThrow(() -> new RuntimeException("Comment not found with id " + commentId + " for post " + postId));
        comment.setText(commentDto.getText());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setPostedById(commentDto.getPostedById());
        commentRepository.save(comment);
        log.info("Comment {} is updated", comment.getId());
    }
}
