package com.example.reportmanagementapp.application.user.commands;

import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.domain.entity.Role;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.RoleRepository;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CreateUserCommandHandler {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    public void handle(UserDto userDto) {
        Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
            role = Role.builder()
                    .name("ROLE_USER")
                    .build();
            role = roleRepository.save(role);
        }

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Collections.singletonList(role))
                .build();
        
        userRepository.save(user);
    }
}
