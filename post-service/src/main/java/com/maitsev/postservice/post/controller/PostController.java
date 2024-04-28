package com.maitsev.postservice.post.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.maitsev.postservice.post.dto.PostDto;
import com.maitsev.postservice.post.service.PostService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class PostController {

  @Autowired
  private PostService postservice;

  @GetMapping("/posts")
  public List<PostDto> getAllPosts() {
    return postservice.getAllPosts();
  }

  @GetMapping("/posts/{id}")
  public Optional<PostDto> getPost(@PathVariable String id) {
    return postservice.getPost(id);
  }

  @PostMapping("/posts")
  public void addPost(@RequestBody PostDto postDto) {
    postservice.addPost(postDto);
  }

  @PutMapping("/posts/{id}")
  public void updatePost(@RequestBody PostDto postDto, @PathVariable String id) {
    postservice.updatePost(id, postDto);
  }

  @DeleteMapping("/posts/{id}")
  public void deletePost(@PathVariable String id) {
    postservice.deletePost(id);
  }

  @GetMapping("/profiles/{id}/posts")
  public List<PostDto> getProfilePosts(@PathVariable String id) {
    return postservice.getProfileAllPosts(id);
  }

  @GetMapping("/profiles/{id}/posts/{postid}")
  public Optional<PostDto> getSpecificProfilePost(@PathVariable String id, @PathVariable String postid) {
    return postservice.getSpecificProfilePost(id, postid);
  }
}
