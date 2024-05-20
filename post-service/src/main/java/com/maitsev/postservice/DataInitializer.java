package com.maitsev.postservice;

import com.maitsev.postservice.comment.model.Comment;
import com.maitsev.postservice.comment.repository.CommentRepository;
import com.maitsev.postservice.post.model.Post;
import com.maitsev.postservice.post.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner loadData(PostRepository postRepository, CommentRepository commentRepository) {
        return args -> {
            Post post1 = new Post();
            // post1.setId("01");
            post1.setDescription("hello! this is the first post");
            post1.setImgUrl("imgurl");
            post1.setLikes(Arrays.asList("01", "02"));
            post1.setPostedById("01");
            post1.setTags(Arrays.asList("Vegan", "Spicy"));
            postRepository.save(post1);

            Comment comment1 = new Comment();
            // comment1.setId("01");
            comment1.setText("This is a comment on the first post");
            comment1.setCreatedAt(LocalDateTime.now());
            comment1.setPostedById("02");
            comment1.setPost(post1);
            commentRepository.save(comment1);

            Comment comment2 = new Comment();
            comment2.setId("02");
            comment2.setText("This is another comment on the first post");
            comment2.setCreatedAt(LocalDateTime.now());
            comment2.setPostedById("03");
            comment2.setPost(post1);
            commentRepository.save(comment2);
        };
    }
}
