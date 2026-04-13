package com.example.reportmanagementapp.infrastructure.reports;

import com.example.reportmanagementapp.application.dto.EvidenceDto;
import com.example.reportmanagementapp.application.evidence.queries.GetEvidenceByIdQueryHandler;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class ExcelEvidenceExporter implements ReportExporter {
    
    @Autowired
    private GetEvidenceByIdQueryHandler getEvidenceByIdQueryHandler;

    @Override
    public void exportSingle(Long evidenceId, OutputStream outputStream) throws IOException {
        EvidenceDto evidence = getEvidenceByIdQueryHandler.handle(evidenceId).orElseThrow();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Evidence");
            createHeaderRow(sheet);
            
            Row row = sheet.createRow(1);
            fillEvidenceRow(row, evidence);

            workbook.write(outputStream);
        }
    }

    @Override
    public void exportAll(OutputStream outputStream) throws IOException {
        // This is a default implementation for compatibility, but we should use the one that takes a list.
        throw new UnsupportedOperationException("Please use exportList to ensure security filtering.");
    }
    
    public void exportList(List<EvidenceDto> evidenceList, OutputStream outputStream) throws IOException {
        if (evidenceList != null && !evidenceList.isEmpty()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Reports");
                createHeaderRow(sheet);

                int rowNum = 1;
                for (EvidenceDto evidence : evidenceList) {
                    Row row = sheet.createRow(rowNum++);
                    fillEvidenceRow(row, evidence);
                }
                workbook.write(outputStream);
            }
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Task Name", "Project", "Order Num", "Department", "Est Time", "Time Spent", "Charge", "Total", "State", "Month"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private void fillEvidenceRow(Row row, EvidenceDto evidence) {
        row.createCell(0).setCellValue(evidence.getId());
        row.createCell(1).setCellValue(evidence.getTaskName());
        row.createCell(2).setCellValue(evidence.getProject());
        row.createCell(3).setCellValue(evidence.getOrderNum());
        row.createCell(4).setCellValue(evidence.getDepartment());
        row.createCell(5).setCellValue(evidence.getEstTime());
        row.createCell(6).setCellValue(evidence.getTimeSpent());
        row.createCell(7).setCellValue(evidence.getCharge());
        row.createCell(8).setCellValue(evidence.getTotal());
        row.createCell(9).setCellValue(evidence.getState());
        row.createCell(10).setCellValue(evidence.getMonth());
    }
}
