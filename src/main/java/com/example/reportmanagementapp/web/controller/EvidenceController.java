package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.evidence.commands.CreateEvidenceCommandHandler;
import com.example.reportmanagementapp.application.evidence.commands.DeleteEvidenceCommandHandler;
import com.example.reportmanagementapp.application.evidence.commands.UpdateEvidenceCommandHandler;
import com.example.reportmanagementapp.application.evidence.queries.GetEvidenceByIdQueryHandler;
import com.example.reportmanagementapp.application.evidence.queries.ListUserEvidencesQueryHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserIdByUsernameQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/evidences")
@RequiredArgsConstructor
public class EvidenceController {

    private final ListUserEvidencesQueryHandler listUserEvidencesQueryHandler;
    private final GetEvidenceByIdQueryHandler getEvidenceByIdQueryHandler;
    private final CreateEvidenceCommandHandler createEvidenceCommandHandler;
    private final UpdateEvidenceCommandHandler updateEvidenceCommandHandler;
    private final DeleteEvidenceCommandHandler deleteEvidenceCommandHandler;
    private final GetUserIdByUsernameQueryHandler getUserIdByUsernameQueryHandler;

    @GetMapping
    public ResponseEntity<List<EvidenceDto>> getAllEvidences(Principal principal) {
        Long userId = getUserIdByUsernameQueryHandler.handle(principal.getName());
        return ResponseEntity.ok(listUserEvidencesQueryHandler.handle(userId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@accessControl.canAccessEvidence(authentication.name, #id)")
    public ResponseEntity<EvidenceDto> getEvidenceById(@PathVariable Long id) {
        return getEvidenceByIdQueryHandler.handle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EvidenceDto> createEvidence(@RequestBody EvidenceDto evidenceDto, Principal principal) {
        Long userId = getUserIdByUsernameQueryHandler.handle(principal.getName());
        return ResponseEntity.ok(createEvidenceCommandHandler.handle(evidenceDto, userId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@accessControl.canAccessEvidence(authentication.name, #id)")
    public ResponseEntity<EvidenceDto> updateEvidence(@PathVariable Long id, @RequestBody EvidenceDto evidenceDto, Principal principal) {
        Long userId = getUserIdByUsernameQueryHandler.handle(principal.getName());
        return ResponseEntity.ok(updateEvidenceCommandHandler.handle(id, evidenceDto, userId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessControl.canAccessEvidence(authentication.name, #id)")
    public ResponseEntity<Void> deleteEvidence(@PathVariable Long id, Principal principal) {
        Long userId = getUserIdByUsernameQueryHandler.handle(principal.getName());
        deleteEvidenceCommandHandler.handle(id, userId);
        return ResponseEntity.noContent().build();
    }
}
