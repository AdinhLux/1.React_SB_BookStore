package com.weCode.bookStore.service;

import com.weCode.bookStore.dto.UserDto;
import com.weCode.bookStore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    // To encode password into DB
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UUID addUser(UserDto userDto) {
        return UUID.randomUUID();
    }

    public UserDto getUserByEmail(String email) {
        return new UserDto();
    }
}
