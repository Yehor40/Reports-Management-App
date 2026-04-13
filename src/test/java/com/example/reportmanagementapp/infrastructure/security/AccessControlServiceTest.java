package com.example.reportmanagementapp.infrastructure.security;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.evidence.queries.GetEvidenceByIdQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessControlServiceTest {

    @Mock
    private GetEvidenceByIdQueryHandler getEvidenceByIdQueryHandler;

    @Mock
    private GetUserIdByUsernameQueryHandler getUserIdByUsernameQueryHandler;

    @InjectMocks
    private AccessControlServiceImpl accessControlService;

    @Test
    void canAccessEvidence_WhenUserIsOwner_ShouldReturnTrue() {
        String email = "owner@example.com";
        Long evidenceId = 1L;
        Long userId = 10L;

        EvidenceDto dto = new EvidenceDto();
        dto.setUserId(userId);

        when(getEvidenceByIdQueryHandler.handle(evidenceId)).thenReturn(Optional.of(dto));
        when(getUserIdByUsernameQueryHandler.handle(email)).thenReturn(userId);

        boolean result = accessControlService.canAccessEvidence(email, evidenceId);

        assertThat(result).isTrue();
    }

    @Test
    void canAccessEvidence_WhenUserIsNotOwner_ShouldReturnFalse() {
        String email = "other@example.com";
        Long evidenceId = 1L;
        Long ownerId = 10L;
        Long otherId = 99L;

        EvidenceDto dto = new EvidenceDto();
        dto.setUserId(ownerId);

        when(getEvidenceByIdQueryHandler.handle(evidenceId)).thenReturn(Optional.of(dto));
        when(getUserIdByUsernameQueryHandler.handle(email)).thenReturn(otherId);

        boolean result = accessControlService.canAccessEvidence(email, evidenceId);

        assertThat(result).isFalse();
    }

    @Test
    void canAccessUser_WhenItIsSelf_ShouldReturnTrue() {
        String email = "self@example.com";
        Long userId = 10L;

        when(getUserIdByUsernameQueryHandler.handle(email)).thenReturn(userId);

        boolean result = accessControlService.canAccessUser(email, userId);

        assertThat(result).isTrue();
    }
}
