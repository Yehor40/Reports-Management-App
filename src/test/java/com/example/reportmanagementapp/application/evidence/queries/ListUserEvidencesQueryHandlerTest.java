package com.example.reportmanagementapp.application.evidence.queries;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.mapper.EvidenceMapper;
import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUserEvidencesQueryHandlerTest {

    @Mock
    private EvidenceRepository evidenceRepository;

    @Mock
    private EvidenceMapper evidenceMapper;

    @InjectMocks
    private ListUserEvidencesQueryHandler handler;

    @Test
    void handle_ShouldReturnListOfDtosForUser() {
        Long userId = 1L;
        Evidence e1 = new Evidence();
        Evidence e2 = new Evidence();
        List<Evidence> evidenceList = Arrays.asList(e1, e2);

        when(evidenceRepository.findByUserId(userId)).thenReturn(evidenceList);
        when(evidenceMapper.toDto(any(Evidence.class))).thenReturn(new EvidenceDto());

        List<EvidenceDto> result = handler.handle(userId);

        assertThat(result).hasSize(2);
        verify(evidenceRepository).findByUserId(userId);
        verify(evidenceMapper, times(2)).toDto(any());
    }
}
