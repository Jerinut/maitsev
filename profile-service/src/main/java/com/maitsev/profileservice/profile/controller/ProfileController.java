package com.maitsev.profileservice.profile.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import com.maitsev.profileservice.profile.dto.ProfileDto;
import com.maitsev.profileservice.profile.service.ProfileService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
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


}
