package com.example.reportmanagementapp.application.evidence.queries;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.mapper.EvidenceMapper;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAllEvidencesQueryHandler {
    
    @Autowired
    private EvidenceRepository evidenceRepository;
    
    @Autowired
    private EvidenceMapper evidenceMapper;
    
    public List<EvidenceDto> handle() {
        return evidenceRepository.findAll().stream()
                .map(evidenceMapper::toDto)
                .collect(Collectors.toList());
    }
}
