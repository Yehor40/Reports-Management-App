package com.example.reportmanagementapp.application.user.queries;

import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserIdByUsernameQueryHandler {
    
    @Autowired
    private UserRepository userRepository;
    
    public Long handle(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getId();
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }
}
