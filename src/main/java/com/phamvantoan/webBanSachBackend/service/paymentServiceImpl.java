package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.paymentRepository;
import com.phamvantoan.webBanSachBackend.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class paymentServiceImpl implements paymentService{
    private paymentRepository paymentrepository;
    @Autowired
    public paymentServiceImpl(paymentRepository paymentrepository) {
        this.paymentrepository = paymentrepository;
    }
    @Override
    public Payment findByPaymentID(int paymentID) {
        return this.paymentrepository.findByPaymentID(paymentID);
    }
}
