package com.example.reportmanagementapp.application.user.commands;

import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserCommandHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdateUserCommandHandler handler;

    @Test
    void handle_WithNewPassword_ShouldEncodeAndSave() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setPassword("old_pass");

        UserDto dto = new UserDto();
        dto.setName("New Name");
        dto.setEmail("new@example.com");
        dto.setPassword("new_pass");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("new_pass")).thenReturn("encoded_new_pass");

        handler.handle(userId, dto);

        assertThat(existingUser.getName()).isEqualTo("New Name");
        assertThat(existingUser.getPassword()).isEqualTo("encoded_new_pass");
        verify(userRepository).save(existingUser);
    }

    @Test
    void handle_WithoutNewPassword_ShouldNotChangePassword() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setPassword("old_pass");

        UserDto dto = new UserDto();
        dto.setName("New Name");
        dto.setEmail("new@example.com");
        dto.setPassword(""); // Empty password

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        handler.handle(userId, dto);

        assertThat(existingUser.getPassword()).isEqualTo("old_pass");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository).save(existingUser);
    }
}
