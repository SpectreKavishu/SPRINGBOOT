package com.example.demo;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById_ReturnsUser_WhenUserExists() {
        // Arrange
        Long userId = 6L;
        User user = new User(userId, "John Doe", "john@ok.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findById(userId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetUserById_ReturnsEmpty_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 8L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findById(userId);

        // Assert
        assertThat(result).isNotPresent();
    }
}
