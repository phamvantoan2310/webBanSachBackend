package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.BillItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
public interface billItemService {
    public BillItem save(BillItem billItem);

    public ResponseEntity delete(BillItem billItem);
}
