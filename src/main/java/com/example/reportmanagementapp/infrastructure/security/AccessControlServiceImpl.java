package com.example.reportmanagementapp.infrastructure.security;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.evidence.queries.GetEvidenceByIdQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("accessControl")
public class AccessControlServiceImpl implements AccessControlService {

    @Autowired
    private GetEvidenceByIdQueryHandler getEvidenceByIdQueryHandler;

    @Autowired
    private GetUserIdByUsernameQueryHandler getUserIdByUsernameQueryHandler;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean canAccessEvidence(String email, Long evidenceId) {
        System.out.println("DEBUG: Checking access for email [" + email + "] to evidenceId [" + evidenceId + "]");
        
        Optional<EvidenceDto> evidence = getEvidenceByIdQueryHandler.handle(evidenceId);
        if (evidence.isEmpty()) {
            System.out.println("DEBUG: Access Denied - Evidence not found: " + evidenceId);
            return false;
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("DEBUG: Access Denied - User not found: " + email);
            return false;
        }

        // Admins can access everything
        boolean isAdmin = user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"));
        if (isAdmin) {
            System.out.println("DEBUG: Access Granted - User is ADMIN");
            return true;
        }
        
        Long currentUserId = user.getId();
        Long ownerId = evidence.get().getUserId();
        
        boolean isOwner = currentUserId != null && currentUserId.equals(ownerId);
        System.out.println("DEBUG: Access result for " + email + ": isOwner=" + isOwner + " (Current: " + currentUserId + ", Owner: " + ownerId + ")");
        
        return isOwner;
    }

    @Override
    public boolean canAccessUser(String email, Long userId) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;
        
        boolean isAdmin = user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"));
        if (isAdmin) return true;
        
        return user.getId().equals(userId);
    }
}
