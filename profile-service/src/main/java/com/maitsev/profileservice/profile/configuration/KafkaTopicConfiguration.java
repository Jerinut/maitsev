package com.maitsev.profileservice.profile.configuration;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic ProfileUpdateTopic(){
        return TopicBuilder.name("ProfileUpdateTopic")
                .build();
    }
//    @Bean
//    public NewTopic PaymentTopicCreation(){
//        return TopicBuilder.name("paymentTopic")
//                .build();
//    }
}
