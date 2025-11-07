package org.springframeworkcore.mvc.javaannotationbased.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframeworkcore.mvc.javaannotationbased.dao.UserRepository;
import org.springframeworkcore.mvc.javaannotationbased.service.CustomUserDetailsService;

@Service
public class CustomUserDetailsServiceImplementation implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}

