package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.revenueRepository;
import com.phamvantoan.webBanSachBackend.entity.Orders;
import com.phamvantoan.webBanSachBackend.entity.Revenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class revenueServiceImpl implements revenueService {
    private revenueRepository revenuerepository;
    private orderService orderservice;
    @Autowired
    public revenueServiceImpl(revenueRepository revenuerepository, orderService orderservice){
        this.revenuerepository = revenuerepository;
        this.orderservice = orderservice;
    }
    @Override
    public Revenue getByRevenueDate(LocalDate revenueDate) {
        Revenue revenue = createRevenue(revenueDate);
        return revenue;
    }

    private LocalDate getCurentDate(){
        return LocalDate.now().minusDays(1);
    }
    @Override
    public Revenue createRevenue(LocalDate localDate) {
        try {
            Revenue revenue = new Revenue();

            List<Orders> orders = this.orderservice.findByDeliveryDateAndOrderStatus(Date.valueOf(localDate), "Hoàn Thành");

            revenue.setRevenueDate(localDate);

            int totalRevenue = 0;
            for(Orders orders1: orders){
                totalRevenue += orders1.getTotalPrice();
            }

            revenue.setTotalRevenue(totalRevenue);

            return this.revenuerepository.save(revenue);
        }catch (Exception e){
            throw e;
        }
    }
}
