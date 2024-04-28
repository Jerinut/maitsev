package com.maitsev.postservice.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maitsev.postservice.comment.dto.CommentDto;
import com.maitsev.postservice.comment.model.Comment;
import com.maitsev.postservice.comment.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDto> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comments::add);
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

    public void addComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
            .id(commentDto.getId())
            .text(commentDto.getText())
            .createdAt(commentDto.getCreatedAt())
            .postedById(commentDto.getPostedById())
            .build();
        commentRepository.save(comment);
        log.info("Comment {} is added to the Database", comment.getId());
    }

    public Optional<CommentDto> getComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(this::mapToCommentDto);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
        log.info("A Comment has been deleted");
    }

    public void updateComment(Long id, CommentDto commentDto) {
        Comment comment = Comment.builder()
            .id(commentDto.getId())
            .text(commentDto.getText())
            .createdAt(commentDto.getCreatedAt())
            .postedById(commentDto.getPostedById())
            .build();
        commentRepository.save(comment);
        log.info("Comment {} is updated", comment.getId());
    }
}
