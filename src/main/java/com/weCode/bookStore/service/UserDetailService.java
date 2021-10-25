package com.weCode.bookStore.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {

    /**
     * Password encryptor
     */
    private final PasswordEncoder passwordEncoder;

    public UserDetailService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Spring Security method to load User from DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("peter@gmail.com",passwordEncoder.encode("dummy password"), new ArrayList<>());
    }
}
