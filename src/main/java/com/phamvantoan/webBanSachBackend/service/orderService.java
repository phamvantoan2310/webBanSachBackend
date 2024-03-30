package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.*;
import org.springframework.http.ResponseEntity;

public interface orderService {
    public OrderItem findByOrderItemID(int orderItemID);
    public ResponseEntity<?> deleteOrder(int orderID);
    public ResponseEntity<?> createOrder(User user, int deliveryTypeID, int paymentID);
}
