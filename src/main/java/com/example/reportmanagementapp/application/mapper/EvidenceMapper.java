package com.example.reportmanagementapp.application.mapper;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.domain.entity.Evidence;
import org.springframework.stereotype.Component;

@Component
public class EvidenceMapper {

    public EvidenceDto toDto(Evidence entity) {
        if (entity == null) return null;
        return EvidenceDto.builder()
                .id(entity.getId())
                .taskName(entity.getTaskName())
                .project(entity.getProject())
                .orderNum(entity.getOrderNum())
                .department(entity.getDepartment())
                .estTime(entity.getEstTime())
                .timeSpent(entity.getTimeSpent())
                .charge(entity.getCharge())
                .total(entity.getTotal())
                .state(entity.getState())
                .month(entity.getMonth())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .build();
    }

    public Evidence toEntity(EvidenceDto dto) {
        if (dto == null) return null;
        Evidence evidence = Evidence.builder()
                .id(dto.getId())
                .taskName(dto.getTaskName())
                .project(dto.getProject())
                .orderNum(dto.getOrderNum())
                .department(dto.getDepartment())
                .estTime(dto.getEstTime())
                .timeSpent(dto.getTimeSpent())
                .charge(dto.getCharge())
                .total(dto.getTotal())
                .state(dto.getState())
                .month(dto.getMonth())
                .build();
        return evidence;
    }

    public void updateEntity(EvidenceDto dto, Evidence entity) {
        if (dto == null || entity == null) return;
        entity.setTaskName(dto.getTaskName());
        entity.setProject(dto.getProject());
        entity.setOrderNum(dto.getOrderNum());
        entity.setDepartment(dto.getDepartment());
        entity.setEstTime(dto.getEstTime());
        entity.setTimeSpent(dto.getTimeSpent());
        entity.setCharge(dto.getCharge());
        entity.setTotal(dto.getTotal());
        entity.setState(dto.getState());
        entity.setMonth(dto.getMonth());
    }
}
