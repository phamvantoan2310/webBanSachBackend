package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Payment;

public interface paymentService {
    public Payment findByPaymentID(int paymentID);
}
