package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Revenue;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface revenueService {
    public Revenue getByRevenueDate(LocalDate revenueDate);

    public Revenue createRevenue( LocalDate localDate);
}
