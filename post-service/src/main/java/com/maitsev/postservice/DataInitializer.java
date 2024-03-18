package com.maitsev.postservice;

import com.maitsev.postservice.post.model.Post;
import com.maitsev.postservice.post.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner loadProductsData(PostRepository postRepository) {
        return args -> {
            Post post1 = new Post();
            post1.setId("01");
            post1.setDescription("hello! this is the first post");
            post1.setImgUrl("imgurl");
            post1.setLikes(12);
            post1.setPostedById("01");
            postRepository.save(post1);

        };
    }
}
