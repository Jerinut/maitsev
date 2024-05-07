package com.maitsev.postservice.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
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
    PostDto postDto = PostDto.builder()
        .id(post.getId())
        .description(post.getDescription())
        .imgUrl(post.getImgUrl())
        .likes(post.getLikes())
        .createdAt(post.getCreatedAt())

        .postedById(post.getPostedById())
        .build();
    Hibernate.initialize(postDto.getTags());
    return postDto;
  }

  public void addPost(PostDto postDto) {
    Post post = Post.builder()
        .id(postDto.getId())
        .description(postDto.getDescription())
        .imgUrl(postDto.getImgUrl())
        .likes(postDto.getLikes())
        .createdAt(postDto.getCreatedAt())
        .postedById(postDto.getPostedById())
        .tags(postDto.getTags())
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
        .tags(postDto.getTags())
        .build();
    postRepository.save(post);
    log.info("Post {} is updated", post.getId());
  }

  public List<PostDto> getProfileAllPosts(String id) {
    List<PostDto> allPosts = webClientBuilder
        .build()
        .get()
        .uri("http://localhost:8001/api/posts")
        .retrieve()
        .bodyToFlux(PostDto.class)
        .collectList()
        .block();

    return allPosts.stream()
        .filter(post -> id.equals(post.getPostedById()))
        .collect(Collectors.toList());
  }

  public Optional<PostDto> getSpecificProfilePost(String id, String postId) {
    List<PostDto> allPosts = getProfileAllPosts(id);
    return allPosts.stream()
        .filter(post -> post.getId().equals(postId))
        .findFirst();
  }
}
