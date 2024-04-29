package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.*;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;

public interface orderService {
    public OrderItem findByOrderItemID(int orderItemID);
    public Orders findByOrderID(int orderID);
    public ResponseEntity<?> deleteOrder(int orderID);
    public ResponseEntity<?> createOrder(User user, int deliveryTypeID, int paymentID, int bookID, int numberOfBook);
    public ResponseEntity<?> deleteOrderItem(int orderItemID);
    public ResponseEntity<?> completeOrder(int orderID);
    public ResponseEntity<?> confirmOrder(int orderID);

    public List<Orders> findByDeliveryDateAndOrderStatus(Date deleveryDate, String orderStatus);
}
