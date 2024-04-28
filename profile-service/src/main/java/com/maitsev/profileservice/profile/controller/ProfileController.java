package com.maitsev.profileservice.profile.controller;

import java.util.List;
import java.util.Optional;

import com.maitsev.postservice.post.dto.PostDto;
import com.maitsev.profileservice.profile.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import com.maitsev.profileservice.profile.dto.ProfileDto;
import com.maitsev.profileservice.profile.service.ProfileService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profiles")
    public List<ProfileDto> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/profiles/{id}")
    public Optional<ProfileDto> getProfile(@PathVariable String id) {
        return profileService.getProfile(id);
    }

    @GetMapping("/profiles/{id}/posts")
    public List<PostDto> getProfilePosts(@PathVariable String id) {
        return profileService.getProfileAllPosts(id);
    }

    @GetMapping("/profiles/{id}/posts/{postid}")
    public Optional<PostDto> getSpecificProfilePost(@PathVariable String id, @PathVariable String postid) {
        return profileService.getSpecificProfilePost(id, postid);
    }

    @PostMapping("/profiles")
    public void addProfile(@RequestBody ProfileDto profileDto) {
        profileService.addProfile(profileDto);
    }

    @PutMapping("/profiles/{id}")
    public void updateProfile(@RequestBody ProfileDto profileDto, @PathVariable String id) {
        profileService.updateProfile(id, profileDto);
    }

    @DeleteMapping("/profiles/{id}")
    public void deleteProfile(@PathVariable String id) {
        profileService.deleteProfile(id);
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        profileService.sendMessageToOrderTopic(message);
        return ResponseEntity.ok("Message sent to the profile update topic");
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishjson(@RequestBody Profile profile){
        profileService.sendJsonToOrderTopic(profile);
        return ResponseEntity.ok("Json order object sent to the profile update topic");
    }



}
