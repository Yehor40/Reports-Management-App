package com.example.reportmanagementapp.application.user.queries;

import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.application.mapper.UserMapper;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListAllUsersQueryHandler {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public List<UserDto> handle() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
