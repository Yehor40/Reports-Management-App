package com.example.reportmanagementapp.application.evidence.commands;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.mapper.EvidenceMapper;
import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateEvidenceCommandHandlerTest {

    @Mock
    private EvidenceRepository evidenceRepository;

    @Mock
    private EvidenceMapper evidenceMapper;

    @InjectMocks
    private UpdateEvidenceCommandHandler handler;

    @Test
    void handle_WhenUserIsOwner_ShouldUpdateAndReturnDto() {
        Long evidenceId = 1L;
        Long userId = 10L;
        
        User owner = new User();
        owner.setId(userId);

        Evidence existingEvidence = new Evidence();
        existingEvidence.setId(evidenceId);
        existingEvidence.setUser(owner);

        EvidenceDto inputDto = new EvidenceDto();
        inputDto.setTaskName("Updated Task");

        when(evidenceRepository.findById(evidenceId)).thenReturn(Optional.of(existingEvidence));
        when(evidenceRepository.save(any(Evidence.class))).thenReturn(existingEvidence);
        when(evidenceMapper.toDto(any(Evidence.class))).thenReturn(inputDto);

        EvidenceDto result = handler.handle(evidenceId, inputDto, userId);

        assertThat(result).isNotNull();
        verify(evidenceMapper).updateEntity(inputDto, existingEvidence);
        verify(evidenceRepository).save(existingEvidence);
    }

    @Test
    void handle_WhenUserIsNotOwner_ShouldThrowException() {
        Long evidenceId = 1L;
        Long userId = 10L;
        Long otherUserId = 99L;

        User owner = new User();
        owner.setId(otherUserId);

        Evidence existingEvidence = new Evidence();
        existingEvidence.setId(evidenceId);
        existingEvidence.setUser(owner);

        EvidenceDto inputDto = new EvidenceDto();

        when(evidenceRepository.findById(evidenceId)).thenReturn(Optional.of(existingEvidence));

        assertThatThrownBy(() -> handler.handle(evidenceId, inputDto, userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unauthorized");

        verify(evidenceRepository, never()).save(any());
    }
}
