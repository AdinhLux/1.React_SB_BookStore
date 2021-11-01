package com.weCode.bookStore.service;

import com.weCode.bookStore.dto.UserDto;
import com.weCode.bookStore.model.User;
import com.weCode.bookStore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    public void shouldReturnUserIdWhenCalledWithUserData() {
        UUID id = UUID.randomUUID();

        // Mock UserRepository with any arguments
        when(userRepository.saveAndFlush(any())).thenReturn(getUser(id));
        when(modelMapper.map(any(), any())).thenReturn(getUser(id));

        // Call service
        UUID uuid = userService.addUser(getUserDto());

        assertThat(uuid).isNotNull();
        assertThat(uuid).isEqualTo(id);
    }

    @Test
    public void shouldReturnUserWhenEmailExists() {
        UUID id = UUID.randomUUID();

        // Mock UserRepository with any arguments
        when(userRepository.findByEmail(anyString())).thenReturn(getUser(id));
        when(modelMapper.map(any(), any())).thenReturn(getUserDto());

        // Call service
        UserDto userDto = userService.getUserByEmail("email");

        assertThat(userDto).isNotNull();
        assertThat(userDto.getName()).isEqualTo("username");
    }

    @Test
    public void shouldThrowErrorWhenEmailNotExists() {
        UUID id = UUID.randomUUID();

        // Mock UserRepository with any arguments
        when(userRepository.findByEmail(anyString())).thenThrow(new RuntimeException("error"));

        assertThatThrownBy(() -> userService.getUserByEmail("email")).isInstanceOf(RuntimeException.class);
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .password("password")
                .id(UUID.randomUUID())
                .name("username")
                .email("email")
                .build();
    }

    // Let's create fake User
    private User getUser(UUID uuid) {
        return User.builder()
                .password("password")
                .id(uuid)
                .name("username")
                .email("email")
                .build();
    }
}
