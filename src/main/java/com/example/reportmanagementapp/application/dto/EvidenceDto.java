package com.example.reportmanagementapp.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceDto {
    private Long id;
    private String taskName;
    private String project;
    private String orderNum;
    private String department;
    private double estTime;
    private double timeSpent;
    private double charge;
    private double total;
    private String state;
    private String month;
    private Long userId;
}
