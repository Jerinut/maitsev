package com.maitsev.profileservice.profile.controller;

import com.maitsev.profileservice.profile.model.Profile;
import com.maitsev.profileservice.profile.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kafka")
public class ProducerController {

    @Autowired
    private KafkaProducerService producerService;


    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        producerService.sendMessageToProfileTopic(message);
        return ResponseEntity.ok("Message sent to the Profile topic");
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishjson(@RequestBody Profile profile){
        producerService.sendJsonToProfileTopic(profile);
        return ResponseEntity.ok("Json order object sent to the Profile topic");
    }

}
