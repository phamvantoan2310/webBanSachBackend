package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.DeliveryType;
import com.phamvantoan.webBanSachBackend.entity.Orders;
import com.phamvantoan.webBanSachBackend.entity.Payment;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.http.ResponseEntity;

public interface orderService {
    public ResponseEntity<?> deleteOrder(int orderID);
    public ResponseEntity<?> createOrder(User user, int deliveryTypeID, int paymentID);
}
