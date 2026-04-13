package com.example.reportmanagementapp.application.user.queries;

import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.application.mapper.UserMapper;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserByIdQueryHandler {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public Optional<UserDto> handle(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }
}
