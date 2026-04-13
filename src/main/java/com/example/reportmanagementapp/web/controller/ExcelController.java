package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.evidence.queries.ListAllEvidencesQueryHandler;
import com.example.reportmanagementapp.application.evidence.queries.ListUserEvidencesQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import com.example.reportmanagementapp.infrastructure.reports.ExcelEvidenceExporter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelController {
    
    private final ExcelEvidenceExporter reportExporter;
    private final ListAllEvidencesQueryHandler listAllEvidencesQueryHandler;
    private final ListUserEvidencesQueryHandler listUserEvidencesQueryHandler;
    private final GetUserIdByUsernameQueryHandler getUserIdByUsernameQueryHandler;

    @GetMapping("/download/{id}")
    @PreAuthorize("@accessControl.canAccessEvidence(authentication.name, #id)")
    public void downloadSingleEvidenceExcel(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"evidence_" + id + ".xlsx\"");
        
        reportExporter.exportSingle(id, response.getOutputStream());
    }

    @GetMapping("/download/all")
    @PreAuthorize("hasRole('ADMIN')")
    public void downloadAllEvidencesExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"evidenceAll.xlsx\"");
        
        List<EvidenceDto> allEvidence = listAllEvidencesQueryHandler.handle();
        reportExporter.exportList(allEvidence, response.getOutputStream());
    }

    @GetMapping("/download/my")
    @PreAuthorize("hasRole('USER')")
    public void downloadMyEvidencesExcel(HttpServletResponse response, Principal principal) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"my_evidences.xlsx\"");
        
        Long userId = getUserIdByUsernameQueryHandler.handle(principal.getName());
        List<EvidenceDto> myEvidence = listUserEvidencesQueryHandler.handle(userId);
        reportExporter.exportList(myEvidence, response.getOutputStream());
    }
}
