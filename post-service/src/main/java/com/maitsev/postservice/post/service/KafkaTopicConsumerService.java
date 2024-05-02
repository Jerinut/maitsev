package com.maitsev.postservice.post.service;

import com.maitsev.postservice.comment.dto.ProfileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaTopicConsumerService {
    @KafkaListener(topics = "ProfileUpdateTopic", groupId = "ProfileUpdateEventGroup")
    public void consume(String message){
        log.info("Log message - Message recieved from order topic: {} ", message);
    }


    @KafkaListener(topics = "ProfileUpdateTopicJson", groupId = "ProfileUpdateEventGroup" )
    public void consume(ProfileDto profile){
        log.info("Log message recieved from order topic: {} ", profile.toString());
    }
}
