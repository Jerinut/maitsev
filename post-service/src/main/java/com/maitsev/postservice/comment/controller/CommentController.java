package com.maitsev.postservice.comment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.maitsev.postservice.comment.dto.CommentDto;
import com.maitsev.postservice.comment.service.CommentService;
import com.maitsev.postservice.post.service.PostService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{id}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable String id) {
        return commentService.getAllCommentsByPostId(id);
    }

    @GetMapping("/posts/{id}/comments/{commentId}")
    public Optional<CommentDto> getComment(@PathVariable String id, @PathVariable String commentId) {
        return commentService.getComment(id, commentId);
    }

    @PostMapping("/posts/{id}/comments")
    public void addComment(@RequestBody CommentDto commentDto, @PathVariable String id) {
        commentService.addComment(commentDto, id);
    }

    @PutMapping("/posts/{id}/comments/{commentId}")
    public void updateComment(@RequestBody CommentDto commentDto, @PathVariable String id,
            @PathVariable String commentId) {
        commentService.updateComment(id, commentId, commentDto);
    }

    @DeleteMapping("/posts/{id}/comments/{commentId}")
    public void deleteComment(@PathVariable String id, @PathVariable String commentId) {
        commentService.deleteComment(id, commentId);
    }
}
