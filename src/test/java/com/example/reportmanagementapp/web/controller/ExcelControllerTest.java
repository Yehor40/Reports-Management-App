package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.evidence.queries.ListAllEvidencesQueryHandler;
import com.example.reportmanagementapp.application.evidence.queries.ListUserEvidencesQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import com.example.reportmanagementapp.infrastructure.reports.ExcelEvidenceExporter;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExcelController.class)
@AutoConfigureMockMvc(addFilters = false)
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
    @WithMockUser
    void downloadAllEvidences_ShouldSucceed() throws Exception {
        when(listAllEvidencesQueryHandler.handle()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/excel/download/all"))
                .andExpect(status().isOk());

        verify(reportExporter).exportList(any(), any());
    }

    @Test
    @WithMockUser(username = "user@example.com")
    void downloadMyEvidences_ShouldSucceed() throws Exception {
        when(getUserIdByUsernameQueryHandler.handle("user@example.com")).thenReturn(1L);
        when(listUserEvidencesQueryHandler.handle(1L)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/excel/download/my"))
                .andExpect(status().isOk());

        verify(reportExporter).exportList(any(), any());
    }
}
