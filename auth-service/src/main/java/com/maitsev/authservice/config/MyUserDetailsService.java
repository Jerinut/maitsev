package com.maitsev.authservice.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.maitsev.authservice.users.model.User;
import com.maitsev.authservice.users.repository.UserRepository;


@Component
public class MyUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
      Optional<User> user = userRepository.findByName(username);
      user
      .orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
            return user.map(MyUserDetails::new).get();
}
}