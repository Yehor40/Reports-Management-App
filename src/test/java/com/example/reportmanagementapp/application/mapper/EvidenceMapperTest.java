package com.example.reportmanagementapp.application.mapper;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EvidenceMapperTest {

    private EvidenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EvidenceMapper();
    }

    @Test
    void toDto_ShouldMapAllFields() {
        User user = new User();
        user.setId(10L);

        Evidence entity = new Evidence();
        entity.setId(1L);
        entity.setTaskName("Task 1");
        entity.setProject("Project A");
        entity.setOrderNum("ORD123");
        entity.setDepartment("Dept X");
        entity.setEstTime(5.0);
        entity.setTimeSpent(3.0);
        entity.setCharge(50.0);
        entity.setTotal(150.0);
        entity.setState("In Progress");
        entity.setMonth("April");
        entity.setUser(user);

        EvidenceDto dto = mapper.toDto(entity);

        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getTaskName()).isEqualTo(entity.getTaskName());
        assertThat(dto.getProject()).isEqualTo(entity.getProject());
        assertThat(dto.getUserId()).isEqualTo(10L);
    }

    @Test
    void toEntity_ShouldMapFields() {
        EvidenceDto dto = new EvidenceDto();
        dto.setTaskName("New Task");
        dto.setProject("New Project");

        Evidence entity = mapper.toEntity(dto);

        assertThat(entity.getTaskName()).isEqualTo(dto.getTaskName());
        assertThat(entity.getProject()).isEqualTo(dto.getProject());
    }

    @Test
    void updateEntity_ShouldUpdateFields() {
        Evidence entity = new Evidence();
        entity.setTaskName("Old Name");

        EvidenceDto dto = new EvidenceDto();
        dto.setTaskName("Updated Name");
        dto.setProject("Updated Project");

        mapper.updateEntity(dto, entity);

        assertThat(entity.getTaskName()).isEqualTo("Updated Name");
        assertThat(entity.getProject()).isEqualTo("Updated Project");
    }
}
