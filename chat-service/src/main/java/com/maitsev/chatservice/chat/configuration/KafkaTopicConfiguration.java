package com.maitsev.chatservice.chat.configuration;

import org.springframework.context.annotation.Bean;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic ChatTopicCreation(){
        return TopicBuilder.name("chatTopic")
                .build();
    }


    @Bean
    public NewTopic ChatTopicCreationJson(){
        return TopicBuilder.name("chatTopicJson")
                .build();
    }

    @Bean
    public NewTopic MessageTopicCreationJson(){
        return TopicBuilder.name("messageTopicJson")
                .build();
    }
}

