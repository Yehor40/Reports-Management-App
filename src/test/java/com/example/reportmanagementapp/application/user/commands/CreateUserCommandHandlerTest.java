package com.example.reportmanagementapp.application.user.commands;

import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.domain.entity.Role;
import com.example.reportmanagementapp.domain.repository.RoleRepository;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserCommandHandler handler;

    @Test
    void handle_ShouldEncodePasswordAndSaveUserWithUserRole() {
        UserDto dto = new UserDto();
        dto.setName("New User");
        dto.setEmail("user@example.com");
        dto.setPassword("pass123");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        when(passwordEncoder.encode("pass123")).thenReturn("encoded_pass");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);

        handler.handle(dto);

        verify(passwordEncoder).encode("pass123");
        verify(userRepository).save(argThat(user -> 
            user.getName().equals("New User") &&
            user.getRoles().get(0).getName().equals("ROLE_USER")
        ));
    }
}
