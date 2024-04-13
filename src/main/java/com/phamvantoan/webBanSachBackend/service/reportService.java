package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Report;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.http.ResponseEntity;

public interface reportService {
    public ResponseEntity<?> createReport(User user, String reportDetail, int orderID, int reportTypeID, String reportImageDetail);
    public ResponseEntity<?> deleteReport(Report report);
    public ResponseEntity<?> addReportResponse(int reportID, String reportResponse);
}
