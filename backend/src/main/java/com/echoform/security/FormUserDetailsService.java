package com.echoform.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class FormUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We expect usernames like "form:123"
        if (username == null || !username.startsWith("form:")) {
             throw new UsernameNotFoundException("Invalid user principal: " + username);
        }
        
        return new User(
            username, 
            "{noop}nopassword", // We don't use passwords, but User object requires one. {noop} means plain text (ignored).
            Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
