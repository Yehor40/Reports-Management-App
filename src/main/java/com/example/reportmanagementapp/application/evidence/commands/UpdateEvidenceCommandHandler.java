package com.example.reportmanagementapp.application.evidence.commands;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.mapper.EvidenceMapper;
import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateEvidenceCommandHandler {
    
    @Autowired
    private EvidenceRepository evidenceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EvidenceMapper evidenceMapper;
    
    public EvidenceDto handle(Long id, EvidenceDto updatedDto, Long userId) {
        Evidence existingEvidence = evidenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evidence not found"));
        
        // Security check: Ensure the user owns this evidence
        if (!existingEvidence.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You do not own this evidence.");
        }
        
        evidenceMapper.updateEntity(updatedDto, existingEvidence);

        Evidence saved = evidenceRepository.save(existingEvidence);
        return evidenceMapper.toDto(saved);
    }
}
