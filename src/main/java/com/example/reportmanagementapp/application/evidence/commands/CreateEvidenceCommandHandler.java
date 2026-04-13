package com.example.reportmanagementapp.application.evidence.commands;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.mapper.EvidenceMapper;
import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateEvidenceCommandHandler {
    
    @Autowired
    private EvidenceRepository evidenceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EvidenceMapper evidenceMapper;
    
    public EvidenceDto handle(EvidenceDto command, Long userId) {
        Evidence evidence = evidenceMapper.toEntity(command);
        evidence.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
        
        Evidence saved = evidenceRepository.save(evidence);
        return evidenceMapper.toDto(saved);
    }
}
