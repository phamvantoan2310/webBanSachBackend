package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.BillItemResponse;
import com.phamvantoan.webBanSachBackend.entity.Bill;
import com.phamvantoan.webBanSachBackend.entity.BillItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface billService {
    public ResponseEntity save(Bill bill, List<BillItemResponse> billItemResponses, int userID);
    public ResponseEntity delete(int billID);
}
