package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.selectedBooksResponse;
import com.phamvantoan.webBanSachBackend.entity.*;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;

public interface orderService {
    public OrderItem findByOrderItemID(int orderItemID);
    public Orders findByOrderID(int orderID);
    public ResponseEntity<?> deleteOrder(int orderID, int userID);
    public ResponseEntity<?> createOrder(User user, int deliveryTypeID, int paymentID, int bookID, int numberOfBook, String deliveryAddres, String deliveryPhoneNumber, String deliveryUserName, List<selectedBooksResponse> selectedBooks);
    public ResponseEntity<?> deleteOrderItem(int orderItemID);
    public ResponseEntity<?> completeOrder(int orderID);
    public ResponseEntity<?> confirmOrder(int orderID);
    public List<Orders> findByOrderDateAndOrderStatus(Date deleveryDate, String orderStatus);
    public ResponseEntity<?> updateOrderAddress(int orderID, String updateAddress, User user);
}
