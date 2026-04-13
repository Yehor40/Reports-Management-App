package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.evidence.commands.CreateEvidenceCommandHandler;
import com.example.reportmanagementapp.application.evidence.commands.DeleteEvidenceCommandHandler;
import com.example.reportmanagementapp.application.evidence.commands.UpdateEvidenceCommandHandler;
import com.example.reportmanagementapp.application.evidence.queries.GetEvidenceByIdQueryHandler;
import com.example.reportmanagementapp.application.evidence.queries.ListUserEvidencesQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import com.example.reportmanagementapp.infrastructure.security.AccessControlService;
import com.example.reportmanagementapp.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EvidenceController.class)
@AutoConfigureMockMvc(addFilters = false)
class EvidenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListUserEvidencesQueryHandler listUserEvidencesQueryHandler;
    
    @MockBean
    private GetEvidenceByIdQueryHandler getEvidenceByIdQueryHandler;
    
    @MockBean(name = "accessControl")
    private AccessControlService accessControlService;
    
    @MockBean
    private CreateEvidenceCommandHandler createEvidenceCommandHandler;
    
    @MockBean
    private UpdateEvidenceCommandHandler updateEvidenceCommandHandler;
    
    @MockBean
    private DeleteEvidenceCommandHandler deleteEvidenceCommandHandler;
    
    @MockBean
    private GetUserIdByUsernameQueryHandler getUserIdByUsernameQueryHandler;
    
    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "user@example.com")
    void getAllEvidences_ShouldReturnOk() throws Exception {
        when(getUserIdByUsernameQueryHandler.handle(anyString())).thenReturn(1L);
        when(listUserEvidencesQueryHandler.handle(1L)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/evidences"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@example.com")
    void getEvidenceById_ShouldReturnOk() throws Exception {
        Long evidenceId = 1L;
        EvidenceDto dto = new EvidenceDto();
        dto.setId(evidenceId);

        when(accessControlService.canAccessEvidence("user@example.com", evidenceId)).thenReturn(true);
        when(getEvidenceByIdQueryHandler.handle(evidenceId)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/evidences/" + evidenceId))
                .andExpect(status().isOk());
    }
}
