package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.reportRepository;
import com.phamvantoan.webBanSachBackend.dao.reportTypeRepository;
import com.phamvantoan.webBanSachBackend.entity.Orders;
import com.phamvantoan.webBanSachBackend.entity.Report;
import com.phamvantoan.webBanSachBackend.entity.ReportType;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class reportServiceImpl implements reportService{
    private reportRepository reportrepository;
    private reportTypeRepository reporttyperepository;
    private orderService orderservice;

    @Autowired
    public reportServiceImpl(reportRepository reportrepository, reportTypeRepository reporttyperepository,@Lazy orderService orderservice){
        this.reportrepository = reportrepository;
        this.reporttyperepository = reporttyperepository;
        this.orderservice = orderservice;
    }

    private LocalDate getCurrentDate(){
        return LocalDate.now();
    }
    @Override
    public ResponseEntity<?> createReport(User user, String reportDetail, int orderID, int reportTypeID, String reportImageDetail) {
        try {
            Report report = new Report();
            report.setReportDetail(reportDetail);                            //set reportDetail
            report.setReportImage(reportImageDetail);                        //set reportImageDetail
            Orders orders = this.orderservice.findByOrderID(orderID);        //set Order
            report.setOrders(orders);
            orders.setReport(report);
            orders.setOrderStatus("Chờ Kết Quả Báo Cáo");
            report.setUser(user);                                            //set User
            user.getReportList().add(report);
            ReportType reportType = this.reporttyperepository.findByReportTypeID(reportTypeID);      //set ReportType
            report.setReporttype(reportType);
            reportType.getReportList().add(report);
            report.setCreateReportDate(Date.valueOf(getCurrentDate()));

            this.reportrepository.save(report);
            return ResponseEntity.ok("Tạo thành công report");
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public ResponseEntity<?> deleteReport(Report report) {
        if(report.getUser() != null){
            report.getUser().getReportList().remove(report);
            report.setUser(null);
        }

        if(report.getOrders() != null){
            report.getOrders().setReport(null);
            report.setOrders(null);
        }

        if(report.getReporttype() != null){
            report.getReporttype().getReportList().remove(report);
            report.setReporttype(null);
        }

        this.reportrepository.delete(report);
        return ResponseEntity.ok("Xóa report thành công");
    }

    @Override
    public ResponseEntity<?> addReportResponse(int reportID, String reportResponse) {
        try {
            Report report = this.reportrepository.findByReportID(reportID);
            report.setReportResponse(reportResponse);
            report.getOrders().setOrderStatus("Đơn Hàng Đang Được Giao");
            this.reportrepository.save(report);
            return ResponseEntity.ok("Lưu phản hồi báo cáo đơn hàng thành công");
        }catch (Exception e){
            throw e;
        }
    }

}
