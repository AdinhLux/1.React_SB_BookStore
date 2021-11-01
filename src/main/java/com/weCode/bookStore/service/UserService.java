package com.weCode.bookStore.service;

import com.weCode.bookStore.dto.UserDto;
import com.weCode.bookStore.model.User;
import com.weCode.bookStore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // In case of if a null value is present
        user.setId(null);

        // Save into DB and auto generate ID
        User createdUser = userRepository.saveAndFlush(user);

        return createdUser.getId();
    }

    public UserDto getUserByEmail(String email) {
        User userByEmail = userRepository.findByEmail(email);

        if (Objects.isNull(userByEmail)) {
            throw new RuntimeException("User does not exist : " + email);
        }

        // If exists, convert to UserDto
        return modelMapper.map(userByEmail, UserDto.class);
    }
}
