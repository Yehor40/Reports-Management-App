package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.evidence.queries.ListAllEvidencesQueryHandler;
import com.example.reportmanagementapp.application.evidence.queries.ListUserEvidencesQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import com.example.reportmanagementapp.infrastructure.reports.ExcelEvidenceExporter;
import com.example.reportmanagementapp.infrastructure.security.AccessControlService;
import com.example.reportmanagementapp.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExcelController.class)
class ExcelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExcelEvidenceExporter reportExporter;

    @MockBean
    private ListAllEvidencesQueryHandler listAllEvidencesQueryHandler;

    @MockBean
    private ListUserEvidencesQueryHandler listUserEvidencesQueryHandler;

    @MockBean
    private GetUserIdByUsernameQueryHandler getUserIdByUsernameQueryHandler;

    @MockBean
    private com.example.reportmanagementapp.application.evidence.queries.GetEvidenceByIdQueryHandler getEvidenceByIdQueryHandler;

    @MockBean(name = "accessControl")
    private AccessControlService accessControlService;
    
    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "user@example.com")
    void downloadSingleEvidence_ShouldSucceed() throws Exception {
        Long evidenceId = 1L;
        when(accessControlService.canAccessEvidence("user@example.com", evidenceId)).thenReturn(true);

        mockMvc.perform(get("/api/excel/download/" + evidenceId))
                .andExpect(status().isOk());

        verify(reportExporter).exportSingle(eq(evidenceId), any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void downloadAllEvidences_ShouldSucceed() throws Exception {
        when(listAllEvidencesQueryHandler.handle()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/excel/download/all"))
                .andExpect(status().isOk());

        verify(reportExporter).exportList(any(), any());
    }

    @Test
    @WithMockUser(username = "admin@example.com")
    void downloadMyEvidences_ShouldSucceed() throws Exception {
        when(getUserIdByUsernameQueryHandler.handle("admin@example.com")).thenReturn(1L);
        when(listUserEvidencesQueryHandler.handle(1L)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/excel/download/my").with(user("admin@example.com").roles("ADMIN")))
                .andExpect(status().isOk());

        verify(reportExporter).exportList(any(), any());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void downloadMyEvidences_AsAdmin_ShouldSucceed() throws Exception {
        when(getUserIdByUsernameQueryHandler.handle("admin@example.com")).thenReturn(1L);
        when(listUserEvidencesQueryHandler.handle(1L)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/excel/download/my"))
                .andExpect(status().isOk());

        verify(reportExporter).exportList(any(), any());
    }
    @Test
    @WithMockUser(username = "user@example.com")
    void downloadSelectedEvidences_ShouldSucceed() throws Exception {
        java.util.List<Long> ids = java.util.List.of(1L, 2L);
        when(accessControlService.canAccessEvidence("user@example.com", 1L)).thenReturn(true);
        when(accessControlService.canAccessEvidence("user@example.com", 2L)).thenReturn(true);
        
        mockMvc.perform(post("/api/excel/download/selected")
                        .contentType("application/json")
                        .content("[1, 2]")
                        .with(user("user@example.com").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(reportExporter).exportList(any(), any());
    }
}
