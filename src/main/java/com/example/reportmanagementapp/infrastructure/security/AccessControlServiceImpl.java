package com.example.reportmanagementapp.infrastructure.security;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.evidence.queries.GetEvidenceByIdQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("accessControl")
public class AccessControlServiceImpl implements AccessControlService {

    @Autowired
    private GetEvidenceByIdQueryHandler getEvidenceByIdQueryHandler;

    @Autowired
    private GetUserIdByUsernameQueryHandler getUserIdByUsernameQueryHandler;

    @Override
    public boolean canAccessEvidence(String email, Long evidenceId) {
        Optional<EvidenceDto> evidence = getEvidenceByIdQueryHandler.handle(evidenceId);
        if (evidence.isEmpty()) {
            return false;
        }
        
        Long currentUserId = getUserIdByUsernameQueryHandler.handle(email);
        return evidence.get().getUserId().equals(currentUserId);
    }

    @Override
    public boolean canAccessUser(String email, Long userId) {
        Long currentUserId = getUserIdByUsernameQueryHandler.handle(email);
        return userId.equals(currentUserId);
    }
}
