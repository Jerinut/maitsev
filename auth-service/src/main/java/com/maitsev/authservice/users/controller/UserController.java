package com.maitsev.authservice.users.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.maitsev.authservice.jwt.JwtService;
import com.maitsev.authservice.users.dto.UserDto;
import com.maitsev.authservice.users.model.User;
import com.maitsev.authservice.users.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "http://localhost:8090" })
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> logInAndGetToken(@RequestBody UserDto userDto) {

        if (userDto.getName() == null || userDto.getPassword() == null) {
            throw new UsernameNotFoundException("Username or Password is Empty");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.getName(), userDto.getPassword()));

        if (authentication.isAuthenticated()) {
            String jwtToken = jwtService.generateToken(userDto.getName());
            Optional<User> userOptional = userService.findByName(userDto.getName());

            if (!userOptional.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }

            User user = userOptional.get();

            Map<String, Object> response = new HashMap<>();
            response.put("jwtToken", jwtToken);
            response.put("userId", user.getId());

            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("The user cannot be authenticated!");
        }
    }

    @GetMapping("/authenticate")
    public Boolean authenticate(@RequestHeader("Authorization") String header) {
        String token = header.replace("Bearer ", "");
        log.info(" authenticate - token {} ", token);
        return jwtService.validateToken(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signupUser(@RequestBody User user) {
        userService.addUser(user);
        String jwtToken = jwtService.generateToken(user.getName());

        Map<String, Object> response = new HashMap<>();
        response.put("jwtToken", jwtToken);
        response.put("userId", user.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/public")
    public String publicAPI() {
        log.info("This is an unprotected endpoint");
        return "This is an unprotected endpoint";
    }

    @GetMapping("/admin")
    public String adminAPI() {
        log.info("Protected endpoint - only admins are allowed");
        return "Protected endpoint - only admins are allowed";
    }

    @GetMapping("/user")
    public String userAPI() {
        log.info("Protected endpoint - only users are allowed");
        return "Protected endpoint - only users are allowed";
    }
}
