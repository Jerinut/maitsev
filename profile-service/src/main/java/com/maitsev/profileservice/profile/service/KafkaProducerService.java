package com.maitsev.profileservice.profile.service;

import com.maitsev.profileservice.profile.model.Profile;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Profile> jsonKafkaTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void sendMessageToProfileTopic(String message){
        //log.info("Message send to order topic: {} ", message);
        kafkaTemplate.send("ProfileUpdateTopic", message);
    }



    public void sendJsonToProfileTopic(Profile profile){
       // log.info("Log message - Send order json object to order topic: {} ", profile.toString());
        jsonKafkaTemplate.send("ProfileUpdateTopicJson", profile);
    }
}
