package com.maitsev.reviewservice.Review.configuration;

import org.springframework.context.annotation.Bean;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic ReviewTopicCreation() {
        return TopicBuilder.name("reviewTopic")
                .build();
    }

    @Bean
    public NewTopic ReviewTopicCreationJson() {
        return TopicBuilder.name("reviewTopicJson")
                .build();
    }
}
