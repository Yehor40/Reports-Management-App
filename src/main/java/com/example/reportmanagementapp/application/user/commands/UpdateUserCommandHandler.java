package com.example.reportmanagementapp.application.user.commands;

import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserCommandHandler {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public void handle(Long id, UserDto updatedUserDto) {
        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setName(updatedUserDto.getName());
        existingUser.setEmail(updatedUserDto.getEmail());
        
        if (updatedUserDto.getPassword() != null && !updatedUserDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUserDto.getPassword()));
        }
        
        userRepository.save(existingUser);
    }
}
