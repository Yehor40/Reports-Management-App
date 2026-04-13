package com.example.reportmanagementapp.application.user.commands;

import com.example.reportmanagementapp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserCommandHandler {
    
    @Autowired
    private UserRepository userRepository;
    
    public void handle(Long id) {
        userRepository.deleteById(id);
    }
}
