package com.example.reportmanagementapp.application.user.queries;

import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindUserByEmailQueryHandler {
    
    @Autowired
    private UserRepository userRepository;
    
    public User handle(String email) {
        return userRepository.findByEmail(email);
    }
}
