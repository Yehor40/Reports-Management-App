package com.example.reportmanagementapp.application.evidence.queries;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.mapper.EvidenceMapper;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetEvidenceByIdQueryHandler {
    
    @Autowired
    private EvidenceRepository evidenceRepository;
    
    @Autowired
    private EvidenceMapper evidenceMapper;
    
    public Optional<EvidenceDto> handle(Long id) {
        return evidenceRepository.findById(id).map(evidenceMapper::toDto);
    }
}
