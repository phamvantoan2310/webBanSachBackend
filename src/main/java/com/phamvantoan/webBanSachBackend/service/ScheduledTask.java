package com.phamvantoan.webBanSachBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private revenueService revenueservice;
    @Autowired
    public ScheduledTask(revenueService revenueservice){
        this.revenueservice = revenueservice;
    }

    // Lập lịch chạy vào nửa đêm mỗi ngày

}
