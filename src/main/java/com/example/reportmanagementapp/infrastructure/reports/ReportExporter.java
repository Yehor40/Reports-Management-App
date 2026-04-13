package com.example.reportmanagementapp.infrastructure.reports;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface for exporting data in various formats.
 * Follows the Open/Closed Principle (OCP) - new formats can be added 
 * by creating new implementations of this interface.
 */
public interface ReportExporter {
    void exportSingle(Long entityId, OutputStream outputStream) throws IOException;
    void exportAll(OutputStream outputStream) throws IOException;
}
