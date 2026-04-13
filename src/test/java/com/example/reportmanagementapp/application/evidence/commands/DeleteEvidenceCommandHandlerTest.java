package com.example.reportmanagementapp.application.evidence.commands;

import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteEvidenceCommandHandlerTest {

    @Mock
    private EvidenceRepository evidenceRepository;

    @InjectMocks
    private DeleteEvidenceCommandHandler handler;

    @Test
    void handle_WhenUserIsOwner_ShouldDelete() {
        Long evidenceId = 1L;
        Long userId = 10L;

        User owner = new User();
        owner.setId(userId);

        Evidence existingEvidence = new Evidence();
        existingEvidence.setUser(owner);

        when(evidenceRepository.findById(evidenceId)).thenReturn(Optional.of(existingEvidence));

        handler.handle(evidenceId, userId);

        verify(evidenceRepository).deleteById(evidenceId);
    }

    @Test
    void handle_WhenUserIsNotOwner_ShouldThrowException() {
        Long evidenceId = 1L;
        Long userId = 10L;
        Long otherUserId = 99L;

        User owner = new User();
        owner.setId(otherUserId);

        Evidence existingEvidence = new Evidence();
        existingEvidence.setUser(owner);

        when(evidenceRepository.findById(evidenceId)).thenReturn(Optional.of(existingEvidence));

        assertThatThrownBy(() -> handler.handle(evidenceId, userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unauthorized");

        verify(evidenceRepository, never()).deleteById(anyLong());
    }
}
