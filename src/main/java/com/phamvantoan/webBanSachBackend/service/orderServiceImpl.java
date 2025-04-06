package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.selectedBooksResponse;
import com.phamvantoan.webBanSachBackend.dao.*;
import com.phamvantoan.webBanSachBackend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class orderServiceImpl implements orderService{
    @Autowired
    private orderRepository orderrepository;
    @Autowired
    private orderItemRepository orderitemrepository;
    @Autowired
    private deliveryTypeService deliverytypeservice;
    @Autowired
    private paymentService paymentservice;
    @Autowired
    private reportService reportservice;
    @Autowired
    private bookService bookservice;

    @Override
    public ResponseEntity<?> deleteOrder(int orderID, int userID){
        try {
            Orders orders = this.orderrepository.findOrdersByOrderID(orderID);
            if(orders.getUser().getUserID() == userID){
                orders.getUser().getOrderList().remove(orders);

                List<OrderItem> orderItems = new ArrayList<>();

                for(OrderItem orderItem : orders.getOrderItemList()){
                    orderItem.getBook().getOrderItemList().remove(orderItem);
                    orderItems.add(orderItem);
                }

                for(OrderItem orderItem : orderItems){
                    orders.getOrderItemList().remove(orderItem);
                    orderItem.setBook(null);
                    this.orderitemrepository.delete(orderItem);
                }

                orders.getDeliveryType().getOrderList().remove(orders);
                orders.setDeliveryType(null);

                orders.getPayment().getOrderList().remove(orders);
                orders.setPayment(null);

                if(orders.getReport() != null){
                    this.reportservice.deleteReport(orders.getReport());
                }
                this.orderrepository.delete(orders);
            }
        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok("ok");
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    @Override
    @Transactional
    public ResponseEntity<?> createOrder(User user, int deliveryTypeID, int paymentID, int bookID, int numberOfBook, String deliveryAddress, String deliveryPhoneNumber, String deliveryUserName, List<selectedBooksResponse> selectedBooks){
        try{
            DeliveryType deliveryType = this.deliverytypeservice.findByDeliveryTypeID(deliveryTypeID);
            Payment payment = this.paymentservice.findByPaymentID(paymentID);
            Orders orders = new Orders();                                             //set thông tin cơ bản order
            orders.setOrderDate(java.sql.Date.valueOf(getCurrentDate()));
            orders.setOrderStatus("Đang Chờ Xác Nhận");
            orders.setUser(user);
            orders.setDeliveryAddress(deliveryAddress);
            orders.setDeliveryType(deliveryType);
            orders.setPayment(payment);
            orders.setDeliveryPhoneNumber(deliveryPhoneNumber);
            orders.setDeliveryUserName(deliveryUserName);

            user.getOrderList().add(orders);                                        //set Order cho user

            int totalPrice = 0;
            List<OrderItem> orderItems = new ArrayList<>();                         //set OrderItem và totalPrice từ cartItem cho order

            if(bookID == 0){
                for(selectedBooksResponse selectedBook : selectedBooks){
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrders(orders);

                    Book book = this.bookservice.findByBookID(selectedBook.getBookID());
                    orderItem.setNumberOfOrderItem(selectedBook.getNumberOfBooks());
                    orderItem.setPrice(book.getPrice() * selectedBook.getNumberOfBooks());
                    orderItem.setBook(book);
//                    selectedBook.getBook().getOrderItemList().add(orderItem);//*****
                    orderItems.add(orderItem);

                    totalPrice += book.getPrice() * selectedBook.getNumberOfBooks();
                }
            }else {
                Book book = this.bookservice.findByBookID(bookID);
                OrderItem orderItem = new OrderItem();
                orderItem.setOrders(orders);
                orderItem.setNumberOfOrderItem(0);
                orderItem.setPrice(book.getPrice()* numberOfBook);
                orderItem.setBook(book);
                book.getOrderItemList().add(orderItem);

                orderItems.add(orderItem);

                totalPrice += book.getPrice() * numberOfBook;
            }


            totalPrice += deliveryType.getPriceOfDeliveryType();
            totalPrice += payment.getPriceOfPayment();
            orders.setOrderItemList(orderItems);
            orders.setTotalPrice(totalPrice);

            System.out.println(orders.getOrderDate() + orders.getOrderStatus() + "1");
            this.orderrepository.save(orders);
            return ResponseEntity.ok("Tạo order thành công");
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public ResponseEntity<?> deleteOrderItem(int orderItemID) {
        OrderItem orderItem = this.orderitemrepository.findByOrderItemID(orderItemID);
        orderItem.getOrders().getOrderItemList().remove(orderItem);
        orderItem.setOrders(null);

        orderItem.getBook().getOrderItemList().remove(orderItem);
        orderItem.setBook(null);

        this.orderitemrepository.delete(orderItem);
        return ResponseEntity.ok("Xóa orderItem thành công");
    }

    @Override
    public ResponseEntity<?> completeOrder(int orderID) {
        try {
            Orders orders = findByOrderID(orderID);
            orders.setOrderStatus("Hoàn Thành");
            if(orders.getOrderItemList() != null){   //set quantity sold của sách
                for(OrderItem orderItem : orders.getOrderItemList()){
                    if(orderItem.getBook() != null) {
                        orderItem.getBook().setQuantitySold(orderItem.getBook().getQuantitySold() + orderItem.getNumberOfOrderItem());
                    }
                }
            }

            orders.setDeliveryDate(Date.valueOf(getCurrentDate())); //set ngày giao hàng khi khách hàng hoàn thành

            Report report = orders.getReport();
            if(report != null){
                report.getReporttype().getReportList().remove(report);
                report.setReporttype(null);
                report.getUser().getReportList().remove(report);
                report.setUser(null);
                report.getOrders().setReport(null);
                report.setOrders(null);
                this.reportservice.deleteReport(report);
            }
            this.orderrepository.save(orders);
            return ResponseEntity.ok("Hoàn thành đơn hàng thành công");
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public ResponseEntity<?> confirmOrder(int orderID) {
        try {
            Orders orders = findByOrderID(orderID);
            orders.setOrderStatus("Đơn Hàng Đang Được Giao");
            if(orders.getDeliveryType() != null){
                if(orders.getDeliveryType().getDeliveryTypeID() == 1){
                    orders.setDeliveryDate(Date.valueOf(getCurrentDate().plusDays(7)));
                } else if (orders.getDeliveryType().getDeliveryTypeID() == 2) {
                    orders.setDeliveryDate(Date.valueOf(getCurrentDate().plusDays(3)));
                } else if (orders.getDeliveryType().getDeliveryTypeID() == 3) {
                    orders.setDeliveryDate(Date.valueOf(getCurrentDate().plusDays(1)));
                }
            }

            for(OrderItem orderItem : orders.getOrderItemList()){ //set số lượng sách sau khi confirm đơn hàng
                orderItem.getBook().setNumberOfBooks(orderItem.getBook().getNumberOfBooks() - orderItem.getNumberOfOrderItem());
                orderItem.getBook().setQuantitySold(orderItem.getBook().getQuantitySold() + orderItem.getNumberOfOrderItem());
            }
            this.orderrepository.save(orders);
            return ResponseEntity.ok("Xác nhận đơn hàng thành công ");
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Orders> findByOrderDateAndOrderStatus(Date orderDate, String orderStatus) {
        return this.orderrepository.findByOrderDateAndOrderStatus(orderDate, orderStatus);
    }

    @Override
    public ResponseEntity<?> updateOrderAddress(int orderID, String updateAddress, User user) {
        Orders order = this.orderrepository.findOrdersByOrderID(orderID);
        if(order != null){
            if(order.getUser().getUserID() == user.getUserID()){
                order.setDeliveryAddress(updateAddress);
                this.orderrepository.save(order);
                return ResponseEntity.ok("Cập nhật địa chỉ đơn hàng thành công");
            }else {
                return ResponseEntity.badRequest().body("Người dùng không hợp lệ");
            }
        }else {
            return ResponseEntity.badRequest().body("Mã đơn hàng không hợp lệ");
        }
    }

    @Override
    public OrderItem findByOrderItemID(int orderItemID) {
        return this.orderitemrepository.findByOrderItemID(orderItemID);
    }

    @Override
    public Orders findByOrderID(int orderID) {
        return this.orderrepository.findOrdersByOrderID(orderID);
    }

}
