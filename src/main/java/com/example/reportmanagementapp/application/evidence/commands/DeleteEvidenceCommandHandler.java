package com.example.reportmanagementapp.application.evidence.commands;

import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteEvidenceCommandHandler {
    
    @Autowired
    private EvidenceRepository evidenceRepository;
    
    public void handle(Long id, Long userId) {
        Evidence evidence = evidenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evidence not found"));
        
        if (!evidence.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You do not own this evidence.");
        }
        
        evidenceRepository.deleteById(id);
    }
}
