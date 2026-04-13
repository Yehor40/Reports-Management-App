package com.example.reportmanagementapp.infrastructure.security;

/**
 * Interface for centralizing access control logic.
 * SOLID: SRP - separates authorization logic from controllers.
 */
public interface AccessControlService {
    boolean canAccessEvidence(String email, Long evidenceId);
    boolean canAccessUser(String email, Long userId);
}
