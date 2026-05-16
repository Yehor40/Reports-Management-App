package com.example.reportmanagementapp.infrastructure.security;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.evidence.queries.GetEvidenceByIdQueryHandler;
import com.example.reportmanagementapp.application.user.queries.FindUserByEmailQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.entity.Role;
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
    
    @Mock
    private FindUserByEmailQueryHandler findUserByEmailQueryHandler;

    @InjectMocks
    private AccessControlServiceImpl accessControlService;

    @Test
    void canAccessEvidence_WhenUserIsOwner_ShouldReturnTrue() {
        String email = "owner@example.com";
        Long evidenceId = 1L;
        Long userId = 10L;

        EvidenceDto dto = new EvidenceDto();
        dto.setUserId(userId);

        User user = User.builder().id(userId).email(email).roles(new java.util.ArrayList<>()).build();

        when(getEvidenceByIdQueryHandler.handle(evidenceId)).thenReturn(Optional.of(dto));
        when(findUserByEmailQueryHandler.handle(email)).thenReturn(user);

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

        User user = User.builder().id(otherId).email(email).roles(new java.util.ArrayList<>()).build();

        when(getEvidenceByIdQueryHandler.handle(evidenceId)).thenReturn(Optional.of(dto));
        when(findUserByEmailQueryHandler.handle(email)).thenReturn(user);

        boolean result = accessControlService.canAccessEvidence(email, evidenceId);

        assertThat(result).isFalse();
    }

    @Test
    void canAccessUser_WhenItIsSelf_ShouldReturnTrue() {
        String email = "self@example.com";
        Long userId = 10L;

        User user = User.builder().id(userId).email(email).roles(new java.util.ArrayList<>()).build();
        when(findUserByEmailQueryHandler.handle(email)).thenReturn(user);

        boolean result = accessControlService.canAccessUser(email, userId);

        assertThat(result).isTrue();
    }

    @Test
    void canAccessEvidence_WhenUserIsAdmin_ShouldReturnTrue() {
        String email = "admin@example.com";
        Long evidenceId = 1L;
        Long ownerId = 10L;

        EvidenceDto dto = new EvidenceDto();
        dto.setUserId(ownerId);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        User admin = User.builder()
                .id(99L)
                .email(email)
                .roles(java.util.List.of(adminRole))
                .build();

        when(getEvidenceByIdQueryHandler.handle(evidenceId)).thenReturn(Optional.of(dto));
        when(findUserByEmailQueryHandler.handle(email)).thenReturn(admin);

        boolean result = accessControlService.canAccessEvidence(email, evidenceId);

        assertThat(result).isTrue();
    }
}
