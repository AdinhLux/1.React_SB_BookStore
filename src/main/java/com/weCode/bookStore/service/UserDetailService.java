package com.weCode.bookStore.service;

import com.weCode.bookStore.dto.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {


    private final UserService userService;

    public UserDetailService(UserService userService) {
        this.userService = userService;
    }

    // Spring Security method to load User from DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userByEmail = userService.getUserByEmail(username);
        return new User(userByEmail.getEmail(), userByEmail.getPassword(), new ArrayList<>());
    }
}
