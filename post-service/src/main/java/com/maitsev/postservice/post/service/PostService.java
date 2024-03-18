package com.maitsev.postservice.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.maitsev.postservice.post.dto.PostDto;
import com.maitsev.postservice.post.model.Post;
import com.maitsev.postservice.post.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private WebClient.Builder webClientBuilder;

  public List<PostDto> getAllPosts() {
    List<Post> posts = new ArrayList<>();
    postRepository.findAll().forEach(posts::add);
    return posts.stream().map(this::mapToPostDto).toList();
  }

  private PostDto mapToPostDto(Post post) {
    return PostDto.builder()
        .id(post.getId())
        .description(post.getDescription())
        .imgUrl(post.getImgUrl())
        .likes(post.getLikes())
        .createdAt(post.getCreatedAt())
        .postedById(post.getPostedById())
        .build();
  }

  public void addPost(PostDto postDto) {
    Post post = Post.builder()
        .id(postDto.getId())
        .description(postDto.getDescription())
        .imgUrl(postDto.getImgUrl())
        .likes(postDto.getLikes())
        .createdAt(postDto.getCreatedAt())
        .postedById(postDto.getPostedById())
        .build();
    postRepository.save(post);
    log.info("Post {} is added to the Database", post.getId());
  }

  public Optional<PostDto> getPost(String id) {
    Optional<Post> post = postRepository.findById(id);
    return post.map(this::mapToPostDto);
  }

  public void deletePost(String id) {
    postRepository.deleteById(id);
    log.info("A Post has been deleted");
  }

  public void updatePost(String id, PostDto postDto) {
    Post post = Post.builder()
        .id(postDto.getId())
        .description(postDto.getDescription())
        .imgUrl(postDto.getImgUrl())
        .likes(postDto.getLikes())
        .createdAt(postDto.getCreatedAt())
        .postedById(postDto.getPostedById())
        .build();
    postRepository.save(post);
    log.info("Post {} is updated", post.getId());
  }
}
